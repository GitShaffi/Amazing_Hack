package com.amazon.hack.amazing.utils;

import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * Created by shaffi on 17/06/15.
 */
public class RestClient {
    static RestTemplate restTemplate = new RestTemplate();

    public static Object getResponse(String url, MultiValueMap urlParams, Class responseClass) {

        return restTemplate.getForObject(buildUri(url, urlParams), responseClass);
    }

    private static String buildUri(String url, MultiValueMap urlParams){
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        UriComponents uriComponents = builder.queryParams(urlParams).build();
        return uriComponents.toUriString();
    }


}
