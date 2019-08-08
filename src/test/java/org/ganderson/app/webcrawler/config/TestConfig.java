package org.ganderson.app.webcrawler.config;

import org.ganderson.app.webcrawler.data.SiteProcessor;
import org.ganderson.app.webcrawler.service.HttpService;
import org.ganderson.app.webcrawler.service.HttpServiceTestImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

public class TestConfig {

   @Bean
   public HttpService httpService() {
      HttpService httpService = new HttpServiceTestImpl();
      return httpService;
   }

   @Bean
   @Scope("prototype")
   public SiteProcessor siteProcessor() {
      SiteProcessor siteProcessor = new SiteProcessor();
      return siteProcessor;
   }
}
