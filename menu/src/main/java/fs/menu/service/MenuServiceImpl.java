package fs.menu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fs.menu.clients.ItemClient;
import fs.menu.clients.UserClient;
import fs.menu.entity.Item;
import fs.menu.entity.Menu;
import fs.menu.entity.Role;
import fs.menu.entity.User;
import fs.menu.exception.UserNotFoundException;
import fs.menu.repository.MenuRepository;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private ItemClient itemClient;

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public Menu viewMenu(Integer userId) {
        User user = userClient.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }

        if (user.getRole() != Role.ADMIN && user.getRole() != Role.CUSTOMER) {
            throw new RuntimeException("Unauthorized to view menu");
        }

        List<Item> items = itemClient.getAllItems();

        Menu menu = new Menu();
        menu.setMenuType("FULL_MENU");
        menu.setItems(items);
        return menu;
    }
}
