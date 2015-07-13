package com.redhat.training.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
		order.setPromoCode(cart.getPromotions());
		order.setDiscount(cart.getDiscount());
		
		for(CatalogItem item : cart.getItems()) {
			CatalogItem _item = mgr.merge(item);
			OrderItem oi = new OrderItem();
			oi.setExtPrice(_item.getPrice());
			oi.setQuantity(1);
			oi.setItem(_item);
			order.getItems().add(oi);
		}
		mgr.persist(order);
		order.getCustomer().getOrders().add(order);
	}
	
	public void deliverOrder(Order order){
		order.deliver();
		mgr.merge(order);
	}
	
	public Order getOrderById(Integer id){
		CriteriaBuilder builder = mgr.getCriteriaBuilder();
		CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
		Root<Order> root = criteria.from(Order.class);
		TypedQuery<Order> query = mgr.createQuery(criteria.select(
				root).where(builder.equal(root.get("id"), true)));
		Order order = null;
		try{
			return query.getSingleResult();
		}catch(NonUniqueResultException e){
			return order;
		}

	}
	
	public List<Order> getAllOpenOrders(){
		CriteriaBuilder builder = mgr.getCriteriaBuilder();
		CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
		Root<Order> root = criteria.from(Order.class);
		root.fetch("items");
		TypedQuery<Order> query = mgr.createQuery(criteria.select(
				root).where(builder.equal(root.get("delivered"), false)));
		return query.getResultList();
	}
	
}
