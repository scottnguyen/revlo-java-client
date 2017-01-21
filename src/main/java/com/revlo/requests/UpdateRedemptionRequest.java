package com.revlo.requests;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;


@Builder
@Data
public class UpdateRedemptionRequest implements Request {
    private Integer id;
    private Boolean completed;

    @Override
    public Map<String, String> params() {
        return new HashMap<>();
    }

    @Override
    public String payload() {
        if (getCompleted() != null) {
            return "{ \"completed\": " + getCompleted() + "}";
        }
        return "{}";
    }
}
