# MongoDBLib
MongoDBLib 은 Nukkit 용 플러그인입니다.
이 플러그인으로 Nukkit 에서 MongoDB를 사용할 수 있습니다.

## 이 프로젝트는 정확히 무엇을 하나요
- 이 플러그인은 nukkit 의 다른 플러그인들이 mongodb를 사용할 수 있도록 돕습니다.
- 이 플러그인은 또한 MongoDB 서버를 다른 플러그인과 공유합니다.
- 서버 간에 정보를 교환해야하는 경우 이 플러그인이 도움이 될 수도 있습니다.

## 사용법(일반 사용자)
- MongoDB 서버를 필요로 합니다. (반드시 sha1 증명서를 이용해야합니다.)
- 앞서 말하자면 플러그인 설정을 하기 전에 서버를 한번 실행해야합니다.
- 출시 페이지에서 jar 파일을 다운받아주세요. (https://github.com/organization/MongoDBLib/releases)
- 그리고 누킷 플러그인 폴더에 넣은 후 서버를 동작시키고 종료해주세요.
- 그러면 plugins\MongoDBLib\config.yml 이 생성됩니다. 그 파일을 수정하셔야 합니다.
- config.yml 내에 반드시 입력해야하는 정보
```
#만약 DB서버 IP가 localhost고 포트가 27017이고 기본 DB명이 mydb 고, ID가 myid 고, 비밀번호가 mypw 일때,
mongodblib:
  settings:
    use-logger: false
    server-selection-timeout: 1000
  defaultserver:
    url: localhost:999
    id: mydb
    pw: 'myid'
    db-name: mydb
```

## 사용법(개발자)
- 우리에게 많은 풀 리퀘스트를 보내주세요 :p
- 플러그인 코드를 사용하기 전에 서버에 'MongoDBLib' 이 존재하는지 체크해야할 수도 있습니다.
```
if (this.getServer().getPluginManager().getPlugin("MongoDBLib") == null) return false;
```
- 서버의 기본 Database 를 얻으려 하는 경우 아래 함수를 사용하시면 됩니다.
```
MongoDBLib.getDatabase()
```
- 서버의 다른 Database 를 얻으려 하는 경우 아래 함수를 사용하시면 됩니다.
```
MongoDBLib.getDatabase(String dbName)
```
- MongoDBClient 를 얻으려하는 경우 반드시 아래 플러그인 함수를 사용하셔야 합니다.
```
MongoDBLib.getClient(); //- there will be returned MongoClient
```
- 다른서버에 직접적인 연결을 플러그인 내에서 하려는 경우 아래 함수로 시도하실 수 있습니다.
```
MongoDBLib.getClient(String url,String id,String pw,String dbName, int serverSelectionTimeout);
```

## 메이븐 종속성 연결(개발자)
- 이 프로젝트는 메이븐 프로젝트입니다.
- 다른 메이븐 프로젝트에서 pom.xml에 아래 정보를 적으면 MongoDBLib이 자동으로 적용됩니다.
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
	<version>1.0.2-SNAPSHOT</version>
	<scope>provided</scope>
 </dependency>
</dependencies>
```

## 빌드
- 이 프로젝트는 메이븐 프로젝트입니다.
- 메이븐 빌드 시스템을 갖추고 계신 경우, jar 파일을 직접 빌드하실수 있습니다.
- 1. 프로젝트 파일을 다운로드 합니다. ( 혹은 git clone https://github.com/organization/MongoDBLib && cd MongoDBLib)
- 2. 터미널에서 mvn package 를 적습니다.
