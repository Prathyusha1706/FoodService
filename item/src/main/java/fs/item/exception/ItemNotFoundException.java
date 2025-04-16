package fs.item.exception;

public class ItemNotFoundException extends RuntimeException{
	public ItemNotFoundException(Integer itemid) {
		 super("Item not found with ID: " + itemid);
	}

}
