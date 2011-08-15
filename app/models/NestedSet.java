package models;

import play.data.validation.Min;
import play.db.jpa.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Query;


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
    
        // Get Parent Node
        NestedSet parentNode = JPA.em().find(this.getClass(), this.parent);
        
        Query q;
        
        // Update All Right Column Value
        q = JPA.em().createQuery("UPDATE " + this.getClass().getSimpleName() + " SET rgt = rgt + 2 WHERE rgt >= ?");
        q.setParameter(1, parentNode.rgt);
        q.executeUpdate();
        
        // Update All Left Column Value
        q = JPA.em().createQuery("UPDATE " + this.getClass().getSimpleName() + " SET lft = lft + 2 WHERE rgt >= ? AND lft <> 1 AND lft > ?");
        q.setParameter(1, parentNode.rgt);
        q.setParameter(2, parentNode.lft);
        q.executeUpdate();
        
        this.lft = parentNode.rgt;
        this.rgt = this.lft + 1;
    }
}
