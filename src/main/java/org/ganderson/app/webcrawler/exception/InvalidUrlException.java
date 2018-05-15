package org.ganderson.app.webcrawler.exception;

@SuppressWarnings("serial")
public class InvalidUrlException extends Exception {

   public InvalidUrlException(String message) {
      super(message);
   }

}
