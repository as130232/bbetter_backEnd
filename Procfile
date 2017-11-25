release: java -cp build/output/libs/snakeyaml-1.19.jar:build/output/libs/liquibase-core-3.5.3.jar liquibase.integration.commandline.Main --changeLogFile=src/main/resources/DB/db.changelog-master.yml --url=$JDBC_DATABASE_URL --classpath=build/output/libs/postgresql-9.4.1212.jar:build/output/libs/snakeyaml-1.19.jar --logLevel=debug update 

web:  java -Dserver.port=$PORT -Dspring.profiles.active=heroku $JAVA_OPTS -jar build/libs/*.jar


