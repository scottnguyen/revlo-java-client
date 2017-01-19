package com.revlo.clients;

import com.revlo.exceptions.RevloServiceException;
import com.revlo.models.Loyalty;
import com.revlo.models.Redemption;
import com.revlo.models.Reward;
import com.revlo.requests.*;

import java.util.List;

interface Revlo {

    Reward getReward(GetRewardRequest request) throws RevloServiceException;
    List getRewards(GetRewardsRequest request) throws RevloServiceException;

    Loyalty getLoyalty(GetLoyaltyRequest request) throws RevloServiceException;
    Loyalty bonusPoints(BonusPointsRequest request) throws RevloServiceException;

    List<Redemption> getRedemptions(GetRedemptionsRequest request) throws RevloServiceException;
    Redemption getRedemption(GetRedemptionRequest request) throws RevloServiceException;
    Redemption updateRedemption(UpdateRedemptionRequest request) throws RevloServiceException;
}