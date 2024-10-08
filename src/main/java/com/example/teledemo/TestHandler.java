package com.example.teledemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class TestHandler extends TelegramLongPollingBot {

    @Autowired
    private UpdateHandler updateHandler;

    @Override
    public String getBotUsername() {
        return "whdqjazz_bot";
    }
    @Override
    public String getBotToken() {
        return "";
    }

    @Override
    public void onUpdateReceived(Update update){
        if (updateHandler != null) {
            updateHandler.handleUpdate(update, this);
        } else {
            log.error("업데이트핸들러 널");
        }
    }

}
