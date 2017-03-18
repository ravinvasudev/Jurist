package in.co.jurist.util;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Config {

	private static final Logger logger = LoggerFactory
			.getLogger(Config.class);
	
	// Spring injected properties
	private static Properties utilProperties;

	public Config() {
		logger.info("Config constuctor called");
	}

	public static String getProperty(String key) {
		String value = null;
		if (getUtilProperties().containsKey(key)) {
			value = getUtilProperties().getProperty(key);
		}
		return value;
	}

	public static Properties getUtilProperties() {
		return Config.utilProperties;
	}

	public void setUtilProperties(Properties utilProperties) {
		Config.utilProperties = utilProperties;
		logger.info("Properties loaded: " + Config.utilProperties);
	}

}
