package com.revlo.requests;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Builder
@Data
public class GetRewardsRequest implements Request {
    private Integer page;

    public Map<String,String> params() {
        Map<String, String> hash = new HashMap<>();
        if (page != null) {
            hash.put("page", page.toString());
        }
        return hash;
    }

    @Override
    public String payload() {
        return "";
    }
}
