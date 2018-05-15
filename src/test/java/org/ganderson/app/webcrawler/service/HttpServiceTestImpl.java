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

   public Map<ElementType, List<String>> getElements(String url) throws IOException, SiteNotFoundException {
      if (UNKNOWN_SITE_URL.equals(url)) {
         throw new SiteNotFoundException("site not found");
      }

      Map<ElementType, List<String>> elementMap = new HashMap<>();
      List<String> linkList = new ArrayList<String>();
      linkList.add(url + "/levelone");
      linkList.add(url + "/levelone/leveltwo");
      linkList.add(url + "/levelone/leveltwo/levelthree");
      linkList.add("http://www.google.com");
      linkList.add("http://www.denverbroncos.com");

      List<String> imageList = new ArrayList<String>();
      imageList.add(IMAGE_1);
      imageList.add(IMAGE_2);
      imageList.add(IMAGE_3);
      imageList.add(IMAGE_4);

      elementMap.put(ElementType.LINK, linkList);
      elementMap.put(ElementType.IMAGE, imageList);

      return elementMap;
   }

}
