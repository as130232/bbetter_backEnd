web:  java -Dserver.port=$PORT -Dspring.profiles.active=heroku $JAVA_OPTS -jar build/libs/*.jar

release: java -jar target/dependency/liquibase.jar --changeLogFile=src/main/resources/DB/db.changelog-master.yml --url=$JDBC_DATABASE_URL --classpath=target/dependency/postgres.jar update


