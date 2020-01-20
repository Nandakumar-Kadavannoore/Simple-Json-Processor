# Simple NOSQL database using file.

A simple NOSQL database using file using java. Simple project to demonstrate the storing of input data in form of JSON in a file.

## Technologies used
```sh
Java(Programming language)
Spring Boot(Java based framework)
Maven(Built tool)
```

## Installation or local setup

Import project as maven in any Java based IDE. To try at local run the main application class.
For checking each feature, We exposed functionality as RESTful Web series. So you can try hitting
using client apps or as CURL commend.<br />
<br />
[How to use curl](https://www.baeldung.com/curl-rest)
<br />
Base URL - http://localhost:8080/

Also try using command line arguments.
```sh
 mvn -q clean compile exec:java -Dexec.mainClass="com.example.processor.ProcessorApplication" -Dexec.args="GET_ALL"
```
Add new operational command by changing -Dexec.args="OPERATION"


## Usage or functionality
The following functionality are provided as now
```sh
1. Add a record

2. Delete a record or records by a key-value pair

3. Find all the records which contain a particular value

4. Should be able to select particular fields while finding a record(by default it should return all the fields)
```

## Exposed functionality

```sh
1. Add a record - POST -  http://localhost:8080/ - ADD="input json as string"
2. Delete record by key value pair - DELETE - http://localhost:8080/{key}/{value} - DELETE_RECORD_BY_KEY_VALUE="key,value"
3. Get all records - GET - http://localhost:8080/ - GET_ALL
4. Delete a record by id - DELETE - http://localhost:8080/{recordId} - DELETE_BY_ID="recordId"
5. Get all records by value - GET - http://localhost:8080/record/value/{value} - GET_BY_VALUE="value"
6. Get record by Id or - GET - http://localhost:8080/{recordId} - GET_BY_ID="recordId"
```

For more information see [controller](https://github.com/Nandakumar-Kadavannoore/Simple-Json-Processor/blob/develop/src/main/java/com/example/processor/com/example/processor/controller/ProcessorController.java)

Here, double quotes not required onces.
For get record by Id functionality, if we need only specific response only pass response fields as comma seperated. eg: GET_BY_ID=5653yut,Responsekey1,Responsekey2

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

