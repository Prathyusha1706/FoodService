package fs.item.service;

import java.util.List;

import fs.item.entity.Item;

public interface ItemService {
	 Item addItem(Item item, Integer userId);
	    Item updateItem(Integer itemid, Item item, Integer userId);
	    List<Item> getAllItems();
	    Item getItemById(Integer itemid);
	    void deleteItem(Integer itemid);

}