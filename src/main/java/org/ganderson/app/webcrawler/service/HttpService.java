package org.ganderson.app.webcrawler.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.ganderson.app.webcrawler.ElementType;
import org.ganderson.app.webcrawler.exception.SiteNotFoundException;

public interface HttpService {
   public Map<ElementType, List<String>> getElements(String url) throws IOException, SiteNotFoundException;
}
