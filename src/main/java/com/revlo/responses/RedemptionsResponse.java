package com.revlo.responses;

import com.revlo.Model;
import lombok.Data;

import java.util.List;

@Data
public class RedemptionsResponse {
    private List<Model.Redemption> redemptions;
    private int total;
    private int page_size;
}
