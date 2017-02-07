package com.revlo.models;

import lombok.Data;

import java.util.Map;

/**
 * This class has lombok generated methods. See source code for methods generated.
 * Data model of a Revlo Redemption object.
 */
@Data
public class Redemption implements Model {
    /**
     * Reward identifier
     * @return rewardId
     */
    private int rewardId;
    /**
     * Redemption identifier
     * @return redemptionId
     */
    private int redemptionId;
    /**
     * A timestamp for when the Redemption was made.
     * @return A String representation of a UTC DateTime ISO8601
     */
    private String createdAt;
    /**
     * A flag for whether or not the reward is a refunded redemption
     * @return refunded
     */
    private Boolean refunded;
    /**
     * A flag for whether or not the redemption has been redeemed
     * @return completed
     */
    private Boolean completed;
    /**
     * A dictionary of user inputs. Directly maps from inputFields of a reward
     * @return userInput
     */
    private Map<String, String> userInput;
    /**
     * The username of the user that owns this redemption
     * @return username
     */
    private String username;
}