# Webcrawler

This is a java based simple website crowler.  Given a fully qualified URL as a parameter, the crawler will follow all internal links it finds and capture image source, and internal and external URLs found on the pages it processes.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites to build and run

* Java - see https://www.java.com/en/download/help/download_options.xml for download and install instructions
* Maven - see https://maven.apache.org/install.html for download and install instructions

### Installing

A step by step series of examples that tell you have to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

From the root of the project run the following from the command line:
mvn clean test

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

## Deployment

A stand alone executable jar file will be created in the target director of the project.  This can be copied to anywhere java is available to run the jar from a command line.

## Built With

* [Spring](https://spring.io/) - The application framework
* [Maven](https://maven.apache.org/) - Dependency Management
* [JSoup](https://jsoup.org/) - Used to process the HTML documents
* [JUnit](https://junit.org/junit5/) - Used for unit testing

## Future improvements

* Some websites can be huge and would take a long time to process and results could be extremely large.  I would put some configurable options maybe at the command line to change the processing behavior to limit how many recursions or URLs to return.  Another option would be how to return the results (file, file type, format, etc.)
* Processing could take a long time, so depending on the first improvement listed it would be nice to maybe give some feedback to the user to let them know work is being done until the processing has either hit any limits or finished processing the website



