# MongoDBLib
MongoDBLib for Nukkit Plugin
Through this plugin it can be used MongoDB in Nukkit.

## What exactly do this project
- This plug-in is help using mongodb in nukkit by some other plug-ins.
- It also shared with other plug-ins about the 'MongoDB server'.
- It can may help when you need to share information between multiple servers.

## Usage(Normal User)
- MongoDBLib is need to MongoDB Server (it must be using sha1 credential)
- Enter the following command to enter the default server address and account information.
```
/mongo <url> <id> <pw> <dbname> <timeout>
```

## Usage(Developer)
- Please send us a lot of pull request :p
- You might be check 'MongoDBLib' is exist server before the using the plugin code.
```
if (this.getServer().getPluginManager().getPlugin("MongoDBLib") == null) return false;
```
- If you want to get the default database you can use this plugin method.
```
MongoDBLib.getDatabase()
```
- If you want to get the other database you can use this plugin method.
```
MongoDBLib.getDatabase(String dbName)
```
- And if you want to get MongoDBClient, you must use this plugin method,
```
MongoDBLib.getClient(); //- there will be returned MongoClient
```
- If you want some make a direct connect with other mongodb server, you can try this method
```
MongoDBLib.getClient(String url,String id,String pw,String dbName, int serverSelectionTimeout);
```

## Maven Dependency Connect(Developer)
- This project is maven project.
- If you want use this project In another Maven project, you can use to the put a following information in the pom.xml, then MongoDBLib is automatically applied.
```
<repositories>
 <repository>
  <id>organization</id>
  <url>https://github.com/organization/maven-repo/raw/master/</url>
 </repository>
</repositories>
<dependencies>
 <dependency>
	<groupId>org.mongodb</groupId>
	<artifactId>mongo-java-driver</artifactId>
	<version>3.2.2</version>
	<scope>provided</scope>
 </dependency>
 <dependency>
	<groupId>mongodblib</groupId>
	<artifactId>MongoDBLib</artifactId>
	<version>1.0.3-SNAPSHOT</version>
	<scope>provided</scope>
 </dependency>
</dependencies>
```

## Build
- This project is maven project.
- If you have some maven build system, so you can build plugin to jar.
- 1. Download proeject file. (or git clone https://github.com/organization/MongoDBLib && cd MongoDBLib)
- 2. Type mvn package in terminal.
