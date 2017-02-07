package com.revlo.responses;


import com.revlo.Model;
import lombok.Data;

import java.util.List;

@Data
public class RewardsResponse implements Response {
    private List<Model.Reward> rewards;
    private int pageSize;
    private int total;
}

