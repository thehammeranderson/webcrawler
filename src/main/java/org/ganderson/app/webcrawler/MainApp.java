package org.ganderson.app.webcrawler;

import org.ganderson.app.webcrawler.config.ApplicationConfig;
import org.ganderson.app.webcrawler.data.Page;
import org.ganderson.app.webcrawler.data.SiteProcessor;
import org.ganderson.app.webcrawler.exception.InvalidUrlException;
import org.ganderson.app.webcrawler.exception.SiteNotFoundException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@Import(ApplicationConfig.class)
public class MainApp {
    private static Integer urlProcessingLimit;

   public static void main(String[] args) throws URISyntaxException {
      if (args.length == 0) {
         System.out.println("must provide a url parameter");
          return;
      }

      Logger root = Logger.getLogger("");
      root.setLevel(Level.WARNING);

      ApplicationContext ctx = SpringApplication.run(MainApp.class, args);
      
      try {
         SiteProcessor siteProcessor = ctx.getBean(SiteProcessor.class);

          if (args.length == 2) {
              urlProcessingLimit = Integer.parseInt(args[1]);
          }
          List<Page> pages = siteProcessor.crawlSite(args[0], urlProcessingLimit);

          try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("sitemap.txt"), StandardCharsets.UTF_8))) {
            for (Page page : pages) {
               writer.write("Page processed: " + page.getPageUrl());
               writer.newLine();
               writer.write("  Links found:");
               writer.newLine();
               for (String url : page.getLinkUrls()) {
                  writer.write("    * " + url);
                  writer.newLine();
               }

               writer.write("  Images found:");
               writer.newLine();
               for (String url : page.getImageUrls()) {
                  writer.write("   * " + url);
                  writer.newLine();
               }
            }
         }
         System.out.println("processing complete.  A file name sitemap.txt with your results has been created in this directory.");
      } catch (InvalidUrlException | SiteNotFoundException | IOException e) {
         System.out.println(e.getMessage());
      }
   }
}
