package controllers;

import org.apache.commons.lang.StringUtils;
import play.Logger;
import play.Play;
import play.libs.WS;
import play.mvc.Controller;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;

public class CapabilitiesGetter extends Controller{

    public static void getCapabilities(String serviceUrl) {
        try {
            renderXml(WS.url(buildQueryString(decode(serviceUrl))).timeout("5s").get().getString());
        } catch (UnsupportedEncodingException e) {
            Logger.info("CapabilitiesGetter::getCapabilities: Badly encoded URL: " + serviceUrl);
        } catch(RuntimeException e) {
            Logger.info("CapabilitiesGetter::getCapabilities: Illegal URL: " + serviceUrl);
        } catch (MalformedURLException e) {
            Logger.info("CapabilitiesGetter::getCapabilities: Malformed URL: " + serviceUrl);
        }

    }

    private static String buildQueryString(String serviceUrl) throws MalformedURLException {
        if (!serviceUrl.startsWith("http://")) {
            serviceUrl = "http://" + serviceUrl;
        }
        URL url = new URL(serviceUrl);
        ArrayList<String> queryArray = new ArrayList<String>();
        queryArray.add(url.getQuery());
        queryArray.add("service=WMS");
        queryArray.add("request=getCapabilities");
        return url.getProtocol() + "://" + url.getAuthority() + "/" + url.getPath() + "?" + StringUtils.join(queryArray, "&");
    }

    private static String decode(String utf8Encoded) throws UnsupportedEncodingException {
        return URLDecoder.decode(utf8Encoded, "UTF-8").replace(slashReplacement(), "/");
    }

    private static String slashReplacement() {
        return Play.configuration.getProperty("url.slash_replacement");
    }
}
