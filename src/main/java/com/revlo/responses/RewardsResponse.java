package com.revlo.responses;


import com.revlo.Model;
import lombok.Data;

import java.util.List;

@Data
public class RewardsResponse {
    private List<Model.Reward> rewards;
    private int page_size;
    private int total;
}

