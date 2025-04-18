package fs.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fs.user.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByEmail(String email);


}
