package com.dev.rest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;


/**
 * @author: yaodong zhang
 * @create 2023/2/2
 */
@Configuration
public class ValidConfig {

    private static final Logger log = LoggerFactory.getLogger(ValidConfig.class);

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // i18n资源路径
        messageSource.setBasename("i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
//        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
//        sessionLocaleResolver.setDefaultLocale(new Locale(defaultLanguage));
        return new MyLocaleResolver();
    }

    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(messageSource);
        //快速失败模式
        validatorFactoryBean.getValidationPropertyMap().put("hibernate.validator.fail_fast", "true");
        return validatorFactoryBean;
    }


    // 设置快速失败机制
//    @Bean
//    public Validator validator(AutowireCapableBeanFactory springFactory) {
//        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
//                .configure()
//                // 快速失败模式
//                .failFast(true)
//                .constraintValidatorFactory(new SpringConstraintValidatorFactory(springFactory))
//                .buildValidatorFactory();
//        return validatorFactory.getValidator();
//    }
}
