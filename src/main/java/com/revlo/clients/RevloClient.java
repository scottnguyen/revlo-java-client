package com.revlo.clients;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.revlo.exceptions.RevloClientException;
import com.revlo.exceptions.RevloServiceException;
import com.revlo.models.Error;
import com.revlo.models.Loyalty;
import com.revlo.models.Redemption;
import com.revlo.models.Reward;
import com.revlo.requests.*;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

    public RevloClient(String apiKey, HttpClient httpClient) {
        this(apiKey, httpClient, "https://api.revlo.co", 1);
    }

    public RevloClient(String apiKey, HttpClient httpClient, String baseUrl, int version) {
        this.apiKey = apiKey;
        this.version = version;
        this.baseUrl = baseUrl;
        this.httpHost = HttpHost.create(baseUrl);
        this.httpClient = httpClient;
    }

    private Object invoke(HttpMethod method, String endpoint, Type type) throws RevloServiceException {
        InputStream is;
        Reader reader;
        try{
            HttpResponse httpResponse = this.makeRequest(method, endpoint);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode < 400) {
                is = httpResponse.getEntity().getContent();
                reader = new BufferedReader(new InputStreamReader(is));
                return GSON.fromJson(reader, type);
            }
            handleErrors(httpResponse);
        } catch(IOException ioe) {
            throw new RevloServiceException("Internal Service Error");
        }
        throw new RevloClientException("Unknown Error");
    }

    private HttpResponse makeRequest(HttpMethod method, String endpoint) throws IOException{
        HttpRequest httpRequest;
        endpoint = this.baseUrl + endpoint;
        switch(method) {
            case GET:
                httpRequest = new HttpGet(endpoint);
                break;
            case POST:
                httpRequest = new HttpPost(endpoint);
                break;
            case PUT:
                httpRequest = new HttpPut(endpoint);
                break;
            case PATCH:
                httpRequest = new HttpPatch(endpoint);
                break;
            case DELETE:
                httpRequest = new HttpDelete(endpoint);
                break;
            default:
                throw new IOException("Unknown HttpMethod type!");
        }
        httpRequest.addHeader("x-api-key", this.apiKey);
        return httpClient.execute(this.httpHost, httpRequest);
    }

    private void handleErrors(HttpResponse httpResponse) throws RevloServiceException {
        try {
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode < 500) {
                InputStream is = httpResponse.getEntity().getContent();
                Reader reader = new BufferedReader(new InputStreamReader(is));
                Error error = GSON.fromJson(reader, Error.class);
                throw new RevloClientException(error.getError());
            } else {
                throw new RevloServiceException("Internal Service Error");
            }
        } catch(IOException ioe) {
            throw new RevloClientException(ioe.getMessage());
        }
    }

    public Reward getReward(GetRewardRequest request) throws RevloServiceException {
        String endpoint = "/" + this.version + "/rewards/" + request.getId();
        Type returnType = Reward.class;
        return (Reward)this.invoke(HttpMethod.GET, endpoint, returnType);
    }

    public List<Reward> getRewards(GetRewardsRequest request) throws RevloServiceException {
        String endpoint = "/" + this.version + "/rewards";
        Type returnType = new TypeToken<ArrayList<Reward>>(){}.getType();
        return (List<Reward>)this.invoke(HttpMethod.GET, endpoint, returnType);
    }

    public Loyalty getLoyalty(GetLoyaltyRequest request) throws RevloServiceException {
        String endpoint = "/" + this.version + "/fans/" + request.getUsername() + "/points";
        Type returnType = new TypeToken<Loyalty>(){}.getType();
        return (Loyalty)this.invoke(HttpMethod.GET, endpoint, returnType);
    }

    public Loyalty bonusPoints(BonusPointsRequest request) throws RevloServiceException {
        String endpoint = "/" + this.version + "/fans/" + request.getUsername() + "/points";
        Type returnType = new TypeToken<Loyalty>(){}.getType();
        return (Loyalty)this.invoke(HttpMethod.POST, endpoint, returnType);
    }

    public List<Redemption> getRedemptions(GetRedemptionsRequest request) throws RevloServiceException {
        String endpoint = "/" + this.version + "/redemptions";
        Type returnType = new TypeToken<ArrayList<Redemption>>(){}.getType();
        return (List<Redemption>)this.invoke(HttpMethod.GET, endpoint, returnType);
    }

    public Redemption getRedemption(GetRedemptionRequest request) throws RevloServiceException {
        String endpoint = "/" + this.version + "/redemptions/" + request.getId();
        Type returnType = Redemption.class;
        return (Redemption)this.invoke(HttpMethod.GET, endpoint, returnType);
    }

    public Redemption updateRedemption(UpdateRedemptionRequest request) throws RevloServiceException {
        String endpoint = "/" + this.version + "/redemptions/" + request.getId();
        Type returnType = Redemption.class;
        return (Redemption)this.invoke(HttpMethod.PATCH, endpoint, returnType);
    }
}
