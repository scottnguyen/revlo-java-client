package com.revlo;


import lombok.Data;

import java.util.List;
import java.util.Map;

public interface Model {

    @Data
    class Loyalty implements Model {
        private String fan;
        private int total_points;
        private int current_points;
        private String updated_at;
    }

    @Data
    class Redemption implements Model {
        private int reward_id;
        private int redemption_id;
        private String created_at;
        private boolean refunded;
        private boolean completed;
        private Map<String, String> user_input;
        private String username;
    }

    @Data
    class Reward implements Model {
        private int reward_id;
        private String created_at;
        private boolean refunded;
        private boolean completed;
        private Map<String, String> user_input;
        private String title;
        private String bot_command;
        private boolean enabled;
        private boolean sub_only;
        private List<String> input_fields;
    }

}