package com.itxwl.getoutserver.config;

import org.quartz.CronExpression;

public class Demo {
    public static void main(String[] args) {
        String cron="* * 4,10,16,22 * * ?";
        boolean validExpression = CronExpression.isValidExpression(cron);
        System.out.println(validExpression);
    }
}
