Simple Librairy Project

Using :
- Spring Boot 2.3.1.RELEASE
- Spring 5.2.7.RELEASE
- Spring Batch 4.2.4.RELEASE
- Google AutoValue 1.7.3
- Google Guava 29.0-jre
- JOOQ 3.13.2
- Flyway 6.4.4

Install :
- You need a MySQL Database in version 5.7.x
    - Create a database named librairy
    - Create two MySQL user named :
        - librairy_user (do not need DDL rights)
        - librairy_deploy_user (need DDL rights)
- Run maven command : mvn clean install
- The test are using an H2 Database
- You can easily only use a H2 Database instead of a MySQL one by : 
    - Removing the mysql-connector from the pom 
    - Changing the scope from the h2 driver
    - Updading the application.yaml and config.properties to use H2 instead.

Endpoints :
  - Authors :
    - localhost:8080/v1/author
    - localhost:8080/v1/author/{authorUuid}
  - Books :   
    - localhost:8080/v1/book
    - localhost:8080/v1/book/{bookUuid}
    - localhost:8080/v1/book/author/{authorUuid}