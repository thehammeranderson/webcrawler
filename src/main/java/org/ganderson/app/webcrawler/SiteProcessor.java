package org.ganderson.app.webcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

   public String crawlSite(String url) throws InvalidUrlException, SiteNotFoundException, IOException {
      validateUrl(url);
      List<String> mainList = new ArrayList<String>();
      Map<ElementType, List<String>> elementMap = httpService.getElements(url);
      mainList.addAll(elementMap.get(ElementType.LINK));
      mainList.addAll(elementMap.get(ElementType.IMAGE));
      return mainList.toString();
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
