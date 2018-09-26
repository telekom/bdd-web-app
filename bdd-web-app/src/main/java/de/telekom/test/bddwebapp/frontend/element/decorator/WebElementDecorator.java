package de.telekom.test.bddwebapp.frontend.element.decorator;

import de.telekom.test.bddwebapp.frontend.element.WebElementEnhanced;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.sf.cglib.proxy.Enhancer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Provides the extended WebElement via the WebElementHandler.
 * The WebElementEnhanced is integrated into the page objects using the @FindBy Annotation.
 * The use of WebElementEnhanced is not mandatory.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebElementDecorator implements FieldDecorator {

    private final @NonNull
    WebDriver webDriver;

    public Object decorate(ClassLoader loader, Field field) {
        DefaultElementLocatorFactory defaultElementLocatorFactory = new DefaultElementLocatorFactory(webDriver);
        if (isWebElementEnhanced(field)) {
            return getEnhancedObject(field, defaultElementLocatorFactory);
        }
        if (isListWithWebElementEnhanced(field)) {
            return getEnhancedObject(field, defaultElementLocatorFactory);
        }
        return new DefaultFieldDecorator(defaultElementLocatorFactory).decorate(loader, field);
    }

    private boolean isWebElementEnhanced(Field field) {
        return WebElementEnhanced.class.isAssignableFrom(field.getType()) && field.isAnnotationPresent(FindBy.class);
    }

    private boolean isListWithWebElementEnhanced(Field field) {
        return List.class.isAssignableFrom(field.getType())
                && WebElementEnhanced.class.isAssignableFrom(
                (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0])
                && field.isAnnotationPresent(FindBy.class);
    }

    private Object getEnhancedObject(Field field, DefaultElementLocatorFactory defaultElementLocatorFactory) {
        Enhancer e = new Enhancer();
        e.setSuperclass(field.getType());
        ElementLocator locator = defaultElementLocatorFactory.createLocator(field);
        e.setCallback(new WebElementHandler(webDriver, locator));
        return e.create();
    }

}