package recipes;

import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class RecipeService {
    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    public Recipe saveRecipe(Recipe recipes) {
       return recipeRepository.save(recipes);
    }

    public void deleteRecipeById(Long id) {
        if (!existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        recipeRepository.deleteById(id);
    }

    public Optional<Recipe> getRecipe(Long id) {
        if (!existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return recipeRepository.findById(id);
    }

    public Boolean existsById(Long id) {
        return recipeRepository.existsById(id);
    }
    public Boolean existsByName(String name) {
        return recipeRepository.existsByName(name);
    }
    public Boolean existsByCategory(String category) {
        return recipeRepository.existsByCategory(category);
    }
    public Recipe updateRecipe(Long id, Recipe updatedRecipe) {
        if (id == null || !existsById(id) || updatedRecipe == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return recipeRepository.findById(id).map(recipe -> {
            recipe.setName(updatedRecipe.getName());
            recipe.setDescription(updatedRecipe.getDescription());
            recipe.setDirections(updatedRecipe.getDirections());
            recipe.setDate(updatedRecipe.getDate());
            recipe.setIngredients(updatedRecipe.getIngredients());
            return recipeRepository.save(recipe);
        }).orElseGet(() -> {
            updatedRecipe.setId(id);
            return recipeRepository.save(updatedRecipe);
        });
    }

    public List<Recipe> searchRecipe(String name, String category) {
        if (existsByCategory(category)) {
            return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
        }
        if (existsByName(name)) {
            return recipeRepository.findByNameIgnoreCaseContainsOrderByDateDesc(name);
        }
        if (name == null && category == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return Collections.emptyList();
    }
}
