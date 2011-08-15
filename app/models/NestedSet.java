package models;

import play.data.validation.Min;
import play.db.jpa.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;


/**
 * Base class for JPA model objects Automatically provide a @Id Long id field
 */
@MappedSuperclass
public class NestedSet extends GenericModel {
    
    @Id
    @GeneratedValue
    public Long    id;
    
    public String  name;
    
    @Min(1)
    private Long   lft;
    @Min(2)
    private Long   rgt;
    protected Long parent;
    
    
    public Long getId() {
    
        return id;
    }
    
    @Override
    public Object _key() {
    
        return getId();
    }
    
    @PrePersist
    public void insertNode() {
    
        NestedSet parentNode = JPA.em().find(this.getClass(), this.parent);
        
        JPA.em().createQuery("UPDATE " + this.getClass().getSimpleName() + " SET rgt = rgt + 2 WHERE rgt >= " + parentNode.rgt).executeUpdate();
        JPA.execute("UPDATE " + this.getClass().getSimpleName() + " SET lft = lft + 2 WHERE rgt >= " + parentNode.rgt + " AND lft <> 1 AND lft > "
                + parentNode.lft + "");
        this.lft = parentNode.rgt;
        this.rgt = this.lft + 1;
    }
}
