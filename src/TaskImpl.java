import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TaskImpl implements TaskInterface {

    @Override
    public void printDayAndNightTemp(JSONObject jsonObject) {
        float day;
        float night;
        float feelDay;
        float feelNight;
        DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Формат округления чисел

        JSONArray array = (JSONArray) jsonObject.get("daily");
        Iterator iterator = array.iterator();
        int j = 1;

        for (int i = 0; i < 5; i++) {

            JSONObject next = (JSONObject) iterator.next();
            JSONObject tempObject = (JSONObject) next.get("temp");
            JSONObject feelsLikeObject = (JSONObject) next.get("feels_like");

            day = Float.parseFloat(tempObject.get("day").toString());
            feelDay = Float.parseFloat(feelsLikeObject.get("day").toString());
            night = Float.parseFloat(tempObject.get("night").toString());
            feelNight = Float.parseFloat(feelsLikeObject.get("night").toString());

            day = getCelsiusFromKelvin(day);
            feelDay = getCelsiusFromKelvin(feelDay);
            night = getCelsiusFromKelvin(night);
            feelNight = getCelsiusFromKelvin(feelNight);

            System.out.println("День " + j);
            System.out.println();
            System.out.println("День: " + decimalFormat.format(day) + " C°");
            System.out.println("Ощущается как: " + decimalFormat.format(feelDay) + " C°" + " (" + decimalFormat.format(getDifferenceBetweenFeelsActual(feelDay, day)) + " C°" + ")");
            System.out.println();
            System.out.println("Ночь: " + decimalFormat.format(night) + " C°");
            System.out.println("Ощущается как: " + decimalFormat.format(feelNight) + " C°" + " (" + decimalFormat.format(getDifferenceBetweenFeelsActual(feelNight, night)) + " C°" + ")");
            System.out.println();
            j++;
            iterator.hasNext();
        }
    }

    @Override
    public List<String> getMaxDayLightHours(JSONObject jsonObject) {
        ArrayList<String> dayLightList = new ArrayList<>();
        JSONArray dailyArray = (JSONArray) jsonObject.get("daily");
        Iterator iterator = dailyArray.iterator();

        for (int i = 0; i < 5; i++) {
            JSONObject next = (JSONObject) iterator.next();
            long sunrise = Long.parseLong(next.get("sunrise").toString());
            long sunset = Long.parseLong(next.get("sunset").toString());
            String currentDate = convertUnixDate(next.get("dt").toString());
            long dayLightHours = sunset - sunrise;
            dayLightList.add("Прожолжительность светового дня " + currentDate + " = " + dayLightHours);
            System.out.println(dayLightList.get(i));
        }

        return dayLightList;
    }

    @Override
    public float getCelsiusFromKelvin(float kelvin) {
        return (float) (kelvin - 273.15);
    }

    @Override
    public float getDifferenceBetweenFeelsActual(float feels, float actual) {
        return feels - actual;
    }

    @Override
    public String convertUnixDate(String unixDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date(Long.parseLong(unixDate) * 1000L);
        return formatter.format(date);
    }
}
