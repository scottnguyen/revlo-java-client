package com.revlo;


import lombok.Data;

import java.util.List;
import java.util.Map;

public interface Model {

    /**
     * Data model of a Revlo Loyalty object. This class has lombok generated methods.
     * See source code for methods generated.
     */
    @Data
    class Loyalty implements Model {
        private String fan;
        private int totalPoints;
        private int currentPoints;
        private String updatedAt;
    }

    /**
     * Data model of a Revlo Redemption object. This class has lombok generated methods.
     * See source code for methods generated.
     */
    @Data
    class Redemption implements Model {
        private int rewardId;
        private int redemptionId;
        private String createdAt;
        private Boolean refunded;
        private Boolean completed;
        private Map<String, String> userInput;
        private String username;
    }

    /**
     * Data model of a Revlo Reward object. This class has lombok generated methods.
     * See source code for methods generated.
     */
    @Data
    class Reward implements Model {
        private int rewardId;
        private String createdAt;
        private String title;
        private String botCommand;
        private Boolean enabled;
        private Boolean subOnly;
        private List<String> inputFields;
        private int points;
    }

}