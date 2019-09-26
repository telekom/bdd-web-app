package de.telekom.test.bddwebapp.frontend.element.decorator;

import de.telekom.test.bddwebapp.frontend.element.WebElementEnhanced;
import lombok.RequiredArgsConstructor;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

/**
 * Binds the WebElementEnhanced.
 * Provides the same behavior as the WebElement for the WebElementEnhanced:
 * The WebElementEnhanced is initially only created as a proxy. Only when accessing a method in the proxy object the element in the DOM is accessed.
 * Thus, as in the WebElement, an exception occurs with a failed DOM access when a method is called.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RequiredArgsConstructor
public class WebElementHandler implements MethodInterceptor {

    private static final List<String> IGNORED_METHODS = asList("toString", "hashCode");

    private final WebDriver webDriver;
    private final ElementLocator locator;

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (IGNORED_METHODS.contains(method.getName())) {
            return methodProxy.invokeSuper(o, objects);
        }
        if (o instanceof WebElementEnhanced) {
            return invokeWebElementEnhanced(o, method, objects, methodProxy);
        }
        if (o instanceof List) {
            return invokeListContainingWebElementEnhanced(objects, methodProxy);
        }
        return null;
    }

    protected Object invokeWebElementEnhanced(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        WebElementEnhanced webElementEnhanced = (WebElementEnhanced) o;
        if (!"setWebDriver".equals(method.getName())) {
            webElementEnhanced.setWebDriver(webDriver);
        }
        if (!WebElementEnhanced.NOT_INVOKE_WEB_ELEMENT_METHODS.contains(method.getName())) {
            WebElement element = locator.findElement();
            webElementEnhanced.setWebElement(element);
        }
        try {
            return methodProxy.invokeSuper(o, objects);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    protected Object invokeListContainingWebElementEnhanced(Object[] objects, MethodProxy methodProxy) throws Throwable {
        List<WebElementEnhanced> webElementEnhanceds = locator.findElements().stream()
                .map(webElement -> {
                    WebElementEnhanced webElementEnhanced = new WebElementEnhanced();
                    webElementEnhanced.setWebElement(webElement);
                    webElementEnhanced.setWebDriver(webDriver);
                    return webElementEnhanced;
                })
                .collect(toList());
        try {
            return methodProxy.invoke(webElementEnhanceds, objects);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

}

