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