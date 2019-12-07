package de.telekom.test.bddwebapp.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Fault tolerant url appender
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */

public class UrlAppender {

    private UrlAppender() {
    }

    public static String appendUrl(String url, String... appenders) {
        StringBuilder urlBuilder = new StringBuilder(url);
        for (String appender : appenders) {
            boolean alreadyAppended = false;
            if (urlBuilder.toString().endsWith("/") && appender.startsWith("/")) {
                urlBuilder.append(StringUtils.substring(appender, 1));
                alreadyAppended = true;
            }
            if (!alreadyAppended) {
                if (urlBuilder.toString().endsWith("/") || appender.startsWith("/")) {
                    urlBuilder.append(appender);
                } else {
                    urlBuilder.append("/").append(appender);
                }
            }
        }
        url = urlBuilder.toString();
        return url;
    }

    public static String appendQueryParams(String url, Map<String, String> queryParams) {
        if (queryParams != null && queryParams.size() > 0) {
            StringBuilder query = new StringBuilder();
            boolean isFirstparameter = true;
            for (String key : queryParams.keySet()) {
                if (isFirstparameter) {
                    isFirstparameter = false;
                } else {
                    query.append("&");
                }
                String value = queryParams.get(key);
                if (StringUtils.isEmpty(value)) {
                    query.append(key);
                } else {
                    query.append(key).append("=").append(value);
                }
            }
            url += "?" + query;
        }
        return url;
    }

}
