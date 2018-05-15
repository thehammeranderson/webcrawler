package org.ganderson.app.webcrawler.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ganderson.app.webcrawler.ElementType;
import org.ganderson.app.webcrawler.exception.SiteNotFoundException;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HttpServiceImpl implements HttpService {

   public Map<ElementType, List<String>> getElements(String url) throws IOException, SiteNotFoundException {
      try {
         Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").referrer("http://www.google.com").get();
         List<String> linkList = new ArrayList<String>();
         List<String> imageList = new ArrayList<String>();
         Map<ElementType, List<String>> elementMap = new HashMap<ElementType, List<String>>();
         elementMap.put(ElementType.LINK, linkList);
         elementMap.put(ElementType.IMAGE, imageList);
         return elementMap;
      } catch (HttpStatusException e) {
         throw new SiteNotFoundException("site not found");
      }
   }

}
