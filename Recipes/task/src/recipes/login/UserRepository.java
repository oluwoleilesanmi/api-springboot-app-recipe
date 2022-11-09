package recipes.login;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Boolean existsByEmail(String email);

    User findByEmail(String email);


}