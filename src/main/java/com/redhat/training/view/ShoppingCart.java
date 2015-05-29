package com.redhat.training.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
	private RulesEngine engine;
	
	private static final long serialVersionUID = 1L;

	private List<CatalogItem> items = new ArrayList<CatalogItem>();
	private List<CatalogItem> viewed = new ArrayList<CatalogItem>();
	private List<Promotion> promotions = new ArrayList<Promotion>();

	private Customer customer;
	private boolean loggedIn = false;
	private String promoCode;
	private boolean shipsame;
	private Payment payment;
	private String appliedPromoCode;

	private BigDecimal discount = new BigDecimal("0");

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
			discount = new BigDecimal(0);

		return "buy";
	}

	public void applyPromoCode() {
		promoCode = "big61";
	}

	public String moveFromWishlist(CatalogItem item) {
		wishListService.removeItem(customer, item);
		items.add(item);

		if (items.size() == 0)
			discount = new BigDecimal(0);

		return "buy";
	}

	public String removeItem(CatalogItem item) {

		items.remove(item);
		if (items.size() == 0)
			discount = new BigDecimal(0);

		return "buy";
	}

	public String applyPromo() {
		if ("big61".equals(promoCode)) {
			FacesContext.getCurrentInstance().addMessage("coupon:promo",
					new FacesMessage("", "Promotion code applied."));
			discount = discount.add(new BigDecimal(1.00));
			appliedPromoCode = promoCode;
			promoCode = "";
		} else {
			FacesContext.getCurrentInstance().addMessage("coupon:promo",
					new FacesMessage("", "Promotion code invalid."));
		}

		return "buy";
	}

	public List<CatalogItem> getItems() {
		return items;
	}

	public BigDecimal getCartTotal() {
		BigDecimal total = new BigDecimal("0");
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

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
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
		appliedPromoCode = "";
		payment = null;
		shipsame = false;
		discount = new BigDecimal(0);

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

	public String getAppliedPromoCode() {
		return appliedPromoCode;
	}

	public List<CatalogItem> getViewed() {
		return viewed;
	}

	public void setViewed(List<CatalogItem> viewed) {
		this.viewed = viewed;
	}

}
