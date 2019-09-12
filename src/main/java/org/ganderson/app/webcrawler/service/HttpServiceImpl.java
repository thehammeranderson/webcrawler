package org.ganderson.app.webcrawler.service;

import org.ganderson.app.webcrawler.data.Page;
import org.ganderson.app.webcrawler.exception.SiteNotFoundException;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HttpServiceImpl implements HttpService {

   public Page parsePage(String url) throws IOException, SiteNotFoundException {
      try {
         System.out.println("processing page: " + url);
         Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").referrer("http://www.google.com").get();
         Page page = new Page(url);
         Set<String> linkUrls = new HashSet<String>();

         Iterator<Element> iterator = doc.getElementsByTag("a").iterator();
         while (iterator.hasNext()) {
            Element element = iterator.next();
            linkUrls.add(element.absUrl("href"));
         }
         page.setLinkUrls(linkUrls);

         Set<String> imageUrls = new HashSet<String>();
         iterator = doc.getElementsByTag("img").iterator();
         while (iterator.hasNext()) {
            Element element = iterator.next();
            imageUrls.add(element.absUrl("src"));
         }
         page.setImageUrls(imageUrls);

         return page;
      } catch (HttpStatusException | UnsupportedMimeTypeException e) {
         System.out.println("error processing page: " + e.getMessage());
         return null;
      }
   }

}
