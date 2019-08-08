package org.ganderson.app.webcrawler.config;

import org.ganderson.app.webcrawler.service.HttpService;
import org.ganderson.app.webcrawler.service.HttpServiceImpl;
import org.springframework.context.annotation.Bean;

public class ApplicationConfig {

   @Bean
   public HttpService httpService() {
      HttpService httpService = new HttpServiceImpl();
      return httpService;
   }

}
