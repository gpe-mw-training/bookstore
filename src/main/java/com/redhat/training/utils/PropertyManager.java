package com.redhat.training.utils;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

@Singleton
public class PropertyManager {

	private Properties props = new Properties();
	
	@PostConstruct
	private void load() throws IOException {
		props.load(getClass().getClassLoader().getResourceAsStream("runtime.properties"));
	}
	
	public boolean getPreLoadData() {
		return new Boolean(props.getProperty("preloaddata", "false"));
	}
}
