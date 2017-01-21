package com.revlo.clients;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.revlo.Model;
import com.revlo.exceptions.RevloClientException;
import com.revlo.exceptions.RevloServiceException;
import com.revlo.requests.BonusPointsRequest;
import com.revlo.requests.GetLoyaltyRequest;
import com.revlo.requests.GetRedemptionRequest;
import com.revlo.requests.GetRedemptionsRequest;
import com.revlo.requests.GetRewardRequest;
import com.revlo.requests.GetRewardsRequest;
import com.revlo.requests.UpdateRedemptionRequest;
import com.revlo.responses.LoyaltyResponse;
import com.revlo.responses.RedemptionResponse;
import com.revlo.responses.RedemptionsResponse;
import com.revlo.responses.RewardResponse;
import com.revlo.responses.RewardsResponse;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;

enum HttpMethod {
    GET, PATCH, POST, PUT, DELETE;
}

public class RevloClient implements Revlo {

    private HttpHost httpHost;
    private String apiKey;
    private int version;
    private String baseUrl;
    private HttpClient httpClient;
    private static final Gson GSON = new Gson();

    public RevloClient(String apiKey) {
        this(apiKey, HttpClients.createDefault());
    }

    RevloClient(String apiKey, HttpClient httpClient) {
        this(apiKey, httpClient, "https://api.revlo.co", 1);
    }

    RevloClient(String apiKey, HttpClient httpClient, String baseUrl, int version) {
        this.apiKey = apiKey;
        this.version = version;
        this.baseUrl = baseUrl;
        this.httpHost = HttpHost.create(baseUrl);
        this.httpClient = httpClient;
    }

    private Object invoke(HttpMethod method, String endpoint, String payload, Type type) throws RevloServiceException {
        InputStream is;
        Reader reader;
        try{
            HttpResponse httpResponse = this.makeRequest(method, endpoint, payload);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            System.out.println(httpResponse.toString());
            if (statusCode < 400) {
                is = httpResponse.getEntity().getContent();
                reader = new BufferedReader(new InputStreamReader(is));
                String json = CharStreams.toString(reader);
                return GSON.fromJson(json, type);
            }
            handleErrors(httpResponse);
        } catch(IOException ioe) {
            throw new RevloServiceException("Internal Service Error");
        }
        throw new RevloClientException("Unknown Error");
    }

    private HttpResponse makeRequest(HttpMethod method, String endpoint, String payload) throws IOException{
        HttpRequest httpRequest;
        endpoint = this.baseUrl + endpoint;
        switch(method) {
            case GET:
                httpRequest = new HttpGet(endpoint);
                break;
            case POST:
                HttpPost httpPost = new HttpPost(endpoint);
                httpPost.setEntity(new StringEntity(payload));
                httpRequest = httpPost;
                break;
            case PUT:
                HttpPut httpPut = new HttpPut(endpoint);
                httpPut.setEntity(new StringEntity(payload));
                httpRequest = httpPut;
                break;
            case PATCH:
                HttpPatch httpPatch = new HttpPatch(endpoint);
                httpPatch.setEntity(new StringEntity(payload));
                httpRequest = new HttpPatch(endpoint);
                break;
            case DELETE:
                httpRequest = new HttpDelete(endpoint);
                break;
            default:
                throw new IOException("Unknown HttpMethod type!");
        }
        httpRequest.addHeader("x-api-key", this.apiKey);
        System.out.println(httpRequest.toString());
        return httpClient.execute(this.httpHost, httpRequest);
    }

    private void handleErrors(HttpResponse httpResponse) throws RevloServiceException {
        try {
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode < 500) {
                InputStream is = httpResponse.getEntity().getContent();
                Reader reader = new BufferedReader(new InputStreamReader(is));
                String error = CharStreams.toString(reader);
                throw new RevloClientException(error);
            } else {
                throw new RevloServiceException("Internal Service Error");
            }
        } catch(IOException ioe) {
            throw new RevloClientException(ioe.getMessage());
        }
    }

    private String queryParams(Map<String, String> params) {
        if (params.isEmpty()) {
            return "";
        }
        String query = "?";
        for (String key : params.keySet()) {
            query += key + "=" + params.get(key) + "&";
        }
        return query.substring(0, query.length() - 1);
    }

    public RewardResponse getReward(GetRewardRequest request) throws RevloServiceException {
        String endpoint = "/" + this.version + "/rewards/" + request.getId();
        Type returnType = Model.Reward.class;
        return (RewardResponse)this.invoke(HttpMethod.GET, endpoint, request.payload(), returnType);
    }

    public RewardsResponse getRewards(GetRewardsRequest request) throws RevloServiceException {
        String endpoint = "/" + this.version + "/rewards" + queryParams(request.params());

        Type returnType = RewardsResponse.class;
        return (RewardsResponse)this.invoke(HttpMethod.GET, endpoint, request.payload(), returnType);
    }

    public LoyaltyResponse getLoyalty(GetLoyaltyRequest request) throws RevloServiceException {
        String endpoint = "/" + this.version + "/fans/" + request.getUsername() + "/points";
        Type returnType = LoyaltyResponse.class;
        return (LoyaltyResponse)this.invoke(HttpMethod.GET, endpoint, request.payload(), returnType);
    }

    public LoyaltyResponse bonusPoints(BonusPointsRequest request) throws RevloServiceException {
        String endpoint = "/" + this.version + "/fans/" + request.getUsername() + "/points/bonus";
        Type returnType = LoyaltyResponse.class;
        return (LoyaltyResponse)this.invoke(HttpMethod.POST, endpoint, request.payload(), returnType);
    }

    public RedemptionsResponse getRedemptions(GetRedemptionsRequest request) throws RevloServiceException {
        String endpoint = "/" + this.version + "/redemptions" + queryParams(request.params());
        Type returnType = RedemptionsResponse.class;
        return (RedemptionsResponse)this.invoke(HttpMethod.GET, endpoint, request.payload(), returnType);
    }

    public RedemptionResponse getRedemption(GetRedemptionRequest request) throws RevloServiceException {
        String endpoint = "/" + this.version + "/redemptions/" + request.getId();
        Type returnType = RedemptionResponse.class;
        return (RedemptionResponse)this.invoke(HttpMethod.GET, endpoint, request.payload(), returnType);
    }

    public RedemptionResponse updateRedemption(UpdateRedemptionRequest request) throws RevloServiceException {
        String endpoint = "/" + this.version + "/redemptions/" + request.getId();
        Type returnType = RedemptionResponse.class;
        return (RedemptionResponse)this.invoke(HttpMethod.PATCH, endpoint, request.payload(), returnType);
    }
}
