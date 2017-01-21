package com.revlo.requests;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Builder
@Data
public class BonusPointsRequest implements Request {
    private String username;
    private Integer amount;

    @Override
    public Map<String, String> params() {
        return new HashMap<>();
    }

    @Override
    public String payload() {
        if (getAmount() != null) {
            return "{\"amount\":" + getAmount() + "}";
        }
        return "{}";
    }
}
