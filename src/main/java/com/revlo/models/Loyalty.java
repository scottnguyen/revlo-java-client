package com.revlo.models;


import lombok.Data;

/**
 * This class has lombok generated methods. See source code for methods generated.
 * Data model of a Revlo Loyalty object.
 */
@Data
public class Loyalty implements Model {
    /**
     * Fan username
     * @return The fan's username
     */
    private String fan;
    /**
     * Total points gained passively
     * @return The current value of total_points
     */
    private int totalPoints;
    /**
     * Current points gained passively
     * @return The current value of current_points
     */
    private int currentPoints;
    /**
     * Last time the points object was refreshed.
     * @params A Datetime String
     * @return A String representation of a UTC DateTime ISO8601
     */
    private String updatedAt;
}