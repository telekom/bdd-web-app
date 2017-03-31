package de.telekom.test.api;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CompareUtils {

	public static void compareAnyAttributeTypeWithString(Object actual, String expected) throws AssertionError {
		if (actual instanceof String) {
			assertThat(actual, is(expected));
			return;
		} else if (actual instanceof BigDecimal && isBoolean(expected)) {
			BigDecimal dbObjectNo = (BigDecimal) actual;
			Boolean responseBoolVariable = Boolean.parseBoolean(expected);
			Boolean requestBoolVariable = dbObjectNo.compareTo(BigDecimal.ONE) == 0;

			assertThat(responseBoolVariable, is(requestBoolVariable));
			return;
		} else if (actual instanceof BigDecimal) {
			BigDecimal dbObjectNo = (BigDecimal) actual;
			BigDecimal responseObjectNo = new BigDecimal(expected);

			assertTrue(responseObjectNo.compareTo(dbObjectNo) == 0);
			return;
		} else if (actual instanceof Long && isBoolean(expected)) {
			Long dbObjectNo = (Long) actual;
			Boolean requestBoolVariable = dbObjectNo.compareTo(1L) == 0;
			Boolean responseBoolVariable = Boolean.parseBoolean(expected);

			assertThat(requestBoolVariable, is(responseBoolVariable));
			return;
		} else if (actual instanceof Long) {
			Long dbObjectNo = (Long) actual;
			Long responseObjectNo = new Long(expected);

			assertThat(dbObjectNo, is(responseObjectNo));
			return;
		} else if (actual instanceof Integer) {
			Integer dbObjectNo = (Integer) actual;
			Integer responseObjectNo = new BigDecimal(expected).intValueExact();

			assertThat(dbObjectNo, is(responseObjectNo));
			return;
		} else if (actual instanceof List) {
			assertList((List<?>) actual, expected, ",");
			return;
		} else if (actual instanceof Map) {
			List<String> mapContentAsList = new ArrayList<>();
			Map<String, Object> map = (Map<String, Object>) actual;
			for (String key : map.keySet()) {
				if (StringUtils.isNotBlank(key + map.get(key))) {
					mapContentAsList.add(key + ":" + map.get(key));
				}
			}
			if (mapContentAsList.isEmpty()) {
				mapContentAsList.add(":");
			}
			assertList(mapContentAsList, expected, ", ");
			return;
		} else if (actual instanceof Date) {
			Date dbDate = (Date) actual;
			Long responseDateTime = new Long(expected);

			assertThat(dbDate.getTime(), is(responseDateTime));
			return;
		} else if (actual instanceof Boolean && isBoolean(expected)) {
			boolean actualAsBoolean = (Boolean) actual;
			boolean expectedAsBoolean = Boolean.parseBoolean(expected);

			assertThat(actualAsBoolean, is(expectedAsBoolean));
			return;
		} else if (actual instanceof BigDecimal || actual instanceof Double || actual instanceof Float) {
			// try BigDecimal
			try {
				BigDecimal actualBigDecimal = new BigDecimal(actual.toString());
				BigDecimal expectedBigDecimal = new BigDecimal(expected);
				assertTrue(actualBigDecimal.compareTo(expectedBigDecimal) == 0);
			} catch (NumberFormatException e) {
				// compare String
				assertThat(actual.toString(), is(expected));
			}
			return;
		} else if (actual == null && expected.equals("null")) {
			return;
		}
		fail("Expected was " + expected + " but was " + actual);
	}

	private static void assertList(List<?> actual, String expected, String separator) {
		if (expected.equals("null") && actual.get(0) == null) {
			return;
		}
		String dbString = Joiner.on(separator).join(actual);
		assertThat(dbString, is(expected));
	}

	private static boolean isBoolean(String param) {
		return param.equalsIgnoreCase(Boolean.TRUE.toString()) || param.equalsIgnoreCase(Boolean.FALSE.toString());
	}

}
