import com.fasterxml.jackson.core.JsonProcessingException;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static javafx.application.Application.launch;


public class Task1 {

    public Task1() throws UnirestException {
    }

    private static void initApplication() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static void main(String[] args) throws UnirestException {

        System.out.println("Введите массив ID видео через пробел");
        Scanner sc = new Scanner(System.in);
        // int a = sc.nextInt();


        String[] videoID = sc.nextLine().split(" ");


        initApplication();


        for (int i6 = 0; i6 < videoID.length; i6++) {

            HttpResponse<ActivityResponse> response = Unirest.get("https://www.googleapis.com/youtube/v3/commentThreads")
                    .queryString("part", "snippet")
                    .queryString("videoId", videoID[i6])
                    // .queryString("maxResults", maxResults)
                    .queryString("key", "AIzaSyAoJtBYFKQT3rF5Qy0P0hHEFdAMDnOXOhA")
                    .asObject(ActivityResponse.class);

            // System.out.println(response.getBody());
            // System.out.println(response.getStatus());


            ActivityResponse activity = response.getBody();

            for (int i = 0; i < activity.items.size(); i++) {
                final Activity item = activity.items.get(i);
                String authorDisplayName = item.snippet.topLevelComment.snippet.authorDisplayName;
                 System.out.println(authorDisplayName);
            }


        }
    }
}

