package models;

import java.util.List;

public class MapSourceCollection {

    public static MapSourceCollection getInstance() {
        return new MapSourceCollection();
    }

    public List<MapSource> allSortedBy(String column) {
        return MapSource.find("order by " + column).fetch();
    }

    public Boolean canBeUsed(MapSource source) {
        for (MapService service : source.webMapServices) {
            if (MapServiceCollection.getInstance().canBeUsed(service)) {
                return true;
            }
        }
        return false;
    }

    public Boolean canBeUsed(MapSource source, String installation) {
        if (installation != null) {
            MapSource installationSource = MapSourceCollection.getInstance().findByName("systherm_" + installation);
            String lowerName = source.name.toLowerCase();
            if (installationSource != null) {
                if (lowerName.matches("^systherm.*")){ // no '_' because 'systherm' source needs to by filter
                    if (0 != lowerName.compareToIgnoreCase("systherm_" + installation)) {
                        return false;
                    }
                }
            }
            else {
                if (lowerName.matches("^systherm_.*")){
                    return false;
                }
            }
        }
        return true;
    }

    public MapSource findByName(String installation) {
        return MapSource.find("lower(name) = ?", installation.toLowerCase()).first();
    }
}
