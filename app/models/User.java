package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;


@Entity
public class User extends Model {
    
    public String  pass;
    public String  email;
    public Date    dateRegistered;
    public String  activationKey;
    public String  fName;
    public String  lName;
    public Integer status;
    
    public User(String email, String pass, String fName, String lName) {
        this.email = email;
        this.pass = pass;
        this.fName = fName;
        this.lName = lName;
    }
    
    public static User connect(String email, String pass) {
        return find("byEmailAndPass", email, pass).first();
    }
}
