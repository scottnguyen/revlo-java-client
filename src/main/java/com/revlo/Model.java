package com.revlo;


import lombok.Data;

import java.util.List;
import java.util.Map;

public interface Model {

    /**
     * Data model of a Revlo Loyalty object.
     */
    @Data
    class Loyalty implements Model {
        private String fan;
        private int totalPoints;
        private int currentPoints;
        private String updatedAt;
    }

    /**
     * Data model of a Revlo Redemption object.
     */
    @Data
    class Redemption implements Model {
        private int rewardId;
        private int redemptionId;
        private String createdAt;
        private boolean refunded;
        private boolean completed;
        private Map<String, String> userInput;
        private String username;
    }

    /**
     * Data model of a Revlo Reward object.
     */
    @Data
    class Reward implements Model {
        private int rewardId;
        private String createdAt;
        private boolean refunded;
        private boolean completed;
        private String title;
        private String botCommand;
        private boolean enabled;
        private boolean subOnly;
        private List<String> inputFields;
    }

}