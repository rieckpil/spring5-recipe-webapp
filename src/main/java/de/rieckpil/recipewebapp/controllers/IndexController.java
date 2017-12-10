package de.rieckpil.recipewebapp.controllers;

import de.rieckpil.recipewebapp.domain.Category;
import de.rieckpil.recipewebapp.domain.UnitOfMeasure;
import de.rieckpil.recipewebapp.repositories.CategoryRepository;
import de.rieckpil.recipewebapp.repositories.UnitOfMeasureRepository;
import de.rieckpil.recipewebapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.text.html.Option;
import java.util.Optional;

@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"/","","/index"})
    public String getIndexPage(Model model){

        model.addAttribute("recipies", recipeService.getRecipies());

        return "index";
    }

}
