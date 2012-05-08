package controllers;

import play.Logger;
import play.Play;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    @Before
    private static void commonData() {
        renderArgs.put("useArms", Boolean.parseBoolean(Play.configuration.getProperty("views.use_application_arms")));
        renderArgs.put("mapBoundingBoxLeft", Play.configuration.getProperty("map.bounding_box.left"));
        renderArgs.put("mapBoundingBoxBottom", Play.configuration.getProperty("map.bounding_box.bottom"));
        renderArgs.put("mapBoundingBoxRight", Play.configuration.getProperty("map.bounding_box.right"));
        renderArgs.put("mapBoundingBoxTop", Play.configuration.getProperty("map.bounding_box.top"));
        renderArgs.put("mapMinScale", Play.configuration.getProperty("map.min_scale"));
        renderArgs.put("mapMaxScale", Play.configuration.getProperty("map.max_scale"));
        renderArgs.put("mapNumZoomLevels", Play.configuration.getProperty("map.num_zoom_levels"));
        renderArgs.put("mapInitialX", Play.configuration.getProperty("map.initial.x"));
        renderArgs.put("mapInitialY", Play.configuration.getProperty("map.initial.y"));
        renderArgs.put("mapInitialZ", Play.configuration.getProperty("map.initial.z"));
    }

    public static void index() {
        List<MapSource> sources = MapSource.findAll();
        render(sources);
    }

}
