package org.ganderson.app.webcrawler.config;

import org.ganderson.app.webcrawler.SiteProcessor;
import org.ganderson.app.webcrawler.service.HttpService;
import org.ganderson.app.webcrawler.service.HttpServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ApplicationConfig {

   @Bean
   public HttpService httpService() {
      HttpService httpService = new HttpServiceImpl();
      return httpService;
   }

   @Bean
   @Scope("prototype")
   public SiteProcessor siteProcessor() {
      SiteProcessor siteProcessor = new SiteProcessor();
      return siteProcessor;
   }
}
