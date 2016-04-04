package mongodblib;

import java.util.Arrays;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

public class MongoDBLib {
	public static MongoClient getClient() {
		return MongoDBLibPlugin.getPlugin().getDefaultClient();
	}

	public static MongoClient getClient(String url, String id, String pw, String dbName, int serverSelectionTimeout) {
		MongoClient client = null;
		try {
			MongoClientOptions.Builder optionsBuilder = new MongoClientOptions.Builder();
			optionsBuilder.serverSelectionTimeout(serverSelectionTimeout);

			MongoClientOptions options = optionsBuilder.build();
			MongoCredential credential = MongoCredential.createScramSha1Credential(id, dbName, pw.toCharArray());

			client = new MongoClient(new ServerAddress(url), Arrays.asList(credential), options);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return client;
	}

	public static MongoDatabase getDatabase() {
		return MongoDBLib.getClient().getDatabase(MongoDBLibPlugin.getPlugin().getDBLibConfig().dbName);
	}

	public static MongoDatabase getDatabase(String dbName) {
		return MongoDBLib.getClient().getDatabase(dbName);
	}
}