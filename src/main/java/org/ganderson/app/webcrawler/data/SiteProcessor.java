package org.ganderson.app.webcrawler.data;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

   private List<Page> mainList = new ArrayList<Page>();
   private Set<String> processedUrlMap = new HashSet<String>();
   private String host;
   private String baseUrl;
   private int maxRecursion = 100;
   private int recursionCount = 0;

   public List<Page> crawlSite(String url) throws InvalidUrlException, SiteNotFoundException, IOException, URISyntaxException {
      validateUrl(url);
      baseUrl = url;
      host = new URL(url).getHost();
      processUrl(url);
      return mainList;
   }

   private void processUrl(String url) throws IOException, SiteNotFoundException, URISyntaxException {
      // standardize urls so both versions will equate
      if (url.endsWith("/")) {
         url = url.substring(0, url.length() - 1);
      }
      if (!processedUrlMap.contains(url) && recursionCount <= maxRecursion) {
         recursionCount++;

         if (recursionCount >= maxRecursion) {
            return;
         }

         processedUrlMap.add(url);
         Page page = httpService.parsePage(url);
         if (page == null) {
            return;
         }

         mainList.add(page);

         for (String linkUrl : page.getLinkUrls()) {
            URI aUrl = new URI(linkUrl);
            if (linkUrl.toLowerCase().startsWith("mailto:")) {
               continue;
            } else if (!linkUrl.startsWith("http://") && !linkUrl.startsWith("https://")) {
               // handle relative urls   
               String concatChar = "";
               if (!baseUrl.endsWith("/")) {
                  concatChar = "/";
               }
               processUrl(baseUrl + concatChar + linkUrl);
            } else if (host.contains(aUrl.getHost()) || aUrl.getHost().contains(host)) {
               processUrl(linkUrl);
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
