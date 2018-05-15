package org.ganderson.app.webcrawler.exception;

@SuppressWarnings("serial")
public class SiteNotFoundException extends Exception {

   public SiteNotFoundException(String message) {
      super(message);
   }

}
