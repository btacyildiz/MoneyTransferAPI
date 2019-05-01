
# MONEY TRANSFER API

This rest api consist of account management and transaction operations.

## Getting Started

For the dependency management purpose, MAVEN is used.
After the project has been cloned, in the project directory
"mvn test" and "mvn package" commands can be called.

### Prerequisites

Java 8
MAVEN
This project uses port "7000" as default
please make sure that this port is not already in use.

### Dependencies
javalin -> for rest api
jackson -> json marshall, un-marshall
testng  -> unit testing
unirest -> unit testing

### Installing

Run "mvn package"
Under the target directory "MoneyTransferAPI-<version>.jar" will be created.
This jar file contains all the necessary dependencies.
It can be directly called via "java -jar <jar_name>"

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

Calling "mvn test" command will eventually execute all the test cases.
There is also Postman collection is added ("MoneyTransferAPI.postman_collection.json").

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## References
http://web.mit.edu/6.005/www/fa14/classes/17-concurrency/