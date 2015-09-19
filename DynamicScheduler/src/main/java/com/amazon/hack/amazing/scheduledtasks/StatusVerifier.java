package com.amazon.hack.amazing.scheduledtasks;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class StatusVerifier {

    @Scheduled(fixedRate = 3600000)
    public void identifyInactiveUser() {

    }


}