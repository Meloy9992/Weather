import org.json.simple.JSONObject;

import java.util.Date;
import java.util.List;

public interface TaskInterface {
    void printDayAndNightTemp(JSONObject jsonObject);

    List<String> getMaxDayLightHours(JSONObject jsonObject);

    float getCelsiusFromKelvin(float kelvin);

    float getDifferenceBetweenFeelsActual(float feels, float actual);

    String convertUnixDate(String unixDate);
}
