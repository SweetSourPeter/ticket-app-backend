package movie.app.repository;

import movie.app.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface UserRepository extends CrudRepository<UserInfo, Integer> {

    boolean existsByAccount(String account);

    boolean existsByEmail(String email);

    boolean existsByAccountOrEmail(String account, String email);

    boolean existsByAccountAndPassword(String account, String password);

}
