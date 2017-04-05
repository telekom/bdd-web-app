package de.telekom.test.frontend.element.decorator;

import de.telekom.test.frontend.element.WebElementEnhanced;
import net.sf.cglib.proxy.Enhancer;
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
public class WebElementDecorator implements FieldDecorator {

	private final WebDriver webDriver;

	public WebElementDecorator(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public Object decorate(ClassLoader loader, Field field) {
		if (isWebElementEnhanced(field)) {
			return getEnhancedObject(field);
		}
		if (isListWithWebElementEnhanced(field)) {
			return getEnhancedObject(field);
		}
		return new DefaultFieldDecorator(new DefaultElementLocatorFactory(webDriver)).decorate(loader, field);
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

	private Object getEnhancedObject(Field field) {
		Enhancer e = new Enhancer();
		e.setSuperclass(field.getType());
		e.setCallback(new WebElementHandler(webDriver, field));
		return e.create();
	}

}