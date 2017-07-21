package org.jbehave.webapp.frontend.element.decorator;

import org.jbehave.webapp.frontend.element.WebElementEnhanced;
import lombok.RequiredArgsConstructor;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Binds the WebElementEnhanced.
 * Provides the same behavior as the WebElement for the WebElementEnhanced:
 * The WebElementEnhanced is initially only created as a proxy. Only when accessing a method in the proxy object the element in the DOM is accessed.
 * Thus, as in the WebElement, an exception occurs with a failed DOM access when a method is called.
 *
 * @author Daniel Keiss
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
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

	private Object invokeWebElementEnhanced(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
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

	private Object invokeListContainingWebElementEnhanced(Object[] objects, MethodProxy methodProxy) throws Throwable {
		List<Object> list = new ArrayList<>();
		List<WebElement> elements = locator.findElements();
		for (WebElement element : elements) {
			WebElementEnhanced webElementEnhanced = new WebElementEnhanced();
			webElementEnhanced.setWebElement(element);
			webElementEnhanced.setWebDriver(webDriver);
			list.add(webElementEnhanced);
		}

		try {
			return methodProxy.invoke(list, objects);
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}
	}

}

