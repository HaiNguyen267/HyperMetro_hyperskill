package metro;

import com.google.gson.JsonObject;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.TreeMap;

public class Line {
    private String lineName;
    private Station head;
    private Station tail;

    public Line() {}

    public Line(String lineName) {
        this.lineName = lineName;
    }

    public void addToTail(Station station) {
        station.setLineName(lineName);

        if (isEmpty()) {
            // when the line is empty, the first station will be both the head and the tail
            head = station;
            tail = station;
            head.setNext(tail);
        } else {
            tail.setNext(station);
            tail = station;
        }
    }

    public void addToHead(Station station) {
        station.setLineName(lineName);

        if (isEmpty()) {
            // when the line is empty, the first station will be both the head and the tail
            head = station;
            tail = station;
            head.setNext(tail);
        } else {
            station.setNext(head);
            head = station;
        }
    }

    public void remove(String stationName) {
        Station current = head;

        while (current != null) {
            if (current.getStationName().equals(stationName)) {
                if (isHead(current) && isTail(current)) {
                    head = null;
                    tail = null;
                } else if (isHead(current)) {
                    removeHead();
                } else if (isTail(current)) {
                    removeTail();
                } else {
                    Station prev = current.getPrev();
                    Station next = current.getNext();

                    prev.setNext(next);
                }
            }
            current = current.getNext();
        }
    }

    public void output() {
        addToHead(new Station("depot"));
        addToTail(new Station("depot"));

        Station current = head;
        while (current != null) {
            System.out.println(current);
            current = current.getNext();
        }

        removeHead();
        removeTail();
    }

    public Station getStation(String stationName) {
        Station current = head;

        while (current != null) {
            if (current.getStationName().equals(stationName)) {
                return current;
            }
            current = current.getNext();
        }

        return null;
    }
    private boolean isHead(Station station) {
        return Objects.equals(station, head);
    }

    private boolean isTail(Station station) {
        return Objects.equals(station, tail);
    }

    private boolean isEmpty() {
        return head == null && tail == null;
    }

    private void removeHead() {
        head = head.getNext();
        head.setPrev(null);
    }

    private void removeTail() {
        tail = tail.getPrev();
        tail.setPrev(null);
    }

    public static Line parseFromJsonObject(JsonObject lineJsonObject, String lineName) {
        Line line = new Line(lineName);

        TreeMap<Integer, Station> stations = convertAndSortStations(lineJsonObject);
        addStationsToLine(line,stations);
        return line;
    }

    private static TreeMap<Integer, Station> convertAndSortStations(JsonObject lineJsonObject) {

        TreeMap<Integer, Station> treeMap = new TreeMap<>();
        Iterator<String> iter = lineJsonObject.keySet().iterator();

        while (iter.hasNext()) {
            String stationNumber = iter.next();
            JsonObject stationJsonObject = lineJsonObject.get(stationNumber).getAsJsonObject();

            Station station = Station.parseFromJsonObject(stationJsonObject);
            treeMap.put(Integer.parseInt(stationNumber), station);
        }

        return treeMap;
    }

    private static void addStationsToLine(Line line, TreeMap<Integer, Station> treeMap) {
        for (Station station: treeMap.values()) {
            line.addToTail(station);
        }
    }


}
