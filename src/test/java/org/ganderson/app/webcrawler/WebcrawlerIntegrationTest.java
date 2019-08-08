package org.ganderson.app.webcrawler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.ganderson.app.webcrawler.config.ApplicationConfig;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ApplicationConfig.class)
public class WebcrawlerIntegrationTest {
   @Test
   public void testKnowledgeFactor() throws Exception {
      String[] urls = { "https://knowledgefactor.com" };
      MainApp.main(urls);

      File sitemap = new File("sitemap.txt");
      assertTrue("file doesn't exist", sitemap.exists());

      try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("sitemap.txt"), "utf-8"))) {
         assertEquals("first line in file is incorrect", "Page processed: https://knowledgefactor.com", reader.readLine());
         assertEquals("second line in file is incorrect", "  Links found:", reader.readLine());
         reader.readLine();
         assertEquals("fourth line in file is incorrect", "    * https://amplifire.com/leadership/", reader.readLine());

         String line;
         boolean foundImagesLine = false;
         while ((line = reader.readLine()) != null) {
            if (line.equals("  Images found:")) {
               foundImagesLine = true;
               assertEquals("the first image found was incorrect", "   * https://amplifire.com/wp-content/uploads/2019/01/CHM-Universal-600.png", reader.readLine());
               break;
            }
         }

         assertTrue("didn't return images line", foundImagesLine);
      }
   }
}
