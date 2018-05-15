package org.ganderson.app.webcrawler;

import org.ganderson.app.webcrawler.exception.InvalidUrlException;
import org.ganderson.app.webcrawler.exception.SiteNotFoundException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class WebcrawlerTest extends TestCase {
   SiteProcessor siteProcessor = new SiteProcessor();

   /**
    * Create the test case
    *
    * @param testName name of the test case
    */
   public WebcrawlerTest(String testName) {
      super(testName);
   }

   /**
    * @return the suite of tests being tested
    */
   public static Test suite() {
      return new TestSuite(WebcrawlerTest.class);
   }

   public void testBadURL() throws Exception {

      try {
         siteProcessor.crawlSite(null);
         fail("Did not throw exception for null URL");
      } catch (InvalidUrlException e) {
         assertEquals("url param required", e.getMessage());
      }

      try {
         siteProcessor.crawlSite("blah");
         fail("Did not throw exception for invalid URL");
      } catch (InvalidUrlException e) {
         assertEquals("invalid url", e.getMessage());
      }

      try {
         siteProcessor.crawlSite("http://");
         fail("Did not throw exception for invalid URL");
      } catch (InvalidUrlException e) {
         assertEquals("invalid url", e.getMessage());
      }

      try {
         siteProcessor.crawlSite("http://www./");
         fail("Did not throw exception for invalid URL");
      } catch (InvalidUrlException e) {
         assertEquals("invalid url", e.getMessage());
      }
   }

   public void testUnknownWebsite() throws Exception {
      try {
         siteProcessor.crawlSite("http://wwww.goggle.com");
         fail("Did not throw exception for site not found");
      } catch (SiteNotFoundException e) {
         assertEquals("site not found", e.getMessage());
      }

      try {
         siteProcessor.crawlSite("http://www.google.com/somebadplace");
         fail("Did not throw exception for site not found");
      } catch (SiteNotFoundException e) {
         assertEquals("site not found", e.getMessage());
      }
   }
}
