package com.cit.its.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContainer {
	private ApplicationContext context = null;
	public ApplicationContext getContext() {
		return context;
	}
	public void setContext(ApplicationContext context) {
		this.context = context;
	}
	public void loadContextByXMLPath(String filename){
		context = new ClassPathXmlApplicationContext(filename);
	}
	private SpringContainer() {
	}
	private static SpringContainer container = null;
	public static SpringContainer getInstance(){
		if(container == null){
			container = new SpringContainer();
		}
		return container;
	}
}
