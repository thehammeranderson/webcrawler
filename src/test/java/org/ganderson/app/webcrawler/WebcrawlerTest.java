package org.ganderson.app.webcrawler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import org.ganderson.app.webcrawler.config.TestConfig;
import org.ganderson.app.webcrawler.exception.InvalidUrlException;
import org.ganderson.app.webcrawler.exception.SiteNotFoundException;
import org.ganderson.app.webcrawler.service.HttpServiceTestImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
public class WebcrawlerTest {
   @Autowired
   SiteProcessor siteProcessor;

   @Test
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

   @Test
   public void testUnknownWebsite() throws Exception {
      try {
         siteProcessor.crawlSite(HttpServiceTestImpl.UNKNOWN_SITE_URL);
         fail("Did not throw exception for site not found");
      } catch (SiteNotFoundException e) {
         assertEquals("site not found", e.getMessage());
      }
   }

   @Test
   public void testNoRecursion() throws Exception {
      Set<String> results = siteProcessor.crawlSite(HttpServiceTestImpl.KNOWN_SITE_LEVEL_THREE_URL);
      assertTrue("image 4 wasn't returned", results.contains(HttpServiceTestImpl.IMAGE_4));
      assertTrue("broncos url wasn't returned", results.contains(HttpServiceTestImpl.BRONCOS_URL));
   }

   @Test
   public void testRecursion() throws Exception {
      Set<String> results = siteProcessor.crawlSite(HttpServiceTestImpl.KNOWN_SITE_URL);
      assertTrue("image 1 wasn't returned", results.contains(HttpServiceTestImpl.IMAGE_1));
      assertTrue("level one wasn't returned", results.contains(HttpServiceTestImpl.KNOWN_SITE_LEVEL_ONE_URL));

      assertTrue("image 2 wasn't returned", results.contains(HttpServiceTestImpl.IMAGE_2));
      assertTrue("level two wasn't returned", results.contains(HttpServiceTestImpl.KNOWN_SITE_LEVEL_TWO_URL));

      assertTrue("image 3 wasn't returned", results.contains(HttpServiceTestImpl.IMAGE_3));
      assertTrue("level three wasn't returned", results.contains(HttpServiceTestImpl.KNOWN_SITE_LEVEL_THREE_URL));

      assertTrue("image 4 wasn't returned", results.contains(HttpServiceTestImpl.IMAGE_4));
      assertTrue("broncos url wasn't returned", results.contains(HttpServiceTestImpl.BRONCOS_URL));
   }
}
