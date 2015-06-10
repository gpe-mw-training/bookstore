package com.redhat.training.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.redhat.training.domain.Address;
import com.redhat.training.domain.CatalogItem;
import com.redhat.training.domain.Customer;
import com.redhat.training.domain.Payment;
import com.redhat.training.domain.PaymentType;
import com.redhat.training.domain.Promotion;
import com.redhat.training.domain.WishList;
import com.redhat.training.rules.RulesEngine;
import com.redhat.training.service.CatalogService;
import com.redhat.training.service.DiscountService;
import com.redhat.training.service.OrderService;
import com.redhat.training.service.RememberMeService;
import com.redhat.training.service.WishListService;

@Named
@SessionScoped
public class ShoppingCart implements Serializable {

	@Inject
	private CatalogService service;
	@Inject
	private OrderService orderService;
	@Inject
	private RememberMeService rememberMeService;
	@Inject
	private WishListService wishListService;
	@Inject
	private DiscountService discountService;
	@Inject
	private RulesEngine engine;
	
	private static final long serialVersionUID = 1L;

	private List<CatalogItem> items = new ArrayList<CatalogItem>();
	private List<CatalogItem> viewed = new ArrayList<CatalogItem>();
	private Set<Promotion> promotions = new HashSet<Promotion>();

	private Customer customer;
	private boolean loggedIn = false;
	private boolean shipsame;
	private Payment payment;
	private String promoCode;
	

	private BigDecimal discount = BigDecimal.ZERO;

	public String addItem(Integer itemid) {

		// if (!conversation.isTransient())
		// conversation.end();

		CatalogItem item = service.getItem(itemid);

		for (CatalogItem ci : items) {
			if (ci.getId() == item.getId()) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										"Item has already been added to the shopping bag."));
				return "buy?faces-redirect=true";
			}
		}

		items.add(item);
		engine.fire(items, promotions);

		return "buy?faces-redirect=true";

	}

	public String addToWishList(Integer itemid) {
		CatalogItem item = service.getItem(itemid);

		for (CatalogItem ci : items) {
			if (ci.getId() == item.getId()) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										"Item has already been added to the shopping bag."));
				return "index?faces-redirect=true";
			}
		}
		wishListService.addItem(customer, item);
		return "index?faces-redirect=true";

	}

	public String moveItemWishlist(CatalogItem item) {

		items.remove(item);

		wishListService.addItem(customer, item);
		if (items.size() == 0)
			discount = BigDecimal.ZERO;

		return "buy";
	}


	public String moveFromWishlist(CatalogItem item) {
		wishListService.removeItem(customer, item);
		items.add(item);

		if (items.size() == 0)
			discount = BigDecimal.ZERO;

		return "buy";
	}

	public String removeItem(CatalogItem item) {

		items.remove(item);
		if (items.size() == 0)
			discount = BigDecimal.ZERO;

		return "buy";
	}

	public String applyPromo() {
		String result = applyPromo(promoCode);
		promoCode="";
		return result;

	}
	
	public String applyPromo(String code) {
		Promotion promotionCode = discountService.getPromotionCode(code);
		promotions.add(promotionCode);
		promoCode="";
		discount= engine.fire(items, promotions,this);
		return "buy";
	}

	public List<CatalogItem> getItems() {
		return items;
	}

	public BigDecimal getCartTotal() {
		BigDecimal total = BigDecimal.ZERO;
		for (CatalogItem item : items) {
			total = total.add(item.getPrice());
		}

		return total;
	}

	public BigDecimal getOrderTotal() {
		return getCartTotal().subtract(discount);
	}

	public List<CatalogItem> getWishlist() {
		WishList wishList = wishListService.findByCustomer(customer);
		if (wishList != null) {
			return wishList.getAsCatalogItems();
		} else {
			return null;
		}

	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
		loggedIn = true;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}


	public BigDecimal getDiscount() {
		return discount;
	}

	public String logout() {
		if (customer != null) {
			rememberMeService.logout(customer);
			customer = null;
			loggedIn = false;
		}
		return "index?faces-redirect=true";
	}

	public String prepareOrder() {

		if (customer.getBillingAddress() == null)
			customer.setBillingAddress(new Address());
		if (customer.getShippingAddress() == null)
			customer.setShippingAddress(new Address());

		return "order?faces-redirect=true";
	}

	public String collectPayment() {
		if (payment == null)
			payment = new Payment();

		return "payment?faces-redirect=true";
	}

	public String review() {
		return "review?faces-redirect=true";
	}

	public String createOrder() {

		orderService.placeOrder(this);
		items.clear();
		payment = null;
		shipsame = false;
		discount =BigDecimal.ZERO;

		return "confirm?faces-redirect=true";
	}

	public boolean isShipsame() {
		return shipsame;
	}

	public void setShipsame(boolean shipsame) {
		this.shipsame = shipsame;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public PaymentType[] getPaymentTypes() {
		return PaymentType.values();
	}


	public List<CatalogItem> getViewed() {
		return viewed;
	}

	public void setViewed(List<CatalogItem> viewed) {
		this.viewed = viewed;
	}

	public Set<Promotion> getPromotions() {
		return promotions;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	
}
