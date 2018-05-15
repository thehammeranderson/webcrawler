package org.ganderson.app.webcrawler;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ganderson.app.webcrawler.exception.InvalidUrlException;
import org.ganderson.app.webcrawler.exception.SiteNotFoundException;
import org.ganderson.app.webcrawler.service.HttpService;
import org.ganderson.app.webcrawler.service.HttpServiceImpl;

public class SiteProcessor {
   HttpService webUtils = new HttpServiceImpl();

   public static void main(String[] args) {
      try {
         System.out.println(new SiteProcessor().crawlSite(args[0]));
      } catch (ArrayIndexOutOfBoundsException ae) {
         System.out.println("must provide a url parameter");
      } catch (InvalidUrlException | SiteNotFoundException | IOException e) {
         System.out.println(e.getMessage());
      }
   }

   public String crawlSite(String url) throws InvalidUrlException, SiteNotFoundException, IOException {
      validateUrl(url);
      webUtils.getElements(url);
      return "hello";
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
