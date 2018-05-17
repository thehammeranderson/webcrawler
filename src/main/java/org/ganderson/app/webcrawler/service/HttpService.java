package org.ganderson.app.webcrawler.service;

import java.io.IOException;

import org.ganderson.app.webcrawler.data.Page;
import org.ganderson.app.webcrawler.exception.SiteNotFoundException;

public interface HttpService {
   public Page parsePage(String url) throws IOException, SiteNotFoundException;
}
