package controllers;

import models.MapSetting;
import play.Play;
import play.mvc.Before;
import play.mvc.Controller;

/**
 * Created with IntelliJ IDEA.
 * User: wstasiak
 * Date: 14.06.12
 * Time: 10:50
 * Common code used among other controllers
 */
public class CommonData extends Controller {
    @Before
    private static void commonData() {
        renderArgs.put("useArms", Boolean.parseBoolean(MapSetting.findByKey(MapSetting.APPLICATION_ARMS).value));
        renderArgs.put("appOwner", MapSetting.getValue(MapSetting.APPLICATION_TITLE, "Systherm-Info"));
        renderArgs.put("mapBoundingBoxLeft", MapSetting.getValue(MapSetting.MAP_BOUNDINGBOX_LEFT, "130000"));
        renderArgs.put("mapBoundingBoxBottom", MapSetting.getValue(MapSetting.MAP_BOUNDINGBOX_BOTTOM, "120000"));
        renderArgs.put("mapBoundingBoxRight", MapSetting.getValue(MapSetting.MAP_BOUNDINGBOX_RIGHT, "980000"));
        renderArgs.put("mapBoundingBoxTop", MapSetting.getValue(MapSetting.MAP_BOUNDINGBOX_TOP, "780000"));
        renderArgs.put("mapResolutions", MapSetting.getValue(MapSetting.MAP_RESOLUTIONS, "1600,800,400,200,100,50,25,12.5,6.25,3.125,1.5625,0.78125"));
        renderArgs.put("mapMaxResolution", MapSetting.getValue(MapSetting.MAP_MAX_RESOLUTION, "1600"));
        renderArgs.put("mapZoomLevels", MapSetting.getValue(MapSetting.MAP_ZOOM_LEVELS, "16"));
        renderArgs.put("mapInitialX", MapSetting.getValue(MapSetting.MAP_INITIAL_Y_COORDINATE, "460000"));
        renderArgs.put("mapInitialY", MapSetting.getValue(MapSetting.MAP_INITIAL_X_COORDINATE, "500000"));
        renderArgs.put("mapInitialZ", MapSetting.getValue(MapSetting.MAP_INITIAL_Z, "0"));
        renderArgs.put("urlSlashReplacement", Play.configuration.getProperty("url.slash_replacement"));
        renderArgs.put("systhermSourceId", 0);
        renderArgs.put("systhermServiceId", 0);
        renderArgs.put("systhermLayerId", 0);
        renderArgs.put("sourceInstallation", "false");
    }
}
