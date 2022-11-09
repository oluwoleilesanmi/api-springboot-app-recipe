package recipes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.login.UserRepository;


import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/recipe")
public class RecipeController {
    private RecipeService recipeService;

    private UserRepository userRepository;

    @Autowired
    public RecipeController(RecipeService recipeService, UserRepository userRepository) {

        this.recipeService = recipeService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity getRecipe(@PathVariable @Min(1) Long id, @AuthenticationPrincipal UserDetails details) {
        return RecipeUtil.customRecipeWithOutIdOnly(recipeService.getRecipe(id));
    }

    @PostMapping("/new")
    public ResponseEntity postRecipe(@RequestBody @Valid Recipe recipe, @AuthenticationPrincipal UserDetails details) {
        if (userRepository.findByEmail(details.getUsername()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        recipe.setUser(userRepository.findByEmail(details.getUsername()));
        return RecipeUtil.customRecipeWithIdOnly(recipeService.saveRecipe(recipe));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable @Min(1) Long id, @AuthenticationPrincipal UserDetails details) {
        if (!details.getUsername().equals(recipeService.getRecipe(id).get().getUser().getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        recipeService.deleteRecipeById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateRecipe(@PathVariable @Min(1) Long id, @RequestBody @Valid Recipe recipe, @AuthenticationPrincipal UserDetails details) {
        if (!details.getUsername().equals(recipeService.getRecipe(id).get().getUser().getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        recipeService.updateRecipe(id, recipe);
    }

    @GetMapping("/search")
    public ResponseEntity searchRecipe(@RequestParam(required = false) String name, @RequestParam(required = false) String category) {
        return RecipeUtil.customRecipeWithOutIdOnly(recipeService.searchRecipe(name, category));
    }
}
