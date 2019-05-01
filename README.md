
# MONEY TRANSFER API

This rest api consist of account management and transaction operations.

## Getting Started

For the dependency management purpose, MAVEN is used. <br/>
After the project has been cloned, in the project directory <br/>
"mvn test" and "mvn package" commands can be called. <br/>

### Prerequisites

java version 11.0.3 <br/>
MAVEN <br/>
This project uses port "7000" as default <br/>
please make sure that this port is not already in use.

### Dependencies
javalin -> for rest api <br/>
jackson -> json marshall, un-marshall <br/>
testng  -> unit testing <br/>
unirest -> unit testing <br/>

### Installing

Run "mvn package"<br/>
Under the target directory "MoneyTransferAPI-<version>.jar" will be created.<br/>
This jar file contains all the necessary dependencies.<br/>
It can be directly called via "java -jar <jar_name>"<br/>

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

Calling "mvn test" command will eventually execute all the test cases.
There is also Postman collection is added ("MoneyTransferAPI.postman_collection.json").

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## References
http://web.mit.edu/6.005/www/fa14/classes/17-concurrency/