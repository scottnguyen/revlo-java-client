package com.revlo.models;

import lombok.Data;

import java.util.List;

/**
 * This class has lombok generated methods. See source code for methods generated.
 * Data model of a Revlo Reward object.
 */
@Data
public class Reward implements Model {
    /**
     * Reward identifier
     * @return rewardId
     */
    private int rewardId;
    /**
     * A timestamp for when the Reward was made.
     * @return A String representation of a UTC DateTime ISO8601
     */
    private String createdAt;
    /**
     * The title of the reward
     * @return the title of the reward
     */
    private String title;
    /**
     * The bot command associated with the reward
     * @return the bot command keyword
     */
    private String botCommand;
    /**
     * A flag for whether or not the reward is enabled
     * @return enabled
     */
    private Boolean enabled;
    /**
     * A flag for whether or not the reward is a sub only reward
     * @return enabled
     */
    private Boolean subOnly;
    /**
     * List of input fields that exist for this reward
     * @return subOnly
     */
    private List<String> inputFields;
    /**
     * Amount of points this reward costs to a viewer
     * @return inputFields
     */
    private int points;
}