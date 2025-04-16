package fs.item.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fs.item.entity.Item;
import fs.item.entity.Role;
import fs.item.entity.User;
import fs.item.exception.ItemNotFoundException;
import fs.item.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserClient userClient;

    private void checkIfAdmin(Integer userId) {
        User user = userClient.getUserById(userId);
        log.info("Checking if user {} is admin", userId);
        if (!Role.ADMIN.equals(user.getRole())) {
            log.warn("User {} is not admin. Access denied.", userId);
            throw new RuntimeException("Only admin can perform this action");
        }
    }

    @Override
    public Item addItem(Item item, Integer userId) {
        checkIfAdmin(userId);
        log.info("Admin {} is adding item: {}", userId, item.getName());
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Integer itemid, Item item, Integer userId) {
        checkIfAdmin(userId);
        log.info("Admin {} is updating item with id {}", userId, itemid);
        Item existing = itemRepository.findById(itemid)
            .orElseThrow(() -> {
                log.error("Item with id {} not found", itemid);
                return new ItemNotFoundException(itemid);
            });
        existing.setName(item.getName());
        existing.setPrice(item.getPrice());
        existing.setDescription(item.getDescription());
        return itemRepository.save(existing);
    }

    @Override
    public List<Item> getAllItems() {
        log.info("Fetching all items");
        return itemRepository.findAll();
    }

    @Override
    public Item getItemById(Integer itemid) {
        log.info("Fetching item with id {}", itemid);
        return itemRepository.findById(itemid)
            .orElseThrow(() -> {
                log.error("Item with id {} not found", itemid);
                return new ItemNotFoundException(itemid);
            });
    }

    @Override
    public void deleteItem(Integer itemid) {
        log.info("Deleting item with id {}", itemid);
        if (!itemRepository.existsById(itemid)) {
            log.error("Item with id {} not found for deletion", itemid);
            throw new ItemNotFoundException(itemid);
        }
        itemRepository.deleteById(itemid);
    }
}
