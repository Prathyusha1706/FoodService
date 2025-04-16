package fs.menu.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import fs.menu.entity.Item;

@FeignClient(name = "ITEM-SERVICE", url = "http://localhost:9003")
public interface ItemClient {
    @GetMapping("/items")
    List<Item> getAllItems();
}