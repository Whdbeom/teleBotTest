package com.example.teledemo;

import com.google.gson.JsonObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test {
    public static void main(String[] args) {
        String apiId = "";
        String apiHash = "";
        String botToken = "";
        long chatId = -4518086389L;
        String newTitle = "newTestGroup111";

        try {
            URL url = new URL("https://api.telegram.org/bot" + botToken + "/setChatTitle");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("chat_id", chatId);
            jsonObject.addProperty("title", newTitle);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonObject.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("그룹 이름이 변경되었습니다.");
            } else {
                System.out.println("오류 발생: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
