package com.revlo.requests;

import java.util.Map;

interface Request {

    /**
     * @return Query parameters of a Revlo Request
     */
    Map<String, String> params();

    /**
     * @return String representation of the JSON payload of this request.
     */
    String payload();
}
