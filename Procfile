release: java -jar build/output/libs/liquibase-core-3.5.3.jar --changeLogFile=src/main/resources/DB/db.changelog-master.yml --url=$JDBC_DATABASE_URL --classpath=build/output/libs/snakeyaml-1.15.jar update

web:  java -Dserver.port=$PORT -Dspring.profiles.active=heroku $JAVA_OPTS -jar build/libs/*.jar




