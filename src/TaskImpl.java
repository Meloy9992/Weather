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

        JSONArray array = (JSONArray) jsonObject.get("daily"); //Получение массива daily из полученного Json

        Iterator iterator = array.iterator();
        int j = 1;

        for (int i = 0; i < 5; i++) {

            JSONObject next = (JSONObject) iterator.next();
            JSONObject tempObject = (JSONObject) next.get("temp"); //Получение списка объектов temp
            JSONObject feelsLikeObject = (JSONObject) next.get("feels_like"); //Получение списка объектов temp

            day = Float.parseFloat(tempObject.get("day").toString()); //Получение и преобразование объекта day в тип float
            feelDay = Float.parseFloat(feelsLikeObject.get("day").toString()); //Получение и преобразование объекта feelDay в тип float
            night = Float.parseFloat(tempObject.get("night").toString()); //Получение и преобразование объекта night в тип float
            feelNight = Float.parseFloat(feelsLikeObject.get("night").toString()); //Получение и преобразование объекта feelNight в тип float

            day = getCelsiusFromKelvin(day); //Перевод из кельвина в цельсии
            feelDay = getCelsiusFromKelvin(feelDay); //Перевод из кельвина в цельсии
            night = getCelsiusFromKelvin(night); //Перевод из кельвина в цельсии
            feelNight = getCelsiusFromKelvin(feelNight); //Перевод из кельвина в цельсии

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
            String currentDate = convertUnixDate(next.get("dt").toString()); // Конвертация UNIX даты в календарную дату
            long dayLightHours = sunset - sunrise; //Вычисление продолжительности светового дня
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
