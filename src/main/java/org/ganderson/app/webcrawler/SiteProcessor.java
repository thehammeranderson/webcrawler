package org.ganderson.app.webcrawler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ganderson.app.webcrawler.exception.InvalidUrlException;
import org.ganderson.app.webcrawler.exception.SiteNotFoundException;
import org.ganderson.app.webcrawler.service.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class SiteProcessor {
   @Autowired
   HttpService httpService;

   private Set<String> mainList = new HashSet<String>();
   private Set<String> processedUrlMap = new HashSet<String>();
   private String baseUrl;
   private int maxRecursion = 100;
   private int recursionCount = 0;

   public Set<String> crawlSite(String url) throws InvalidUrlException, SiteNotFoundException, IOException {
      validateUrl(url);
      baseUrl = url;
      processUrl(url);
      return mainList;
   }

   private void processUrl(String url) throws IOException, SiteNotFoundException {
      if (!processedUrlMap.contains(url) && recursionCount <= maxRecursion) {
         recursionCount++;

         if (recursionCount >= maxRecursion) {
            return;
         }

         processedUrlMap.add(url);
         Map<ElementType, Set<String>> elementMap = httpService.getElements(url);
         mainList.addAll(elementMap.get(ElementType.LINK));
         mainList.addAll(elementMap.get(ElementType.IMAGE));

         for (String linkUrl : elementMap.get(ElementType.LINK)) {
            if (linkUrl.toLowerCase().startsWith("mailto:")) {
               continue;
            } else if (linkUrl.startsWith(baseUrl)) {
               processUrl(linkUrl);

            } else if (!linkUrl.startsWith("http://") && !linkUrl.startsWith("https://")) {
               // handle relative urls   
               String concatChar = "";
               if (!baseUrl.endsWith("/")) {
                  concatChar = "/";
               }
               processUrl(baseUrl + concatChar + linkUrl);
            }
         }
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
