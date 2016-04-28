package mongodblib;

import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.SimpleCommandMap;
import cn.nukkit.permission.Permission;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.scheduler.Task;
import cn.nukkit.scheduler.TaskHandler;

public class MongoDBLibPlugin extends PluginBase {
	private static MongoDBLibPlugin plugin;
	private MongoClient client = null;
	private MongoDBLibConfig libConfig;

	public boolean isOnline = false;
	public boolean isFallbackRun = false;

	@Override
	public void onLoad() {
		plugin = this;

		this.libConfig = new MongoDBLibConfig(this);
		this.libConfig.load();
		this.libConfig.save();

		if (this.libConfig.useLogger) {
			Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
		} else {
			Logger.getLogger("org.mongodb.driver").setLevel(Level.OFF);
		}

		this.registerPermission("mongodblib.db.config", true, "Change the DB connection information.");
		this.registerCommand("mongo", "mongodblib.db.config", "Change the DB connection information.",
				"mongo <url> <id> <pw> <dbname> <timeout>");

		this.initDB();
	}

	@Override
	public void onDisable() {
		this.libConfig.save();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().toLowerCase() == "mongo") {
			if (args.length != 5)
				return false;

			int timeout = 1;
			try {
				timeout = Integer.valueOf(args[4]);
			} catch (Exception e) {
			}

			this.libConfig.url = args[0];
			this.libConfig.id = args[1];
			this.libConfig.pw = args[2];
			this.libConfig.dbName = args[3];
			this.libConfig.serverSelectionTimeout = timeout * 1000;

			if (this.client != null)
				this.client.close();
			this.isOnline = false;
			this.getLogger().info("Succesfully changed!");
			return true;
		}
		return false;
	}

	private void initDB() {
		this.client = MongoDBLib.getClient(this.libConfig.url, this.libConfig.id, this.libConfig.pw,
				this.libConfig.dbName, this.libConfig.serverSelectionTimeout);
		this.getLogger().info("Trying to connect MongoDB...");

		try {
			ServerAddress address = this.client.getAddress();
			this.getLogger().info("Connected with " + address.getHost() + address.getPort());
			this.isOnline = true;
		} catch (Exception e) {
			this.getLogger().error("Failed to connect with MongoDB...");
			this.client.close();
		}

		this.dbOfflineChecker();
	}

	public void dbOfflineChecker() {
		this.getServer().getScheduler().scheduleDelayedRepeatingTask(new Task() {
			@Override
			public void onRun(int currentTick) {
				Server.getInstance().getScheduler().scheduleAsyncTask(new AsyncTask() {
					private boolean isOnline = false;

					@Override
					public void onRun() {
						try {
							this.isOnline = MongoDBLib.getClient().getAddress() != null ? true : false;
						} catch (Exception e) {
						}
					}

					@Override
					public void onCompletion(Server server) {
						MongoDBLibPlugin plugin = MongoDBLibPlugin.getPlugin();

						if (plugin.isFallbackRun || this.isOnline)
							return;

						plugin.isOnline = this.isOnline;
						plugin.getLogger().error("Failed to connect with MongoDB...");
						plugin.getLogger().error("You can enter the DB connection information.");
						plugin.getLogger().error("mongo <url> <id> <pw> <dbname> <timeout>");
						plugin.dbOfflineFallback();
					}
				});
			}
		}, 20, 100);
	}

	public void dbOfflineFallback() {
		this.isFallbackRun = true;

		Task task = new Task() {
			@Override
			public void onRun(int currentTick) {
				MongoDBLibPlugin plugin = MongoDBLibPlugin.getPlugin();
				if (plugin.isOnline) {
					plugin.isFallbackRun = false;
					this.cancel();
					return;
				}

				String url, id, pw, dbName;
				int timeout;
				url = plugin.getDBLibConfig().url;
				id = plugin.getDBLibConfig().id;
				pw = plugin.getDBLibConfig().pw;
				dbName = plugin.getDBLibConfig().dbName;
				timeout = plugin.getDBLibConfig().serverSelectionTimeout;
				System.out.println(url + " " + id + " " + pw + " " + dbName + " " + timeout);

				Server.getInstance().getScheduler().scheduleAsyncTask(new AsyncTask() {
					public String url, id, pw, dbName;
					private MongoClient client = null;
					private ServerAddress address = null;

					private boolean isOnline = false;
					int timeout;

					public AsyncTask setData(String url, String id, String pw, String dbName, int timeout) {
						this.url = url;
						this.id = id;
						this.pw = pw;
						this.dbName = dbName;
						this.timeout = timeout;
						return this;
					}

					@Override
					public void onRun() {
						this.client = MongoDBLib.getClient(this.url, this.id, this.pw, this.dbName, this.timeout);
						try {
							this.address = this.client.getAddress();
							this.isOnline = true;
						} catch (Exception e) {
							this.client.close();
						}
					}

					@Override
					public void onCompletion(Server server) {
						if (this.isOnline) {
							MongoDBLibPlugin plugin = MongoDBLibPlugin.getPlugin();
							plugin.isOnline = true;
							plugin.client = this.client;
							plugin.getLogger()
									.info("Connected with " + this.address.getHost() + this.address.getPort());
						}
					}
				}.setData(url, id, pw, dbName, timeout));
			}
		};

		TaskHandler handler = this.getServer().getScheduler().scheduleDelayedRepeatingTask(task, 100, 100);
		task.setHandler(handler);
	}

	public static MongoDBLibPlugin getPlugin() {
		return plugin;
	}

	public MongoClient getDefaultClient() {
		return this.client;
	}

	public MongoDBLibConfig getDBLibConfig() {
		return this.libConfig;
	}

	protected boolean registerCommand(String commandName, String permissionName, String commandDescription,
			String commandUsage) {
		SimpleCommandMap commandMap = this.getServer().getCommandMap();
		PluginCommand<Plugin> command = new PluginCommand<>(commandName, getPlugin());
		command.setDescription(commandDescription);
		command.setPermission(permissionName);
		command.setUsage(commandUsage);
		return commandMap.register(commandName, command);
	}

	protected boolean registerPermission(String permissionName, boolean isOp, String description) {
		LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("description", description);

		String DEFAULT = (isOp) ? Permission.DEFAULT_OP : Permission.DEFAULT_TRUE;
		Permission permission = Permission.loadPermission(permissionName, data, DEFAULT);
		return this.getServer().getPluginManager().addPermission(permission);
	}
}
