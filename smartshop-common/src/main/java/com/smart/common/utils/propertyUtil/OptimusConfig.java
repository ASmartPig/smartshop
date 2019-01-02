package com.smart.common.utils.propertyUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * optimus全局config
 * @author hongwm
 * @since 2015-04-13
 */
public class OptimusConfig {
    private final static Logger logger = LoggerFactory.getLogger(OptimusConfig.class);
    private static Properties props;

    /**
     * 获取key的属性值
     * @param key
     * @return
     */
    public static String getValue(String key){
        return getValue(key, String.class);
    }

    /**
     * 获取key的属性值
     * @param <T>
     * @param key
     * @return
     */
    public static <T> T getValue(String key, Class<T> clazz){
        ensureProps();
        String k = props.getProperty(key);
        if(k == null){
            k = props.getProperty(key.toLowerCase());
        }
        return ObjectUtil.convertValue(k, clazz);
    }



    /**
     * 获取property key的值，如果值不存在，返回defaultValue
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getValue(String key, String defaultValue){
        ensureProps();
        String value = props.getProperty(key);
        if(value == null){
            value = props.getProperty(key.toLowerCase());
        }
        if(value == null) {
            value = defaultValue;
        }

        return value;
    }

    /**
     * 获取key的属性值
     * @param <T>
     * @param key
     * @return
     */
    public static <T> T getValue(String key, Class<T> clazz, T defaultValue){
        T obj = getValue(key, clazz);
        if(obj == null) {
            obj = defaultValue;
        }
        return obj;
    }



    public static void updateProperty(String key,String value) {
        ensureProps();
        props.setProperty(key, value);
        props.setProperty(key.toLowerCase(), value);
    }

    private static void ensureProps() {
        if(props == null) {
            PropertyPlaceholderConfigurer propertyConfigurer = new PropertyPlaceholderConfigurer();
            PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
            try {
                Resource[] locations = resourceResolver.getResources("classpath*:**/*.properties");
                propertyConfigurer.setLocations(locations);
                propertyConfigurer.setFileEncoding("utf-8");
                props = (Properties) CommonUtil.invokeMethod(propertyConfigurer, "mergeProperties");
                if(props != null){
                    Properties newProps = new Properties();
                    for(Object key : props.keySet()){
                        if(key == null) {
                            continue;
                        }
                        String keyLower = key.toString().toLowerCase();
                        Object value = props.get(key);
                        newProps.put(keyLower, value);
                        newProps.put(key, value);
                    }
                    props.clear();
                    props.putAll(newProps);
                }
            } catch (IOException e) {
                logger.warn("", e);
//                e.printStackTrace();
                props = new Properties();
            }

        }
    }

    public void setPropertyConfigurer(
            PropertyPlaceholderConfigurer propertyConfigurer) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        logger.info("start to set propertyConfigurer");
        props = (Properties) CommonUtil.invokeMethod(propertyConfigurer, "mergeProperties");
        ensureProps();
    }
}
