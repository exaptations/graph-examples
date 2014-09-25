mvn clean -Premoveeclipse 
mvn clean -Pshadepackage 
mvn clean install -Dmaven.test.skip=true 
mvn compile -Dmaven.test.skip=true 
mvn compile -DskipTests=true 
mvn install -DskipTests=true 
mvn compile jetty:run 
mvn compile jetty:run-war 
mvn comile -DsocksProxyPort=9090 mvn clean compile package -DsocksProxyHost=127.0.0.1 
mvn exec:java -Dexec.mainClass="com.js.app.launch.LaunchTray" -Djava.library.path=./src/main/resources/native/ -o  
