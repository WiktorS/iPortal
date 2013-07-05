package controllers;

import play.mvc.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: wstasiak
 * Date: 05.07.13
 * Time: 14:23
 */
public class Systherm extends Controller {
    public static void status() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH.mm").format(new Date());
        String version = "1.0.0";
        String time = ""; //unused
        Integer code = 0;

        response.contentType = "application/xml";
        render(date, version, time, code);
    }
}
