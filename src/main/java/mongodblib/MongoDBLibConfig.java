package mongodblib;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.SimpleConfig;

public class MongoDBLibConfig extends SimpleConfig {
	@Path(value = "mongodblib.settings.use-logger")
	public boolean useLogger = false;
	
	@Path(value = "mongodblib.settings.server-selection-timeout")
	public int serverSelectionTimeout = 1000;
	
	@Path(value = "mongodblib.defaultserver.url")
	public String url = "localhost:27017";
	
	@Path(value = "mongodblib.defaultserver.id")
	public String id = "admin";
	
	@Path(value = "mongodblib.defaultserver.pw")
	public String pw = "";

	@Path(value = "mongodblib.defaultserver.db-name")
	public String dbName = "";
	
	public MongoDBLibConfig(PluginBase plugin) {
		super(plugin);
	}
}
