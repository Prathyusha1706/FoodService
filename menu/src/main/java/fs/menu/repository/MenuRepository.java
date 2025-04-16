package fs.menu.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fs.menu.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
}
