package fs.item.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fs.item.entity.Item;
import fs.item.service.ItemService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/items")
@Slf4j
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<Item> addItem(@RequestBody Item item, @PathVariable Integer userId) {
        log.info("POST /items/add/{} - Adding new item", userId);
        return ResponseEntity.ok(itemService.addItem(item, userId));
    }

    @PutMapping("/update/{itemid}/{userId}")
    public ResponseEntity<Item> updateItem(@PathVariable Integer itemid, @RequestBody Item item, @PathVariable Integer userId) {
        log.info("PUT /items/update/{}/{} - Updating item", itemid, userId);
        return ResponseEntity.ok(itemService.updateItem(itemid, item, userId));
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        log.info("GET /items - Fetching all items");
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Integer itemid) {
        log.info("GET /items/{} - Fetching item by ID", itemid);
        return ResponseEntity.ok(itemService.getItemById(itemid));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Integer itemid) {
        log.info("DELETE /items/{} - Deleting item", itemid);
        itemService.deleteItem(itemid);
        return ResponseEntity.ok("Item deleted successfully");
    }
}
