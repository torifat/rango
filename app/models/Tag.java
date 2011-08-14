package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;


@Entity
public class Tag extends Model implements Comparable<Tag> {
    
    public String name;
    
    private Tag(String name) {
        this.name = name;
    }
    
    public String toString() {
        return name;
    }
    
    public int compareTo(Tag otherTag) {
        return name.compareTo(otherTag.name);
    }
    
    public static Tag findOrCreateByName(String name) {
        Tag tag = Tag.find("byName", name).first();
        if (tag == null) {
            tag = new Tag(name);
        }
        return tag;
    }
    
    public static List<Map> getCloud() {
        List<Map> result = Tag.find(
                "select new map(t.name as tag, count(a.id) as pound) from Article a join a.tags as t group by t.name order by t.name").fetch();
        return result;
    }
}
