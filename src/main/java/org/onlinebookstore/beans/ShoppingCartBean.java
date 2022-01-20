package org.onlinebookstore.beans;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.onlinebookstore.dao.BookDAO;
import org.onlinebookstore.model.Book;
import org.onlinebookstore.model.CartItem;

@Model
public class ShoppingCartBean {

	@Inject
    private BookDAO bookDao;

    @Inject
    private ShoppingCart cart;

    @Transactional
    public String add(Integer id) {
    	Book book = bookDao.findById(id);
        CartItem item = new CartItem(book);
        cart.add(item);

        return "cart?faces-redirect=true";
    }
    
    public List<CartItem> getItems(){
    	return cart.getItems();
    }
    
    public void remove(CartItem item) {
    	cart.remove(item);
    }
}
