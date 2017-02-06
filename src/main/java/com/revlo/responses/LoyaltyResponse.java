package com.revlo.responses;

import com.revlo.Model;
import lombok.Data;

@Data
public class LoyaltyResponse implements Response {
    private Model.Loyalty loyalty;
}
