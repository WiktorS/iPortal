package models;

public class MapServiceCollection {

    public static MapServiceCollection getInstance() {
        return new MapServiceCollection();
    }

    public Boolean canBeUsed(MapService service) {
        return canBeUsed(service, null);
    }

    public Boolean canBeUsed(MapService service, String installation) {
        if (installation != null){
            if (0 == service.mapSource.name.compareToIgnoreCase("Systherm-Info")) {
                if (0 != service.name.compareToIgnoreCase(installation)) {
                    return false;
                }
            }
        }
        for (MapLayer layer : service.layers) {
            if (layer.canBeUsed) {
                return true;
            }
        }
        return false;
    }

    public MapService findByName(String name) {
        return MapService.find("lower(name) = ?", name.toLowerCase()).first();
    }

}
