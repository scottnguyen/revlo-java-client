package com.revlo.responses;

import com.revlo.models.Redemption;
import lombok.Data;

import java.util.List;

@Data
public class RedemptionsResponse implements Response {
    private List<Redemption> redemptions;
    private int total;
    private int pageSize;
}
