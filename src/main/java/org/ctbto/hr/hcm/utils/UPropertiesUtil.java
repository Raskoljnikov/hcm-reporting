package org.ctbto.hr.hcm.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * UPropertiesUtil entity. @author MyEclipse Persistence Tools.
 */
public class UPropertiesUtil extends PropertyPlaceholderConfigurer {
    /**
     * Holds properties.
     */
    private static Map<String, String> propertiesMap;

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer#processProperties(org.springframework.beans.factory.config.ConfigurableListableBeanFactory, java.util.Properties)
     */
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
        super.processProperties(beanFactory, props);

        propertiesMap = new HashMap<String, String>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            propertiesMap.put(keyStr, props.getProperty(keyStr));
        }
    }

    /**
     * Gets property by name.
     * @param name Name.
     * @return property value.
     */
    public static String getProperty(String name) {
        return propertiesMap.get(name);
    }
}
