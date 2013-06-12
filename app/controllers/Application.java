package controllers;

import models.MapLocation;
import models.MapLocationCollection;
import models.MapSource;
import models.MapSourceCollection;
import play.mvc.Controller;
import play.mvc.With;

import java.util.List;

@With(CommonData.class)
public class Application extends Controller {

    public static void index(Long x, Long y, Long z) {
        Application.sendArgumentsToMap(x, y, z);
        List<MapSource> sources = MapSourceCollection.getInstance().allSortedBy("sort, id");
        List<MapLocation> locations = MapLocationCollection.getInstance().topLevel();
        render(sources, locations);
    }

    private static void sendArgumentsToMap(Long cartographerXCoordinate, Long cartographerYCoordinate, Long zoomLevel) {
        if (cartographerXCoordinate==null || cartographerYCoordinate==null || zoomLevel==null) {
            return;
        }
        renderArgs.put("mapInitialX", cartographerYCoordinate);
        renderArgs.put("mapInitialY", cartographerXCoordinate);
        renderArgs.put("mapInitialZ", zoomLevel);
    }

}
