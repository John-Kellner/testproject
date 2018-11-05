package com.project.presentation.server.validation

import com.project.presentation.server.tx.dom.impl.HibernateServiceImpl
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import spock.lang.Specification
import util.PersistenceLoader

import javax.annotation.Resource
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory
import java.lang.annotation.Annotation

import static util.PersistenceLoader.*
import static util.PersistenceLoader.applicationContext;

/**
 * Created by john on 02.10.2016.
 */
class BeanConsumerTest extends Specification {
    //private Validator validator;

    private LocalValidatorFactoryBean localValidatorFactory;

    void setup() {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
    }

    def "testLoad"() {
        given:
        def consumer = new BeanConsumer()
        consumer.load(null);

        def validate = validator.validate(consumer)
        Annotation annotation = validate.iterator().next().getConstraintDescriptor().getAnnotation();

        when:
        validator.validate(consumer)

        then:
        validate.size() > 1
    }
}
