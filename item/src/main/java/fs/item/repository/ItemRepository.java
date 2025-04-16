package fs.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fs.item.entity.Item;
@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{

}
