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
    
    @ManyToMany(cascade = CascadeType.PERSIST)
    public Set<Tag>      tags;
    
    public Article(String title, String content, User author) {
        this.comments = new ArrayList<Comment>();
        this.tags = new TreeSet<Tag>();
        this.author = author;
        this.content = content;
        this.title = title;
        this.postDate = new Date();
        this.modifyDate = new Date();
    }
    
    public Article addComment(String author, String content, String email, String ip, String uri, String agent, Comment parent) {
        Comment newComment = new Comment(this, author, content, email, ip, uri, agent, parent).save();
        this.comments.add(newComment);
        this.save();
        return this;
    }
    
    public Article tagItWith(String name) {
        tags.add(Tag.findOrCreateByName(name));
        return this;
    }
    
    public static List<Article> findTaggedWith(String... tags) {
        return Article
                .find("select distinct a from Article a join a.tags as t where t.name in (:tags) group by a.id, a.author, a.title, a.content, a.postDate having count(t.id) = :size")
                .bind("tags", tags).bind("size", tags.length).fetch();
    }
}
