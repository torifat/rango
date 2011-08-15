package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;


public class Application extends Controller {
    
    public static void index() {
    
        Category root = new Category("Root").save();
        Logger.debug("Category %s added with id %d", root.name, root.id);
        new Category("Sub1", root.id).save();
        
        render();
    }
}