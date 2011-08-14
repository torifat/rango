package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;


@Entity
public class Comment extends Model {
    
    public String  author;
    public String  email;
    public String  ip;
    public String  uri;
    public Date    postedAt;
    @Lob
    public String  content;
    public String  agent;
    public Comment parent;
    @ManyToOne
    public Article article;
    
    public Comment(Article article, String author, String content, String email, String ip, String uri, String agent, Comment parent) {
        this.article = article;
        this.author = author;
        this.content = content;
        this.email = email;
        this.ip = ip;
        this.uri = uri;
        this.agent = agent;
        this.parent = parent;
        this.postedAt = new Date();
    }
}
