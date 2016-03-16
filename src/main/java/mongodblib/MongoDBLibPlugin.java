package mongodblib;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import cn.nukkit.plugin.PluginBase;

public class MongoDBLibPlugin extends PluginBase {
	private static MongoDBLibPlugin plugin;
	private MongoClient client;
	private MongoDBLibConfig libConfig;

	@Override
	public void onEnable() {
		plugin = this;

		this.libConfig = new MongoDBLibConfig(this);
		this.libConfig.load();
		this.libConfig.save();

		if (this.libConfig.useLogger) {
			Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
		} else {
			Logger.getLogger("org.mongodb.driver").setLevel(Level.OFF);
		}

		this.initDB();
	}

	private void initDB() {
		this.client = MongoDBLib.getClient(this.libConfig.url, this.libConfig.id, this.libConfig.pw,
				this.libConfig.dbName, this.libConfig.serverSelectionTimeout);
		this.getLogger().info("Trying to connect MongoDB...");
		try {
			ServerAddress address = this.client.getAddress();
			this.getLogger().info("Connected with " + address.getHost() + address.getPort());
		} catch (Exception e) {
			this.getLogger().error("Failed to connect with MongoDB...");
			this.client.close();
		}
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
}
