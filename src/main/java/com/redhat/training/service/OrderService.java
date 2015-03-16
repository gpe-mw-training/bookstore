package com.redhat.training.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.redhat.training.domain.CatalogItem;
import com.redhat.training.domain.Order;
import com.redhat.training.domain.OrderItem;
import com.redhat.training.view.ShoppingCart;

@Stateless
public class OrderService {
	
	@PersistenceContext
	private EntityManager mgr;

	public void placeOrder(final ShoppingCart cart) {
		
		Order order = new Order();
		
		order.setCustomer(mgr.merge(cart.getCustomer()));
		
		order.setPayment(cart.getPayment());
		order.setPromoCode(cart.getAppliedPromoCode());
		order.setDiscount(cart.getDiscount());
		
		for(CatalogItem item : cart.getItems()) {
			CatalogItem _item = mgr.merge(item);
			OrderItem oi = new OrderItem();
			oi.setExtPrice(_item.getPrice());
			oi.setQuantity(1);
			oi.setItem(_item);
		}
		mgr.persist(order);
		order.getCustomer().getOrders().add(order);
	}
}
