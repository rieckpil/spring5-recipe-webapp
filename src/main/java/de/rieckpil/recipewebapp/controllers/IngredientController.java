package de.rieckpil.recipewebapp.controllers;

import de.rieckpil.recipewebapp.commands.IngredientCommand;
import de.rieckpil.recipewebapp.domain.UnitOfMeasure;
import de.rieckpil.recipewebapp.services.IngredientService;
import de.rieckpil.recipewebapp.services.RecipeService;
import de.rieckpil.recipewebapp.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {

        log.debug("Getting ingredient list for id: " + recipeId);

        model.addAttribute("recipe", recipeService.getCommandById(Long.valueOf(recipeId)));

        return "recipe/ingredient/list";

    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {

        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

        return "recipe/ingredient/show";

    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model){

        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
        model.addAttribute("uomList", unitOfMeasureService.getAllUnitOfMeasures());

        return "recipe/ingredient/ingredientform";
    }

    @PostMapping
    @RequestMapping("/recipe/{recipeId}/ingredient")
    public String updateRecipeIngredient(@ModelAttribute IngredientCommand ingredientCommand){

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" +savedCommand.getId() +  "/show";
    }
}
