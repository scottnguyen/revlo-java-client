package com.revlo.responses;

import com.revlo.Model;
import lombok.Data;

@Data
public class RewardResponse implements Response {
    private Model.Reward reward;
}