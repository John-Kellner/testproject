package util;

import org.apache.log4j.BasicConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class PersistenceLoader {

	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {

		if (applicationContext == null) {
			BasicConfigurator.configure();
			System.out.println("PERSISTENCE LOADING");	
			applicationContext = new FileSystemXmlApplicationContext("/war/WEB-INF/applicationContext.xml");
			System.out.println("PERSISTENCE LOADED");
		}
		return applicationContext;
	}
}
