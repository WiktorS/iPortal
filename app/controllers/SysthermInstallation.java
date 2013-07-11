package controllers;

import models.*;
import play.mvc.Controller;
import play.mvc.With;

import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: umberto
 * Date: 15.07.12
 * Time: 12:22
 * To change this template use File | Settings | File Templates.
 */

@With(CommonData.class)
public class SysthermInstallation extends Controller {

    public static void index(String installation, Boolean logo, Boolean sidebar) {
        if (logo == null)
            logo = true;
        if (sidebar == null)
            sidebar = true;
        installation = installation.replaceFirst("^www\\.", "");
        MapService service = MapServiceCollection.getInstance().findByName(installation);
        MapSource source = MapSourceCollection.getInstance().findByName("systherm_" + installation);
        if (service==null && source == null) {
            notFound(installation);
        }
        List<MapSource> sources = MapSourceCollection.getInstance().allSortedBy("sort, id");
        List<MapLocation> locations = MapLocationCollection.getInstance().topLevel();
        if (service == null) {
            service = source.webMapServices.iterator().next();
        }
        sendAdditionalArgumentsToTemplate(service);
        renderTemplate("Application/index.html", sources, locations, installation, logo, sidebar);
    }

    private static void sendAdditionalArgumentsToTemplate(MapService service) {
        renderArgs.put("mapInitialX", service.yCoordinate != null ? service.yCoordinate : MapSetting.getValue(MapSetting.MAP_INITIAL_Y_COORDINATE, "460000"));
        renderArgs.put("mapInitialY", service.xCoordinate != null ? service.xCoordinate : MapSetting.getValue(MapSetting.MAP_INITIAL_X_COORDINATE, "500000"));
        renderArgs.put("mapInitialZ", service.zoomLevel != null ? service.zoomLevel : MapSetting.getValue(MapSetting.MAP_INITIAL_Z, "0"));
        renderArgs.put("systhermInstallation", service.name);
        renderArgs.put("systhermInstallationTitle", service.armsTitle);
        renderArgs.put("systhermInstallationArms", service.coatOfArms);
        renderArgs.put("systhermSourceId", service.mapSource.id);
        renderArgs.put("systhermServiceId", service.id);
        MapLayer layer = MapLayerCollection.getInstance().getByNameFromMapService(service, "gminy");
        if (layer==null) {
            Iterator<MapLayer> mapLayerIterator = service.layers.iterator();
            layer = mapLayerIterator.next();
        }
        if (layer!=null) {
            renderArgs.put("systhermLayerId", layer.id);
        }
    }
}
