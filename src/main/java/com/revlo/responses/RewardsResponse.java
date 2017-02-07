package com.revlo.responses;


import com.revlo.models.Reward;
import lombok.Data;

import java.util.List;

@Data
public class RewardsResponse implements Response {
    private List<Reward> rewards;
    private int pageSize;
    private int total;
}

