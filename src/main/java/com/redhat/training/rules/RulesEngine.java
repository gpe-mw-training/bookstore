/*
 Copyright (c) 2015 Red Hat, Inc.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are
 met:

   1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above
   copyright notice, this list of conditions and the following
   disclaimer in the documentation and/or other materials provided
   with the distribution.

 THIS SOFTWARE IS PROVIDED BY RED HAT, INC. ``AS IS'' AND ANY EXPRESS
 OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL RED HAT, INC. BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.
 */
package com.redhat.training.rules;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.redhat.training.domain.CatalogItem;
import com.redhat.training.domain.Promotion;
import com.redhat.training.view.ShoppingCart;


@Singleton
@Startup
public class RulesEngine  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private KieContainer container;

	public Collection<Object> fire(Object ... facts) {

		KieSession ksession = container.newKieSession("DiscountSession");

		if (ksession == null)
			throw new IllegalStateException("KIE DiscountSession was not built");
		
		for(Object fact : facts) {
			ksession.insert(fact);
		}
	
		ksession.fireAllRules();
		
		Collection<Object> updatedFacts = new ArrayList<Object>();
		for (Object o : ksession.getObjects())
			updatedFacts.add(o);
		
		ksession.dispose();
		
		return updatedFacts;
	}
	
	public BigDecimal fire(List<CatalogItem> catalogItems,Set<Promotion> promotions, ShoppingCart cart) {
		
		KieSession ksession = container.newKieSession("DiscountSession");
		if (ksession == null)
			throw new IllegalStateException("KIE DiscountSession was not built");
		
		for(Object item : catalogItems) {
			ksession.insert(item);
		}
		
		for (Promotion promotion : promotions) {
			ksession.insert(promotion);
		}
		ksession.insert(cart);
	
		ksession.fireAllRules();
		
		BigDecimal finalDiscountValue = BigDecimal.ZERO;
		for (Object o : ksession.getObjects())
			if (o instanceof BigDecimal)
			  finalDiscountValue = finalDiscountValue.add((BigDecimal) o);
		ksession.dispose();
		return finalDiscountValue;
	}
	
	@PostConstruct
	public void build(){
		KieServices kieServices = KieServices.Factory.get();
		container = kieServices.newKieClasspathContainer();
		if(RuleUtils.checkErrors(container)){
			throw new IllegalArgumentException("There are rule syntax errors");
		}

	}
	
}
