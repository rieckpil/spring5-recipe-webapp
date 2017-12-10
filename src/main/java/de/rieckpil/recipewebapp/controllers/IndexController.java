package de.rieckpil.recipewebapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"/","","/index"})
    public String getIndexPage(){
        System.out.println("hell world! 1234 1212");
        return "index";
    }

}
