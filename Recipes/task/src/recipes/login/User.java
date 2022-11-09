package recipes.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import recipes.Recipe;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotEmpty
    @NotNull
    @Pattern(regexp = ".+@.+\\..+")
    private String email;

    @NotBlank
    @NotEmpty
    @NotNull
    @Size(min=8, max=100)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Recipe> recipes = new ArrayList<>();

}

