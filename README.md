# Webcrawler

This is a java based website crawler.  Given a fully qualified URL (i.e. http://www.espn.com) as a parameter, the crawler will follow all internal links it finds and capture image source, and internal and external URLs found on the pages it processes.

Here are some of the main features:
* URL format validation
* Crawling will not go back on itself when links become circular and could cause an infinite loop
* Processed URLs can be either http or https and be considered as part of the website being processed
* Domain is used to determine whether or not to consider the link internal to the website being processed (ie. http://www.amazon.com/somewhere and http://amazon.com/somewhereelse are both considered to be part of the same website)
* Relative links are processed correctly (<a href="index.html">Home</a>)
* Compatible with all TLS versions at the time of completion
* Currently the crawler will only process up to 1000 pages but can be configured in the SiteProcessor class

The jar file that is created when this project is built could be used as a library but beware that this is a fat JAR that includes Spring Boot and JSoup dependencies inside the JAR.  It would be recommended that your fork this project if you wish to use this strictly as a library inside another application so you can build the JAR as you wish.

## Getting Started

* Download and install Git
  * See https://git-scm.com/book/en/v2/Getting-Started-Installing-Git for more information
* Open a terminal/console and navigate to the directory you wish to clone the repository to
* At the command line type the following
```
git clone https://github.com/thehammeranderson/webcrawler.git
```

See deployment for notes on how to deploy the project on a live system.

### Prerequisites to build and run

* Java - see https://www.java.com/en/download/help/download_options.xml for download and install instructions
* Maven - see https://maven.apache.org/install.html for download and install instructions

### Installing

* Make sure you have Maven and JUnit plugins for your IDE
* Import the project as a Maven project

If these are configured you should be able to run the application by right clicking on MainApp.java and running as a Java application.  You will also be able to run the WebcrawlerTest.java file as a JUnit test or choose test methods in that class and run them individually via a JUnit plugin.

## Running the tests

If your environment is properly setup with Java and Maven, you can open up a shell/console/terminal window.  Change director to the webcrawler folder.  If you are in the right folder, you will see a pom.xml file.  Now you can type the following from the command line and press <return> to run the Unit Tests:

```
mvn clean test

```

Both the Unit Tests and Integration Tests will run when the project is built as follows:

```
mvn clean install

```
### Break down of tests

Unit tests are in src/test/java/ in the WebcrawlerTest class.  These tests are designed to run without a network connection by using mock data and Spring injection of a test service class.  Listed below are more details on the tests performed.

* testBadURL()
  * This tests that a URL was given and in proper format
* testUnknownWebsite
  * This tests when a properly formed URL was given but the application could not load the website
* testNoRecursion
  * This tests that the application can handle a website that has only external links
* testRecursion
  * This tests a multiple page/level website with a mix of images and internal/external links

Integration tests are in src/test/java in the WebcrawlerIntegrationTest class.  These tests are designed to run successfully only when network connectivity is available and use live website calls.
* testKnowledgeFactor()
  * This test makes a call to knowledgefactor.com, which is a small form factor website

## Deployment

A stand alone executable jar file will be created in the target director of the project.  This can be copied to anywhere java is available to run the jar from a command line.  A file will be created in the directory where the command was run from and it will be called sitemap.txt.

```
java -jar <path to jar>webcrawler-0.0.1-SNAPSHOT.jar http://knowledgefactor.com

```

Where the URL listed at the end of the command is replaced with a website you wish to process.

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - The application framework
* [Maven](https://maven.apache.org/) - Dependency Management
* [JSoup](https://jsoup.org/) - Used to process the HTML documents
* [JUnit](https://junit.org/junit5/) - Used for unit testing

## Future improvements

* Some websites can be huge and would take a long time to process and results could be extremely large.  I would put some configurable options maybe at the command line to change the processing behavior to limit how many pages to process or URLs to return.  Another option would be how to return the results (file, file type, format, etc.)
* Maybe treat none HTML page links like PDF and other file links differently, perhaps as a file link or something similar in category to images
* Implement logging to a file for errors instead of System.out
* Possibly implement Maven Failsafe plugin for integration tests
* Figure out a better way to have more and less fragile integration tests in place
* Work some more on the README to put in more useful detail and help
