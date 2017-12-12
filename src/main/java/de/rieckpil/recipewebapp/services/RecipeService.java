package de.rieckpil.recipewebapp.services;

import de.rieckpil.recipewebapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    public Set<Recipe> getRecipies();

    public Recipe getRecipeById(Long id);
}
