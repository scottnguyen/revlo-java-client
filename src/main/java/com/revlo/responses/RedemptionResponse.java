package com.revlo.responses;


import com.revlo.models.Model;
import com.revlo.models.Redemption;
import lombok.Data;

@Data
public class RedemptionResponse implements Response {
    private Redemption redemption;
}

