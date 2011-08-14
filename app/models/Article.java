package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;


@Entity
public class Article extends Model {
    
    public Date          postDate;
    public String        title;
    @Lob
    public String        content;
    public Date          modifyDate;
    @ManyToOne
    public User          author;
    
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    public List<Comment> comments;
    
    public Article(String title, String content, User author) {
        this.comments = new ArrayList<Comment>();
        this.author = author;
        this.content = content;
        this.title = title;
        this.postDate = new Date();
        this.modifyDate = new Date();
    }
}
