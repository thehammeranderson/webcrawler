package org.ganderson.app.webcrawler.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ganderson.app.webcrawler.ElementType;
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
   public static final String GOOGLE_URL = "http://www.google.com";
   public static final String BRONCOS_URL = "http://www.denverbroncos.com";

   public Map<ElementType, List<String>> getElements(String url) throws IOException, SiteNotFoundException {
      if (UNKNOWN_SITE_URL.equals(url)) {
         throw new SiteNotFoundException("site not found");
      }

      Map<ElementType, List<String>> elementMap = new HashMap<>();
      List<String> linkList = new ArrayList<String>();
      List<String> imageList = new ArrayList<String>();

      switch (url) {
      case KNOWN_SITE_URL:
         imageList.add(IMAGE_1);
         linkList.add(KNOWN_SITE_LEVEL_ONE_URL);
         linkList.add(GOOGLE_URL);
         break;

      case KNOWN_SITE_LEVEL_ONE_URL:
         imageList.add(IMAGE_2);
         linkList.add(KNOWN_SITE_LEVEL_TWO_URL);
         break;

      case KNOWN_SITE_LEVEL_TWO_URL:
         imageList.add(IMAGE_3);
         linkList.add(KNOWN_SITE_LEVEL_THREE_URL);
         break;

      case KNOWN_SITE_LEVEL_THREE_URL:
         imageList.add(IMAGE_4);
         linkList.add(BRONCOS_URL);
         break;
      default:
         break;
      }

      elementMap.put(ElementType.LINK, linkList);
      elementMap.put(ElementType.IMAGE, imageList);

      return elementMap;
   }

}
