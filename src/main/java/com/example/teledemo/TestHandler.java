package com.example.teledemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatAdministrators;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.ChatMember;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Component
public class TestHandler extends TelegramLongPollingBot {

    private final TestService testService;

    @Autowired
    public TestHandler(TestService testService) {
        this.testService = testService;
    }

    private void listGroupMembers(String chatId, Long userId) {
        try {

            List<ChatMember> members = execute(new GetChatAdministrators(chatId));
            ChatMember chatMember = execute(new GetChatMember(chatId, userId));
            StringBuilder membersList = new StringBuilder("Group Members:\n");

            System.out.println("=====================================");
            System.out.println(chatMember.toString());
            System.out.println("=====================================");
            for (ChatMember member : members) {
                String name = member.getUser().getFirstName() + member.getUser().getLastName();
                membersList.append(name).append("\n");
            }

            sendMessage(chatId, membersList.toString());
        } catch (TelegramApiException e) {
            e.printStackTrace();
            sendMessage(chatId, "Failed to retrieve members.");
        }
        System.out.println("ㅁㅇㄻㅇㄴㄹㅇㄴㄹㄴㅇㄹㄴㅇㄹㅇㄴ");
    }

    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
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

        String chatId = update.getMessage().getChatId().toString();
        Long userId = update.getMessage().getFrom().getId();
        System.out.println(chatId);
        if (text.equals("/tt")) {
            listGroupMembers(chatId, userId);
        }
    }

}
