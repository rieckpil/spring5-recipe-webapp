package de.rieckpil.recipewebapp.services;

import de.rieckpil.recipewebapp.commands.IngredientCommand;
import de.rieckpil.recipewebapp.converters.IngredientCommandToIngredient;
import de.rieckpil.recipewebapp.converters.IngredientToIngredientCommand;
import de.rieckpil.recipewebapp.domain.Ingredient;
import de.rieckpil.recipewebapp.domain.Recipe;
import de.rieckpil.recipewebapp.repositories.RecipeRepository;
import de.rieckpil.recipewebapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            log.error("no recipe found for id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> optionalIngredientCommand = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if (!optionalIngredientCommand.isPresent()) {
            log.error("no ingredient with id: " + ingredientId + " found in recipe with id: " + recipeId);
        }

        return optionalIngredientCommand.get();
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (!recipeOptional.isPresent()) {
            log.error("no recipe found for id: " + ingredientCommand.getRecipeId());
            return new IngredientCommand();
        } else {

            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("UOM not found!")));
            } else {
                recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().
                    stream()
                    .filter(recipeIngredient -> recipeIngredient.getId().equals(ingredientCommand.getId()))
                    .findFirst()
                    .get());
        }
    }
}
