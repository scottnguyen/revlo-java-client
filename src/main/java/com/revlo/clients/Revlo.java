package com.revlo.clients;

import com.revlo.exceptions.RevloServiceException;
import com.revlo.requests.BonusPointsRequest;
import com.revlo.requests.GetLoyaltyRequest;
import com.revlo.requests.GetRedemptionRequest;
import com.revlo.requests.GetRedemptionsRequest;
import com.revlo.requests.GetRewardRequest;
import com.revlo.requests.GetRewardsRequest;
import com.revlo.requests.UpdateRedemptionRequest;
import com.revlo.responses.*;


interface Revlo {

    RewardResponse getReward(GetRewardRequest request) throws RevloServiceException;
    RewardsResponse getRewards(GetRewardsRequest request) throws RevloServiceException;

    LoyaltyResponse getLoyalty(GetLoyaltyRequest request) throws RevloServiceException;
    LoyaltyResponse bonusPoints(BonusPointsRequest request) throws RevloServiceException;

    RedemptionsResponse getRedemptions(GetRedemptionsRequest request) throws RevloServiceException;
    RedemptionResponse getRedemption(GetRedemptionRequest request) throws RevloServiceException;
    RedemptionResponse updateRedemption(UpdateRedemptionRequest request) throws RevloServiceException;
}