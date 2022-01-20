package org.onlinebookstore.beans;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.onlinebookstore.dao.BookDAO;
import org.onlinebookstore.model.Promo;
import org.onlinebookstore.websockets.PromoEndpoint;

@Model
public class AdminPromoBean {

    private Promo promo = new Promo();
    
    @Inject
    private PromoEndpoint promoEndPoint;
    @Inject
    private BookDAO bookDao;

    @Transactional
    public String send() {
    	bookDao.promoUpdatePrice(promo.getBook().getId(), promo.getDiscount());
    	promoEndPoint.send(promo);
    	return "promo.xhtml?faces-redirect=true";
    }

    public Promo getPromo() {
        return promo;
    }

    public void setPromo(Promo promo) {
        this.promo = promo;
    }
}
