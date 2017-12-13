package de.rieckpil.recipewebapp.services;

import de.rieckpil.recipewebapp.commands.RecipeCommand;
import de.rieckpil.recipewebapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    public Set<Recipe> getRecipies();

    public Recipe getRecipeById(Long id);

    public RecipeCommand getCommandById(Long id);

    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
}
