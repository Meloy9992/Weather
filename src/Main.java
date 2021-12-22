import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        TaskImpl task = new TaskImpl();
        Api api = new Api();

        JSONObject mainObject = (JSONObject) new JSONParser().parse(api.getJson());

        task.printDayAndNightTemp(mainObject);

        task.getMaxDayLightHours(mainObject);

    }
}
