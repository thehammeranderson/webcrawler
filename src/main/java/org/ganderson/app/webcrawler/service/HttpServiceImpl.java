package org.ganderson.app.webcrawler.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.ganderson.app.webcrawler.ElementType;
import org.ganderson.app.webcrawler.exception.SiteNotFoundException;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HttpServiceImpl implements HttpService {

   public Map<ElementType, Set<String>> getElements(String url) throws IOException, SiteNotFoundException {
      try {
         Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").referrer("http://www.google.com").get();
         Set<String> linkList = new HashSet<String>();

         Iterator<Element> iterator = doc.getElementsByTag("a").iterator();
         while (iterator.hasNext()) {
            Element element = iterator.next();
            linkList.add(element.absUrl("href"));
         }

         Set<String> imageList = new HashSet<String>();
         iterator = doc.getElementsByTag("img").iterator();
         while (iterator.hasNext()) {
            Element element = iterator.next();
            imageList.add(element.absUrl("src"));
         }

         Map<ElementType, Set<String>> elementMap = new HashMap<ElementType, Set<String>>();
         elementMap.put(ElementType.LINK, linkList);
         elementMap.put(ElementType.IMAGE, imageList);
         return elementMap;
      } catch (HttpStatusException e) {
         throw new SiteNotFoundException("site not found");
      }
   }

}
