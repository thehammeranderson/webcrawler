package org.ganderson.app.webcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ganderson.app.webcrawler.exception.InvalidUrlException;
import org.ganderson.app.webcrawler.exception.SiteNotFoundException;
import org.ganderson.app.webcrawler.service.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SiteProcessor {
   @Autowired
   HttpService httpService;

   private List<String> mainList = new ArrayList<String>();
   private Set<String> processedUrlMap = new HashSet<String>();

   public String crawlSite(String url) throws InvalidUrlException, SiteNotFoundException, IOException {
      validateUrl(url);
      processUrl(url);
      return mainList.toString();
   }

   private void processUrl(String url) throws IOException, SiteNotFoundException {
      if (!processedUrlMap.contains(url)) {
         processedUrlMap.add(url);
         Map<ElementType, List<String>> elementMap = httpService.getElements(url);
         mainList.addAll(elementMap.get(ElementType.LINK));
         mainList.addAll(elementMap.get(ElementType.IMAGE));
      }
   }

   private void validateUrl(String url) throws InvalidUrlException {
      if (url == null) {
         throw new InvalidUrlException("url param required");
      }

      Pattern pattern = Pattern.compile("^(https?:\\/\\/)(www\\.){0,1}([\\w]+\\.)+[‌​\\w]{2,63}\\/?(\\w+\\/?)+$");
      Matcher matcher = pattern.matcher(url);
      if (!matcher.matches()) {
         throw new InvalidUrlException("invalid url");
      }
   }
}
