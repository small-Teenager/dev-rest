package com.dev.rest.service;

import java.util.Map;

public interface SignService {
    Boolean signIn(String userId);

    Boolean signStatus(String userId);

    Long monthCount(String userId);

    Boolean countersign(String userId,String signDate);

    Map<String, Boolean> signCalendar(String userId);

    Long conMonthCount(String userId);
}
