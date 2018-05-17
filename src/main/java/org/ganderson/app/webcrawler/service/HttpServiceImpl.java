package org.ganderson.app.webcrawler.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.ganderson.app.webcrawler.data.Page;
import org.ganderson.app.webcrawler.exception.SiteNotFoundException;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HttpServiceImpl implements HttpService {
   static {
      TrustManager[] trustAllCertificates = new TrustManager[] { new X509TrustManager() {
         @Override
         public X509Certificate[] getAcceptedIssuers() {
            return null; // Not relevant.
         }

         @Override
         public void checkClientTrusted(X509Certificate[] certs, String authType) {
            // Do nothing. Just allow them all.
         }

         @Override
         public void checkServerTrusted(X509Certificate[] certs, String authType) {
            // Do nothing. Just allow them all.
         }
      } };

      HostnameVerifier trustAllHostnames = new HostnameVerifier() {
         @Override
         public boolean verify(String hostname, SSLSession session) {
            return true; // Just allow them all.
         }
      };

      try {
         System.setProperty("jsse.enableSNIExtension", "false");
         SSLContext sc = SSLContext.getInstance("SSL");
         sc.init(null, trustAllCertificates, new SecureRandom());
         HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
         HttpsURLConnection.setDefaultHostnameVerifier(trustAllHostnames);
      } catch (GeneralSecurityException e) {
         throw new ExceptionInInitializerError(e);
      }

      System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
   }

   public Page parsePage(String url) throws IOException, SiteNotFoundException {
      try {
         System.out.println("processing page: " + url);
         Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").referrer("http://www.google.com").get();
         Page page = new Page(url);
         Set<String> linkUrls = new HashSet<String>();

         Iterator<Element> iterator = doc.getElementsByTag("a").iterator();
         while (iterator.hasNext()) {
            Element element = iterator.next();
            linkUrls.add(element.absUrl("href"));
         }
         page.setLinkUrls(linkUrls);

         Set<String> imageUrls = new HashSet<String>();
         iterator = doc.getElementsByTag("img").iterator();
         while (iterator.hasNext()) {
            Element element = iterator.next();
            imageUrls.add(element.absUrl("src"));
         }
         page.setImageUrls(imageUrls);

         return page;
      } catch (HttpStatusException | UnsupportedMimeTypeException e) {
         System.out.println("error processing page: " + e.getMessage());
         return null;
      }
   }

}
