package de.telekom.test.frontend.element.decorator;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by d.keiss on 05.04.2017.
 */
public class WebElementHandler implements MethodInterceptor {

	private static final List<String> IGNORED_METHODS = asList("toString", "hashCode");

	private final ElementLocator locator;
	private final WebDriver webDriver;

	public WebElementHandler(WebDriver webDriver, Field field) {
		this.locator = new DefaultElementLocatorFactory(webDriver).createLocator(field);
		this.webDriver = webDriver;
	}

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
		if (!method.getName().equals("setElement")
				&& !method.getName().equals("getElement")
				&& !method.getName().equals("setWebDriver")) {
			WebElement element = locator.findElement();
			WebElementEnhanced webElementEnhanced = (WebElementEnhanced) o;
			webElementEnhanced.setElement(element);
			webElementEnhanced.setWebDriver(webDriver);
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
		for (int i = 0; i < elements.size(); i++) {
			WebElementEnhanced webElementEnhanced = new WebElementEnhanced();
			webElementEnhanced.setElement(elements.get(i));
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

