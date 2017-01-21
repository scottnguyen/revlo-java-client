package com.revlo.requests;

import java.util.Map;

interface Request {

    Map<String, String> params();

    String payload();
}
