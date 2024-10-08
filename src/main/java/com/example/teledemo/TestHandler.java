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

    private final TestService testService;

    @Autowired
    public TestHandler(TestService testService) {
        this.testService = testService;
    }

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
        Message message = update.getMessage();

        int messageId = message.getMessageId();
        String text = message.getText();

        TestEntity testEntity = new TestEntity(messageId, text);
        testService.saveMsg(testEntity);

        log.info(message.toString());
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setText("ㅎㅎㅎㅎ");
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
