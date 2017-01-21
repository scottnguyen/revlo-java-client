package com.revlo.requests;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Builder
@Data
public class GetRewardRequest implements Request {
    private Integer id;

    @Override
    public Map<String, String> params() {
        return new HashMap<>();
    }

    @Override
    public String payload() {
        return "";
    }
}
