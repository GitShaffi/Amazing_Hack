package com.amazon.hack.amazing;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DownStreamListener {
    public static void main(String[] args) {
        //This will  load the our listener
        ApplicationContext c1 = new ClassPathXmlApplicationContext("downstream-listener-context.xml");

    }
}
