package com.revlo.requests;

import lombok.Data;

@Data
public class UpdateRedemptionRequest implements Request {
    private int id;
    private boolean completed;
}
