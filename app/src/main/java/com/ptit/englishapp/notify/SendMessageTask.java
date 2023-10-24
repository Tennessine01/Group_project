package com.ptit.englishapp.notify;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendMessageTask extends AsyncTask<String, Void, Void> {

    private static final String serverKey = "AAAAXUU5jd4:APA91bEg_JyTWjSdI4twsmyZbvwgB3QwnE5BMlWd2x5Ecqr0lM60-jU9uGftp9kuXHZkjccuqcFX-lV5lAcrXwGu2aJ939YflaJqi9rnkaMNUPXUYyZfbdXSdVgY-oQqtJPy7lfhJgnj";

    // bởi vì exception nên phải tạo ra 1 cái asyncTask để ko ảnh hưởng đến luồng main

    @Override
    protected Void doInBackground(String... params) {
        String userFCMToken = params[0];
        String title = params[1];
        String messageBody = params[2];

        try {
            // Tạo kết nối tới FCM API
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Thiết lập phương thức POST
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Thiết lập header Authorization với giá trị "key=YOUR_SERVER_KEY"
            conn.setRequestProperty("Authorization", "key=" + serverKey);
            conn.setRequestProperty("Content-Type", "application/json");

            // Tạo JSON payload cho tin nhắn
            String payload = "{\"to\": \"" + userFCMToken + "\", " +
                    "\"notification\": {\"title\": \"" + title + "\", " +
                    "\"body\": \"" + messageBody + "\"}}";

            // Gửi payload đến FCM API

            DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
            outputStream.write(payload.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            // Đọc phản hồi từ FCM API
            int responseCode = conn.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Kiểm tra phản hồi
            if (responseCode == 200) {
                System.out.println("Tin nhắn đã được gửi thành công.");
            } else {
                System.out.println("Gửi tin nhắn thất bại. Phản hồi từ FCM API: " + response.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        cancel(true);
    }
}