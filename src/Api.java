import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Api {
    private String lat = "48.7194"; //COORDINATES YOUR CITY
    private String lon = "44.5018"; //COORDINATES YOUR CITY
    private String apiKey = ""; //YOUR API KEY

    public String getApi = "api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&exclude=alerts&appid=" + apiKey;

    public String getJson() throws Exception {
        String input;

        Api api = new Api();
        URL url = new URL("http://" + api.getApi);

        URLConnection connection = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        input = reader.readLine();
        System.out.println("Ответ по запросу: " + input); //Response from openweathermap.org
        reader.close();

        return input;
    }
}
