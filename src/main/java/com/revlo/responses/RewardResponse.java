package com.revlo.responses;

import com.revlo.models.Reward;
import lombok.Data;

@Data
public class RewardResponse implements Response {
    private Reward reward;
}