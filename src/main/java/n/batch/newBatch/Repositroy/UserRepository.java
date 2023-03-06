package n.batch.newBatch.Repositroy;

import n.batch.newBatch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
