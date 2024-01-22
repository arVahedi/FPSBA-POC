package com.db.product.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * This class responsible for gathering configuration from application.properties file.
 *
 * <p>You <b>MUST NOT</b> use {@link Value} annotation in other classes. Instead, use it there and inject this class
 * and call getter methods in anywhere you want access to properties. In this way, we have a full encapsulation for
 * properties.</p>
 *
 * @author arVahedi
 */

@Getter
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration(value = BusinessConfig.BEAN_NAME)
public class BusinessConfig implements BaseConfig {

    public static final String BEAN_NAME = "businessConfig";

}

