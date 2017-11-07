# Spring Cache with Hazelcast

Sample application using Springs @Cacheable annotation, with Hazelcast 
as the cache repository.

## Building

The application uses Gradle. To compile, type:

    ./gradlew clean build
    
    
## Running

To run the application, type:

    java -jar build/libs/caching-1.0-SNAPSHOT.jar
    
Then, open a Web Browser and type:

    http://localhost:8070/country/{country}
    
Where {country} is the name of the country or part of it. Check the logs to see the results.


## Docker

To build the Docker image locally, type

    gradle buildDocker
    
It will build and add the image to the local repository. To run as a Docker container, type:

    docker run -p 8070:8070 spring-caching-sample

### Docker compose 

To make things a easier, we use Docker-compose. The actual file is included in the same folder you found this file.

### Before you start

But before you can use Docker-compose at the first time you need to create a network:

    docker network create caching_vpc

Check if the network was created:

    docker network ls

To see even more information:

    docker network inspect caching_vpc
    
### Run Docker-compose
    
Now, you are ready to run Docker-compose. Go to src/main/docker folder using your preferred Terminal application and
type:

    docker-compose up -d


Make some requests to 

    http://localhost:18070/country/{country}
    http://localhost:28070/country/{country}
    
Check the logs with
    
    docker logs instance1
    docker logs instance2

