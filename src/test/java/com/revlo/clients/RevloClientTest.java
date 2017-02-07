package com.revlo.clients;

import com.google.common.collect.ImmutableMap;
import com.revlo.Model;
import com.revlo.exceptions.RevloClientException;
import com.revlo.requests.*;
import com.revlo.responses.*;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RevloClientTest {

    private RevloClient client;

    @Test
    public void bonusPointsToAUser__shouldGivePoints() throws Exception {
        StatusLine fakeStatusLine = mock(StatusLine.class);
        when(fakeStatusLine.getStatusCode()).thenReturn(200);
        CloseableHttpResponse bonusResponse = mock(CloseableHttpResponse.class);
        when(bonusResponse.getEntity()).thenReturn(new StringEntity("{\"loyalty\":{\"fan\":\"pokemaniac\"," +
                "\"total_points\":0,\"current_points\":100,\"updated_at\":\"2016-09-14T16:02:00.000Z\"}}"));
        when(bonusResponse.getStatusLine()).thenReturn(fakeStatusLine);

        HttpHost httpHost = new HttpHost("");

        CloseableHttpClient fakeClient = mock(CloseableHttpClient.class);
        when(fakeClient.execute(any(HttpHost.class), any(HttpRequest.class)))
                .thenReturn(bonusResponse);

        BonusPointsRequest request = BonusPointsRequest.builder()
                .username("pokemaniac")
                .amount(100)
                .build();
        this.client = new RevloClient("apiKey", fakeClient, httpHost,1);
        LoyaltyResponse response = client.bonusPoints(request);
        assertEquals(100, response.getLoyalty().getCurrentPoints());

        when(bonusResponse.getEntity()).thenReturn(new StringEntity("{\"loyalty\":{\"fan\":\"pokemaniac\"," +
                "\"total_points\":0,\"current_points\":200,\"updated_at\":\"2016-09-14T16:02:00.000Z\"}}"));
        response = client.bonusPoints(request);
        assertEquals(200, response.getLoyalty().getCurrentPoints());

    }

    private void bonusAmountOverflowClient(int amount) throws Exception {
        StatusLine fakeStatusLine = mock(StatusLine.class);
        when(fakeStatusLine.getStatusCode()).thenReturn(400);
        CloseableHttpResponse bonusResponse = mock(CloseableHttpResponse.class);
        when(bonusResponse.getEntity()).thenReturn(new StringEntity("{\"error\":{\"gg\"}"));
        when(bonusResponse.getStatusLine()).thenReturn(fakeStatusLine);

        HttpHost httpHost = new HttpHost("");

        CloseableHttpClient fakeClient = mock(CloseableHttpClient.class);
        when(fakeClient.execute(any(HttpHost.class), any(HttpRequest.class)))
                .thenReturn(bonusResponse);

        BonusPointsRequest request = BonusPointsRequest.builder()
                .username("pokemaniac")
                .amount(amount)
                .build();
        this.client = new RevloClient("apiKey", fakeClient, httpHost,1);
        client.bonusPoints(request);
    }

    @Test(expected = RevloClientException.class)
    public void bonusPointsToAUserMoreThanOneMillion__shouldFail() throws Exception {
        bonusAmountOverflowClient(1000001);
    }

    @Test(expected = RevloClientException.class)
    public void negativeBonusPointsToAUserMoreThanOneMillion__shouldFail() throws Exception {
        bonusAmountOverflowClient(-1000001);
    }

    @Test
    public void rewards__shouldGiveRewardList() throws Exception {
        StatusLine fakeStatusLine = mock(StatusLine.class);
        when(fakeStatusLine.getStatusCode()).thenReturn(200);
        CloseableHttpResponse rewardsResponse = mock(CloseableHttpResponse.class);
        String data = "{\"rewards\":[{\"reward_id\":3," +
                "\"created_at\":\"2016-11-17T21:07:47.762Z\"," +
                "\"title\":\"Display Random giphy\",\"bot_command\":\"giphy\"," +
                "\"enabled\":true,\"points\":300,\"sub_only\":false,\"input_fields\":[]}," +
                "{\"reward_id\":2,\"created_at\":\"2016-11-17T21:07:35.615Z\"," +
                "\"title\":\"Song Requests\",\"bot_command\":\"song\",\"enabled\":true," +
                "\"points\":100,\"sub_only\":false,\"input_fields\":[]}," +
                "{\"reward_id\":1,\"created_at\":\"2016-11-17T21:07:24.924Z\",\"" +
                "title\":\"Reward Suggestions\",\"bot_command\":\"suggestions\"," +
                "\"enabled\":true,\"points\":1,\"sub_only\":false,\"input_fields\":[]}]," +
                "\"total\":3,\"page_size\":25}";
        when(rewardsResponse.getEntity()).thenReturn(new StringEntity(data));
        when(rewardsResponse.getStatusLine()).thenReturn(fakeStatusLine);
        HttpHost httpHost = new HttpHost("");

        CloseableHttpClient fakeClient = mock(CloseableHttpClient.class);
        when(fakeClient.execute(any(HttpHost.class), any(HttpRequest.class)))
                .thenReturn(rewardsResponse);

        GetRewardsRequest request = GetRewardsRequest.builder().build();
        this.client = new RevloClient("apiKey", fakeClient, httpHost,1);
        RewardsResponse response = client.getRewards(request);
        List<Model.Reward> li = response.getRewards();
        assertEquals(3, response.getTotal());
        assertEquals(25, response.getPageSize());
        assertEquals(3, response.getRewards().size());
    }


    @Test
    public void redemptions__shouldGiveRedemptionsList() throws Exception {
        StatusLine fakeStatusLine = mock(StatusLine.class);
        when(fakeStatusLine.getStatusCode()).thenReturn(200);
        CloseableHttpResponse redemptionsResponse = mock(CloseableHttpResponse.class);
        String data = "{\"redemptions\":[{\"reward_id\":2,\"redemption_id\":26," +
                            "\"created_at\":\"2016-11-17T21:24:20.703Z\",\"refunded\":false," +
                            "\"completed\":false,\"user_input\":{\"song\":\n" +
                            "\"https://www.youtube.com/watch?v=VN8GXJBJpr0\"}," +
                            "\"username\":\"youngster\"}," +
                        "{\"reward_id\":2,\"redemption_id\":25," +
                "\"created_at\":\"2016-11-17T21:24:17.069Z\"" +
                ",\"refunded\":false,\"completed\":false,\"user_input\":{\"song\":\"" +
                "https://www.youtube.com/watch?v=i25zLvU_xcs\"},\"username\":\"cooltrainer\"}" +
                ",{\"reward_id\":2,\"redemption_id\":24,\"created_at\":\"2016-11-17T21:24:15.802Z\"" +
                ",\"refunded\":false,\"completed\":false,\"user_input\":{\"song\":\"" +
                "https://www.youtube.com/watch?v=xMk8wuw7nek\"},\"username\":\"pokemaniac\"}," +
                "{\"reward_id\":2,\"redemption_id\":22,\"created_at\":\"2016-11-17T21:24:12.621Z\"," +
                "\"refunded\":false,\"completed\":false,\"user_input\":{\"song\":\"" +
                "https://www.youtube.com/watch?v=mRt0d1O4tiE\"},\"username\":\"bugcatcher\"}]" +
                ",\"total\":4,\"page_size\":25}";
        when(redemptionsResponse.getEntity()).thenReturn(new StringEntity(data));
        when(redemptionsResponse.getStatusLine()).thenReturn(fakeStatusLine);
        HttpHost httpHost = new HttpHost("");

        CloseableHttpClient fakeClient = mock(CloseableHttpClient.class);
        when(fakeClient.execute(any(HttpHost.class), any(HttpRequest.class)))
                .thenReturn(redemptionsResponse);

        GetRedemptionsRequest request = GetRedemptionsRequest.builder()
                .refunded(false)
                .page(1)
                .completed(false)
                .build();
        this.client = new RevloClient("apiKey", fakeClient, httpHost,1);
        RedemptionsResponse response = client.getRedemptions(request);
        assertEquals(4, response.getTotal());
        assertEquals(25, response.getPageSize());
        assertEquals(4, response.getRedemptions().size());
    }

    @Test
    public void redemption__shouldGiveRedemption() throws Exception {
        StatusLine fakeStatusLine = mock(StatusLine.class);
        when(fakeStatusLine.getStatusCode()).thenReturn(200);
        CloseableHttpResponse redemptionResponse = mock(CloseableHttpResponse.class);
        String data = "{\"redemption\":{\"reward_id\":2,\"redemption_id\":1337," +
                "\"created_at\":\"2016-11-17T21:24:17.069Z\",\"refunded\":false," +
                "\"completed\":false,\"user_input\":{\"song\":\"" +
                "https://www.youtube.com/watch?v=mRt0d1O4tiE\"},\"username\":\"pokemaniac\"}}";
        when(redemptionResponse.getEntity()).thenReturn(new StringEntity(data));

        when(redemptionResponse.getStatusLine()).thenReturn(fakeStatusLine);
        HttpHost httpHost = new HttpHost("");

        CloseableHttpClient fakeClient = mock(CloseableHttpClient.class);
        when(fakeClient.execute(any(HttpHost.class), any(HttpRequest.class)))
                .thenReturn(redemptionResponse);
        GetRedemptionRequest request = GetRedemptionRequest.builder().id(1337).build();
        this.client = new RevloClient("apiKey", fakeClient, httpHost,1);

        RedemptionResponse response = client.getRedemption(request);
        Model.Redemption redemption = response.getRedemption();
        assertEquals(2, redemption.getRewardId());
        assertEquals(1337, redemption.getRedemptionId());
        assertEquals("2016-11-17T21:24:17.069Z", redemption.getCreatedAt());
        assertEquals(false, redemption.getRefunded());
        assertEquals(false, redemption.getCompleted());
        assertEquals(ImmutableMap.of("song","https://www.youtube.com/watch?v=mRt0d1O4tiE"), redemption.getUserInput());
        assertEquals("pokemaniac", redemption.getUsername());
    }

    @Test
    public void updateRedemption__shouldGiveRedemption() throws Exception {
        StatusLine fakeStatusLine = mock(StatusLine.class);
        when(fakeStatusLine.getStatusCode()).thenReturn(200);
        CloseableHttpResponse redemptionResponse = mock(CloseableHttpResponse.class);
        String data = "{\"redemption\":{\"reward_id\":2,\"redemption_id\":1337," +
                "\"created_at\":\"2016-11-17T21:24:17.069Z\",\"refunded\":false," +
                "\"completed\":true,\"user_input\":{\"song\":\"" +
                "https://www.youtube.com/watch?v=mRt0d1O4tiE\"},\"username\":\"pokemaniac\"}}";
        when(redemptionResponse.getEntity()).thenReturn(new StringEntity(data));

        when(redemptionResponse.getStatusLine()).thenReturn(fakeStatusLine);
        HttpHost httpHost = new HttpHost("");

        CloseableHttpClient fakeClient = mock(CloseableHttpClient.class);
        when(fakeClient.execute(any(HttpHost.class), any(HttpRequest.class)))
                .thenReturn(redemptionResponse);
        UpdateRedemptionRequest request = UpdateRedemptionRequest.builder()
                .id(1337)
                .completed(true)
                .build();
        this.client = new RevloClient("apiKey", fakeClient, httpHost,1);

        RedemptionResponse response = client.updateRedemption(request);
        Model.Redemption redemption = response.getRedemption();
        assertEquals(2, redemption.getRewardId());
        assertEquals(1337, redemption.getRedemptionId());
        assertEquals("2016-11-17T21:24:17.069Z", redemption.getCreatedAt());
        assertEquals(false, redemption.getRefunded());
        assertEquals(true, redemption.getCompleted());
        assertEquals(ImmutableMap.of("song","https://www.youtube.com/watch?v=mRt0d1O4tiE"), redemption.getUserInput());
        assertEquals("pokemaniac", redemption.getUsername());
    }

    @Test
    public void getReward__shouldGetReward() throws Exception {
        StatusLine fakeStatusLine = mock(StatusLine.class);
        when(fakeStatusLine.getStatusCode()).thenReturn(200);
        CloseableHttpResponse redemptionResponse = mock(CloseableHttpResponse.class);
        String data = "{\"reward\":{\"reward_id\":3, \"created_at\":\"2016-11-17T21:07:47.762Z\"," +
                "\"title\":\"Display Random giphy\",\"bot_command\":\"giphy\",\"enabled\":true," +
                "\"points\":300,\"sub_only\":false,\"input_fields\":[]}}";
        when(redemptionResponse.getEntity()).thenReturn(new StringEntity(data));

        when(redemptionResponse.getStatusLine()).thenReturn(fakeStatusLine);
        HttpHost httpHost = new HttpHost("");

        CloseableHttpClient fakeClient = mock(CloseableHttpClient.class);
        when(fakeClient.execute(any(HttpHost.class), any(HttpRequest.class)))
                .thenReturn(redemptionResponse);
        GetRewardRequest request = GetRewardRequest.builder()
                .id(3)
                .build();
        this.client = new RevloClient("apiKey", fakeClient, httpHost,1);

        RewardResponse response = client.getReward(request);
        Model.Reward reward = response.getReward();
        assertEquals(3, reward.getRewardId());
        assertEquals("2016-11-17T21:07:47.762Z", reward.getCreatedAt());
        assertEquals("Display Random giphy", reward.getTitle());
        assertEquals("giphy", reward.getBotCommand());
        assertEquals(true, reward.getEnabled());
        assertEquals(300, reward.getPoints());
        assertEquals(false, reward.getSubOnly());
        assertEquals(new ArrayList<String>(), reward.getInputFields());
    }

    @Test
    public void getLoyalty_shouldGetLoyalty() throws Exception {
        StatusLine fakeStatusLine = mock(StatusLine.class);
        when(fakeStatusLine.getStatusCode()).thenReturn(200);
        CloseableHttpResponse redemptionResponse = mock(CloseableHttpResponse.class);
        String data = "{\"loyalty\":{\"fan\":\"pokemaniac\",\"total_points\":0,\"current_points\":0," +
                "\"updated_at\":\"2016-09-14T16:02:00.000Z\"}}\n";
        when(redemptionResponse.getEntity()).thenReturn(new StringEntity(data));

        when(redemptionResponse.getStatusLine()).thenReturn(fakeStatusLine);
        HttpHost httpHost = new HttpHost("");

        CloseableHttpClient fakeClient = mock(CloseableHttpClient.class);
        when(fakeClient.execute(any(HttpHost.class), any(HttpRequest.class)))
                .thenReturn(redemptionResponse);
        GetLoyaltyRequest request = GetLoyaltyRequest.builder()
                .username("pokemaniac")
                .build();
        this.client = new RevloClient("apiKey", fakeClient, httpHost,1);

        LoyaltyResponse response = client.getLoyalty(request);
        Model.Loyalty loyalty= response.getLoyalty();
        assertEquals("pokemaniac", loyalty.getFan());
        assertEquals(0,loyalty.getTotalPoints());
        assertEquals(0,loyalty.getCurrentPoints());
        assertEquals("2016-09-14T16:02:00.000Z", loyalty.getUpdatedAt());
    }
}
