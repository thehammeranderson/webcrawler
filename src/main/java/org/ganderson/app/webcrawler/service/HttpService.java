package org.ganderson.app.webcrawler.service;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.ganderson.app.webcrawler.ElementType;
import org.ganderson.app.webcrawler.exception.SiteNotFoundException;

public interface HttpService {
   public Map<ElementType, Set<String>> getElements(String url) throws IOException, SiteNotFoundException;
}
