package ie.dq.web.model;

import java.util.LinkedList;
import java.util.List;

public class ShoppingCart {
	
	List<String> items = new LinkedList<String>();

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}
	
	private void addItem(String item){
		this.items.add(item);
	}
}
