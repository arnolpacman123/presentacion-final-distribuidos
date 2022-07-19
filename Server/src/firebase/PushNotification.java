package firebase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PushNotification {

    private static final String serverToken = "AAAAecinpnc:APA91bHyHM6FJfYTpPOwJsy6F6pMbNNNfZAirzUQrU6Jo3rkS6CTTGjSwTdPyh-KKuJt72UAMfExh3yb-yoKZmUIH0nDbySyhgbd9OM3e7JFcQbcfj0Cps5D0YbVrspeV1U7zQ62NNP9";
    private static final String deviceToken = "cMDN7ZdxQ5i3tMj8cw29Ku:APA91bG3Mhg0QSf6q9WlD6hEryKJF0k7ml7ezbwnEluds34xGfvw3d8XZUbDnSscGFrN9IUc9TRsUnqjLZZfanGCF_tLIC1ZCn5Z9sCWcc2h64ryOQUDwt40F6aCTI4MVGF1pRooviwh";
    private static final String bodyString = "Un dispositivo envio data";
    private static final String titleString = "Nueva data";
    private static final String jsonInputString = "{\"registration_ids\": [\"" + deviceToken
            + "\"], \"notification\": { \"body\": \"" + bodyString + "\", \"title\": \"" + titleString + "\" }}";
    private static final String urlFirebaseAppString = "https://fcm.googleapis.com/fcm/send";
    private static URL url = null;
    private static HttpURLConnection connection = null;

    private static URL getUrl() {
        if (url == null) {
            try {
                url = new URL(urlFirebaseAppString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    private static HttpURLConnection getConnection() {
        if (connection == null) {
            try {
                connection = (HttpURLConnection) getUrl().openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Authorization", "key=" + serverToken);
                connection.setDoOutput(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return connection;
    }

    private static String getJsonInputString(String titleString, String bodyString, String id) {
        return "{\"registration_ids\": [\"" + deviceToken + "\"], \"notification\": { \"body\": \"" + bodyString
                + "\", \"title\": \"" + titleString + "\" }, \"data\": { \"id\": \"" + id + "\" }}";
    }

    public static void sendNotification(String title, String body, String id) {
        try {
            try (OutputStream os = getConnection().getOutputStream()) {
                String json = getJsonInputString(title, body, id);
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                // System.out.println(response.toString());
            }
            connection = null;
            url = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
