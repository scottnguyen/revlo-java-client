package com.revlo.requests;

import lombok.Data;

@Data
public class GetLoyaltyRequest implements Request {
    private String username;
}
