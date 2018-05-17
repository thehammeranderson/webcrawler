package org.ganderson.app.webcrawler.data;

import java.util.Set;

public class Page {
   private Set<String> imageUrls;
   private String pageUrl;
   private Set<String> linkUrls;

   public Page(String url) {
      pageUrl = url;
   }

   public Set<String> getImageUrls() {
      return imageUrls;
   }

   public void setImageUrls(Set<String> imageUrls) {
      this.imageUrls = imageUrls;
   }

   public String getPageUrl() {
      return pageUrl;
   }

   public void setPageUrl(String pageUrl) {
      this.pageUrl = pageUrl;
   }

   public Set<String> getLinkUrls() {
      return linkUrls;
   }

   public void setLinkUrls(Set<String> linkUrls) {
      this.linkUrls = linkUrls;
   }

}
