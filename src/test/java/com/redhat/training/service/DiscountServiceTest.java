package com.redhat.training.service;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.training.BaseWebTestTemplate;
import com.redhat.training.domain.Promotion;

@RunWith(Arquillian.class)
public class DiscountServiceTest extends BaseWebTestTemplate {

	@Inject
	private DiscountService service;

	@Test
	public void testValidDiscount() {
		Promotion discount = service.getPromotionCode("BIG61");
		Assert.assertNotNull(discount);

	}
}
