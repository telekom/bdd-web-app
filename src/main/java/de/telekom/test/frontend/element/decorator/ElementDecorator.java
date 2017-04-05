package de.telekom.test.frontend.element.decorator;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by d.keiss on 05.04.2017.
 */
public class ElementDecorator implements FieldDecorator {

	private final WebDriver webDriver;
	private final DefaultFieldDecorator defaultFieldDecorator;

	public ElementDecorator(WebDriver webDriver) {
		this.webDriver = webDriver;
		this.defaultFieldDecorator = new DefaultFieldDecorator(new DefaultElementLocatorFactory(webDriver));
	}

	public Object decorate(ClassLoader loader, Field field) {
		if (WebElementEnhanced.class.isAssignableFrom(field.getType()) && field.isAnnotationPresent(FindBy.class)) {
			return getEnhancedObject(field.getType(), new ElementHandler(webDriver, field));
		} else if (List.class.isAssignableFrom(field.getType())
				&& WebElementEnhanced.class.isAssignableFrom(getFirstGenericTypeOfList(field))
				&& field.isAnnotationPresent(FindBy.class)) {
			return getEnhancedObject(field.getType(), new ElementHandler(webDriver, field));
		} else {
			return defaultFieldDecorator.decorate(loader, field);
		}
	}

	private Object getEnhancedObject(Class clzz, MethodInterceptor methodInterceptor) {
		Enhancer e = new Enhancer();
		e.setSuperclass(clzz);
		e.setCallback(methodInterceptor);
		return e.create();
	}

	private Class<?> getFirstGenericTypeOfList(Field field) {
		return (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
	}

}