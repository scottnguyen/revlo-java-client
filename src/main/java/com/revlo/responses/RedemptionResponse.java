package com.revlo.responses;


import com.revlo.Model;
import lombok.Data;

@Data
public class RedemptionResponse implements Response {
    private Model.Redemption redemption;
}

