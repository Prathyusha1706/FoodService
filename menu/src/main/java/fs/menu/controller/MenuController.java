package fs.menu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fs.menu.entity.Menu;
import fs.menu.service.MenuService;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/view/{userId}")
    public ResponseEntity<Menu> viewMenu(@PathVariable Integer userId) {
        Menu menu = menuService.viewMenu(userId);
        return ResponseEntity.ok(menu);
    }
}

