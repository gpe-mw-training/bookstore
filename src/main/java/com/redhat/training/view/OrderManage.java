package com.redhat.training.view;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.redhat.training.domain.Order;
import com.redhat.training.service.OrderService;

@Named
@ConversationScoped
public class OrderManage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private OrderService service;

	private Order orderSelected;
	
	@Inject
	private Conversation conversation;

	public List<Order> getAllOpenItems() {
		if(!conversation.isTransient()){
			conversation.end();
		}
		conversation.begin();
		return service.getAllOpenOrders();
	}

	public String getDetails(Order order){
		this.orderSelected=order;
		return "orderdetail";
	}

	public String sendOrder(){
		service.deliverOrder(orderSelected);
		setOrderSelected(null);
		conversation.end();
		return "ordermgmt";
	}
	
	public Order getOrderSelected() {
		return orderSelected;
	}

	public void setOrderSelected(Order orderSelected) {
		this.orderSelected = orderSelected;
	}

	
	
}
