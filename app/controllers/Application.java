package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;


public class Application extends Controller {
    
    public static void index() {
    
        new Category("Test", (long) 1).save();
        render();
    }
    
}