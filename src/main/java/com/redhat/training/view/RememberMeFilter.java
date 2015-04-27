package com.redhat.training.view;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.redhat.training.domain.UserToken;
import com.redhat.training.service.RememberMeService;

@WebFilter(urlPatterns = { "/index.xhtml" })
public class RememberMeFilter implements Filter {

	@Inject
	private RememberMeService rememberMeService;

	@Inject
	private ShoppingCart cart;

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Cookie[] cookies = httpRequest.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("auth_key".equals(cookie.getName())) {
					UserToken userToken = rememberMeService.findToken(cookie
							.getValue());
					cart.setCustomer(userToken.getCustomer());

				}
			}

		}
		chain.doFilter(httpRequest, response);
	}

}
