package com.example.teledemo;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class UpdateHandler {

    private final TestService testService;

    @Autowired
    public UpdateHandler(TestService testService) {
        this.testService = testService;
    }

    public void handleUpdate(Update update, TelegramLongPollingBot bot) {
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
                bot.execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}