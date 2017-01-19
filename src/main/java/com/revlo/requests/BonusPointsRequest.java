package com.revlo.requests;

import lombok.Data;

@Data
public class BonusPointsRequest implements Request {
    private String username;
}
