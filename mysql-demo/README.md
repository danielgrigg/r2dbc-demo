# mysql-demo

minimal example of using micronaut to add a reactive endpoint to retrieve results from a h2 database using  data-r2dbc.

will need to run 'docker compose up' to bring up the mysql db.

note that doesn't actually work if you try accessing the endpoint - the results aren't well-formed json. presumably
some bug somewhere integrating with r2dbc.

# run

You'll need jdk 11.

.   docker-compose up -d
    ./gradlew run

    curl http://localhost:8080/things

