package com.amazon.hack.amazing.utils;

import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class FileUploader {

    private static final String TEST_FILE = "merchant_payload.csv";
    private static String url = "http://localhost:8080/uploadFile";

    static public void upload() {
        RestTemplate template = new RestTemplate();
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
        parts.add("name", TEST_FILE);
        parts.add("file", new FileSystemResource(TEST_FILE));
        String response = template.postForObject(url,
                parts, String.class);
        System.out.println(response);
    }
}