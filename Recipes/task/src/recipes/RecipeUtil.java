package recipes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

public class RecipeUtil {

    public static ResponseEntity customRecipeWithOutIdOnly(Optional<Recipe> recipe) {
        Map<String, Object> json = new HashMap<>();
        json.put("name",recipe.get().getName());
        json.put("description",recipe.get().getDescription());
        json.put("ingredients",recipe.get().getIngredients());
        json.put("category",recipe.get().getCategory());
        json.put("date",recipe.get().getDate());
        json.put("directions",recipe.get().getDirections());
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    public static ResponseEntity customRecipeWithOutIdOnly(List<Recipe> recipe) {
        List<Map<String, Object>> list = new ArrayList<>();
        for(Recipe r : recipe) {
            Map<String, Object> json = new HashMap<>();
            json.put("name",r.getName());
            json.put("description",r.getDescription());
            json.put("ingredients",r.getIngredients());
            json.put("category",r.getCategory());
            json.put("date",r.getDate());
            json.put("directions",r.getDirections());
            list.add(json);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    public static ResponseEntity customRecipeWithIdOnly(Recipe recipe) {
        Map<String, Object> json = new HashMap<>();
        json.put("id",recipe.getId());
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}
