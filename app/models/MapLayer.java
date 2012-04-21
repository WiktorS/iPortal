package models;

import play.db.ebean.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;

@Entity
public class MapLayer extends Model {

    @Id
    public Long id;

    @Required
    public String name;

    @Required
    public String displayName;

    @Required
    public Boolean defaultVisible = false;

    @Required
    public Boolean canBeUsed = true;

    @ManyToOne
    public MapWMS mapWMS;

    public static Model.Finder<Long,MapLayer> find = new Model.Finder(Long.class, MapLayer.class);
}
