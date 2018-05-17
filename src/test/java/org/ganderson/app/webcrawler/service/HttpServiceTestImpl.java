package org.ganderson.app.webcrawler.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.ganderson.app.webcrawler.data.Page;
import org.ganderson.app.webcrawler.exception.SiteNotFoundException;

public class HttpServiceTestImpl implements HttpService {
   public static final String UNKNOWN_SITE_URL = "http://www.google.com/badurl";
   public static final String IMAGE_1 = "/images/image1.png";
   public static final String IMAGE_2 = "/images/image2.png";
   public static final String IMAGE_3 = "/images/image3.png";
   public static final String IMAGE_4 = "/images/image4.png";
   public static final String KNOWN_SITE_URL = "http://www.knownsite.com";
   public static final String KNOWN_SITE_LEVEL_ONE_URL = KNOWN_SITE_URL + "/levelone";
   public static final String KNOWN_SITE_LEVEL_TWO_URL = KNOWN_SITE_LEVEL_ONE_URL + "/leveltwo";
   public static final String KNOWN_SITE_LEVEL_THREE_URL = KNOWN_SITE_LEVEL_TWO_URL + "/levelthree";
   public static final String KNOWN_SITE_RELATIVE_URL = "relativepage.html";
   public static final String GOOGLE_URL = "http://www.google.com";
   public static final String BRONCOS_URL = "http://www.denverbroncos.com";
   public static final String MAILTO_URL = "mailto:blah@mailinator.com";

   public Page parsePage(String url) throws IOException, SiteNotFoundException {
      if (UNKNOWN_SITE_URL.equals(url)) {
         throw new SiteNotFoundException("site not found");
      }

      Page page = new Page(url);
      Set<String> linkUrls = new HashSet<String>();
      Set<String> imageUrls = new HashSet<String>();

      switch (url) {
      case KNOWN_SITE_URL:
         imageUrls.add(IMAGE_1);
         linkUrls.add(KNOWN_SITE_LEVEL_ONE_URL);
         linkUrls.add(GOOGLE_URL);
         linkUrls.add(MAILTO_URL);
         break;

      case KNOWN_SITE_LEVEL_ONE_URL:
         imageUrls.add(IMAGE_2);
         linkUrls.add(KNOWN_SITE_LEVEL_TWO_URL);
         linkUrls.add(KNOWN_SITE_RELATIVE_URL);
         break;

      case KNOWN_SITE_LEVEL_TWO_URL:
         imageUrls.add(IMAGE_3);
         // test data to test that recursion won't infinitely loop
         linkUrls.add(KNOWN_SITE_LEVEL_ONE_URL);
         linkUrls.add(KNOWN_SITE_LEVEL_THREE_URL);
         break;

      case KNOWN_SITE_LEVEL_THREE_URL:
         imageUrls.add(IMAGE_4);
         linkUrls.add(BRONCOS_URL);
         break;

      case KNOWN_SITE_URL + "/" + KNOWN_SITE_RELATIVE_URL:
         linkUrls.add(BRONCOS_URL);
         break;
      default:
         break;
      }

      page.setLinkUrls(linkUrls);
      page.setImageUrls(imageUrls);
      return page;
   }

}
