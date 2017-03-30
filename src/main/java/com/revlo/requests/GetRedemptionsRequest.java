package com.revlo.requests;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.HashMap;

@Builder @Data
public class GetRedemptionsRequest implements Request {
    private Integer page;
    private Boolean completed;
    private Boolean refunded;
    private Integer rewardId;

    public Map<String,String> params() {
        Map<String,String> hash = new HashMap<>();
        if (getPage() != null) {
            hash.put("page", getPage().toString());
        }
        if (getCompleted() != null) {
            hash.put("completed", getCompleted().toString());
        }
        if (getRefunded() != null) {
            hash.put("refunded", getRefunded().toString());
        }
        if (getRewardId() != null) {
            hash.put("reward_id", getRewardId().toString());
        }
        return hash;
    }

    @Override
    public String payload() {
        return "";
    }
}
