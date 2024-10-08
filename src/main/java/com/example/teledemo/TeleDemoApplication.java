package com.example.teledemo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class TeleDemoApplication {

    @Autowired
    private TestHandler testHandler;

    public static void main(String[] args) {
        SpringApplication.run(TeleDemoApplication.class, args);
    }

    @PostConstruct
    public void init() {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(testHandler);
//            api.registerBot(new TestHandler());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
