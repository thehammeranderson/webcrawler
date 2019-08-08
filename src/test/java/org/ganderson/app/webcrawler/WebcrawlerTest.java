package org.ganderson.app.webcrawler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ganderson.app.webcrawler.config.TestConfig;
import org.ganderson.app.webcrawler.data.Page;
import org.ganderson.app.webcrawler.data.SiteProcessor;
import org.ganderson.app.webcrawler.exception.InvalidUrlException;
import org.ganderson.app.webcrawler.exception.SiteNotFoundException;
import org.ganderson.app.webcrawler.service.HttpServiceTestImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringRunner.class)
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
      List<Page> results = siteProcessor.crawlSite(HttpServiceTestImpl.KNOWN_SITE_LEVEL_THREE_URL);
      assertEquals("only one page was not returned", 1, results.size());
      Page page = results.get(0);
      assertTrue("image 4 wasn't returned", page.getImageUrls().contains(HttpServiceTestImpl.IMAGE_4));
      assertTrue("broncos url wasn't returned", page.getLinkUrls().contains(HttpServiceTestImpl.BRONCOS_URL));
   }

   @Test
   public void testRecursion() throws Exception {
      List<Page> pages = siteProcessor.crawlSite(HttpServiceTestImpl.KNOWN_SITE_URL);
      assertEquals("four pages were not returned", 5, pages.size());

      Set<String> results = new HashSet<String>();
      for (Page page : pages) {
         results.addAll(page.getImageUrls());
         results.addAll(page.getLinkUrls());
      }

      assertTrue("image 1 wasn't returned", results.contains(HttpServiceTestImpl.IMAGE_1));
      assertTrue("level one wasn't returned", results.contains(HttpServiceTestImpl.KNOWN_SITE_LEVEL_ONE_URL));
      assertTrue("google url wasn't returned", results.contains(HttpServiceTestImpl.GOOGLE_URL));

      assertTrue("image 2 wasn't returned", results.contains(HttpServiceTestImpl.IMAGE_2));
      assertTrue("level two wasn't returned", results.contains(HttpServiceTestImpl.KNOWN_SITE_LEVEL_TWO_URL));
      assertTrue("relative url wasn't returned", results.contains(HttpServiceTestImpl.KNOWN_SITE_RELATIVE_URL));

      assertTrue("image 3 wasn't returned", results.contains(HttpServiceTestImpl.IMAGE_3));
      assertTrue("level three wasn't returned", results.contains(HttpServiceTestImpl.KNOWN_SITE_LEVEL_THREE_URL));

      assertTrue("image 4 wasn't returned", results.contains(HttpServiceTestImpl.IMAGE_4));
      assertTrue("broncos url wasn't returned", results.contains(HttpServiceTestImpl.BRONCOS_URL));

   }
}
