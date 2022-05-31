package metro;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Metro {
    private static Metro metro;

    private Map<String, Line> map = new HashMap<String, Line>();

    public void fastestRoute(String lineName1, String stationName1, String lineName2, String stationName2) {
        Station start = getStation(lineName1, stationName1);
        Station dest = getStation(lineName2, stationName2);

        Route route = new Route(start, dest);
        route.findAndPrintFastestRoute();

    }

    public void route(String lineName1, String stationName1, String lineName2, String stationName2) {
        Station start = getStation(lineName1, stationName1);
        Station dest = getStation(lineName2, stationName2);

        Route route = new Route(start, dest);
        route.findAndPrintRoute();

    }

    public void connect(String lineName1, String stationName1, String lineName2, String stationName2) {

        if (containsLine(lineName1) && containsLine(lineName2)) {
            Station station1 = getLine(lineName1).getStation(stationName1);
            Station station2 = getLine(lineName2).getStation(stationName2);

            if (station1 != null && station2 != null) {
                station1.connect(station2);
                station2.connect(station1);
            }
        }
    }

    public void add(String lineName, String stationName, int timeToNext) {
        if (!containsLine(lineName)) {
            addLine(lineName, new Line());
        }

        getLine(lineName).addToTail(new Station(stationName, timeToNext));
    }
    public void addToTail(String lineName, String stationName) {
        if (!containsLine(lineName)) {
            addLine(lineName, new Line());
        }

        getLine(lineName).addToTail(new Station(stationName));
    }
    public void addToHead(String lineName, String stationName) {
        if (!containsLine(lineName)) {
            addLine(lineName, new Line());
        }

        getLine(lineName).addToHead(new Station(stationName));
    }

    public void remove(String lineName, String stationName) {
        if (!containsLine(lineName)) {
            return;
        }

        getLine(lineName).remove(stationName);
    }

    public void outputLine(String lineName) {
        if (!containsLine(lineName)) {
            return;
        }

        getLine(lineName).output();
    }

    public Line getLine(String lineName) {
        return map.get(lineName);
    }

    private boolean containsLine(String lineName) {
        return getLine(lineName) != null;
    }

    public Station getStation(String lineName, String stationName) {
        if (containsLine(lineName)) {
            return getLine(lineName).getStation(stationName);
        }

        return null;
    }

    private void addLine(String lineName, Line line) {
        map.put(lineName, line);
    }
    public static void initInstanceFromJsonObject(JsonObject metroJsonObject) {
        metro = new Metro();
        Iterator<String> iter = metroJsonObject.keySet().iterator();

        while (iter.hasNext()) {
            String lineName = iter.next();
            JsonObject lineJsonObject = metroJsonObject.get(lineName).getAsJsonObject();

            Line line = Line.parseFromJsonObject(lineJsonObject, lineName);
            metro.addLine(lineName, line);
        }
    }

    public static Metro getInstance() {
        return metro;
    }
}
