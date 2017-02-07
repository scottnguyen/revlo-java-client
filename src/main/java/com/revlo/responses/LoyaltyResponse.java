package com.revlo.responses;

import com.revlo.models.Loyalty;
import lombok.Data;

@Data
public class LoyaltyResponse implements Response {
    private Loyalty loyalty;
}
