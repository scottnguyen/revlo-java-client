package com.revlo.responses;

import com.revlo.Model;
import lombok.Data;

import java.util.List;

@Data
public class RedemptionsResponse implements Response {
    private List<Model.Redemption> redemptions;
    private int total;
    private int pageSize;
}
