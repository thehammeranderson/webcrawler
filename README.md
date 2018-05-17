# Webcrawler

This is a java based website crowler.  Given a fully qualified URL (i.e. http://www.espn.com) as a parameter, the crawler will follow all internal links it finds and capture image source, and internal and external URLs found on the pages it processes.

Here are some of the main features:
* URL format validation
* Crawling will not go back on itself when links become circular and could cause an infinite loop
* Processed URLs can be either http or https and be considered as part of the website being processed
* Domain is used to determine whether or not to consider the link internal to the website being processed (ie. http://www.amazon.com/somewhere and http://amazon.com/somewhereelse are both considered to be part of the same website
* Relative links are processed correctly (<a href="index.html">Home</a>)

## Getting Started

* Download and install Git
** See https://git-scm.com/book/en/v2/Getting-Started-Installing-Git for more information
* Click Clone or Download on this Github repository page
* In the Clone with HTTPs section, click the copy button to the right of the URL to copy the clone URL for the repository.
* Open a terminal/console and navigate to the directory you wish to clone the repository to
* At the command line type the following
** git clone https://github.com/thehammeranderson/webcrawler.git
* You can now import this project into your IDE by importing as a Maven project

See deployment for notes on how to deploy the project on a live system.

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

If your environment is properly setup with Java and Maven, you can open up a shell/console/terminal window.  Change director to the webcrawler folder.  If you are in the right folder, you will see a pom.xml file.  Now you can type the following from the command line and press <return>:

```
mvn clean test

```

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



