package models;

import play.*;
import play.data.validation.Min;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;


@Entity
public class Category extends NestedSet {
    
    public Category(String name) {
    
        this.name = name;
    }
    
    public Category(String name, Long parent) {
    
        this(name);
        this.parent = parent;
    }
}
