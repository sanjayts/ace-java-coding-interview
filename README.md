# ace-java-coding-interview

Code snippets as I follow along with the Educative.io course

## Setup 

The maven wrapper was added to this project by running the command -- `mvn -N io.takari:maven:wrapper`


## Running specific classes

`./mvnw compile exec:java -Dexec.mainClass=net.sanjayts.educative.acejava.ds.SearchRotated`

## Libraries Used

We use:

* jqwik for property based testing/verification
* slf4j and logback for logging
* Lombok for helper annotations like @Slf4j and @Data etc.
* junit5 for unit testing framework
* assert4j for assertions
* maven-shade plugin for creating an uber jar
* surefire plugin for running tests with `./mvnw test`