package org.ganderson.app.webcrawler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ganderson.app.webcrawler.config.ApplicationConfig;
import org.ganderson.app.webcrawler.exception.InvalidUrlException;
import org.ganderson.app.webcrawler.exception.SiteNotFoundException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
   public static void main(String[] args) {
      Logger root = Logger.getLogger("");
      root.setLevel(Level.WARNING);

      try (ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class)) {
         SiteProcessor siteProcessor = ctx.getBean(SiteProcessor.class);
         System.out.println(siteProcessor.crawlSite(args[0]));
      } catch (ArrayIndexOutOfBoundsException ae) {
         System.out.println("must provide a url parameter");
      } catch (InvalidUrlException | SiteNotFoundException | IOException e) {
         System.out.println(e.getMessage());
      }
   }
}
