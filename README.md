# MongoDBLib
MongoDBLib for Nukkit Plugin
Through this plugin it can be used MongoDB in Nukkit.

## What exactly do this project
- This plug-in is help using mongodb in nukkit by some other plug-ins.
- It also shared with other plug-ins about the 'MongoDB server'.
- It can may help when you need to share information between multiple servers.

## Usage(Normal User)
- MongoDBLib is need to MongoDB Server (it must be using sha1 credential)
- First of all you need to run server before the set-up this plug-in.
- Go to the release page and download jar file. (https://github.com/organization/MongoDBLib/releases)
- And put into the nukkit plugins folder run the server and close it.
- Then plugins\MongoDBLib\config.yml is created, you need to edit this file.

## Usage(Developer)
- Please send us a lot of pull request :p
- You might be check 'MongoDBLib' is exist server before the using the plugin code.
```
if (this.getServer().getPluginManager().getPlugin("MongoDBLib") == null) return false;
```
- And if you want to get MongoDBClient, you must use this plugin method,
```
MongoDBLib.getClient(); //- there will be returned MongoClient
```
- If you want some make a direct connect with other mongodb server, you can try this method
```
MongoDBLib.getClient(String url,String id,String pw,String dbName, int serverSelectionTimeout);
```
## Build
- This project is maven project.
- If you have some maven build system, so you can build plugin to jar.
- 1. Download proeject file. (or git clone https://github.com/organization/MongoDBLib && cd MongoDBLib)
- 2. Type mvn package in terminal.
