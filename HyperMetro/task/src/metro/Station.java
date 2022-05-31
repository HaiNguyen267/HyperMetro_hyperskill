package metro;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Station {
    private String lineName;

    private String stationName;

    private Neighbors neighbors;

    private TimeToNeighbors timetoNeighbors;
    public Station(String stationName) {
        this(stationName, Integer.MAX_VALUE);
    }

    public Station(String stationName, int timeToNext) {
        this.stationName = stationName;
        this.neighbors = new Neighbors();
        this.timetoNeighbors = new TimeToNeighbors();
        setTimeToNext(timeToNext);
    }

    public void connect(Station otherStation) {
        String line = otherStation.getLineName();
        String name = otherStation.getStationName();
        setTransferStation(new TransferStation(line, name));
    }


    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getStationName() {
        return stationName;
    }

    public Station getNext() {
        return neighbors.getNext();
    }

    public void setNext(Station station) {
        neighbors.setNext(station);
        if (station != null) {
            station.setPrev(this);
        }
    }

    public Station getPrev() {
        return this.neighbors.getPrev();
    }

    public void setPrev(Station prev) {
        this.neighbors.setPrev(prev);
        if (prev != null) {
            int timeToPrev = prev.getTimeToNext();
            this.setTimeToPrev(timeToPrev);
        }
    }

    public TransferStation getTransferStation() {
        return neighbors.getTransferStation();
    }

    public void setTransferStation(TransferStation transferStation) {
        this.neighbors.setTransferStation(transferStation);
    }

    public List<Station> getNeighbors() {
        return neighbors.getNeighborList();
    }

    public int getTimeToPrev() {
        return timetoNeighbors.getTimeToPrev();
    }

    public void setTimeToPrev(int timeToPrev) {
        timetoNeighbors.setTimeToPrev(timeToPrev);
    }

    public int getTimeToNext() {
        return timetoNeighbors.getTimeToNext();
    }

    public void setTimeToNext(int timeToNext) {
        timetoNeighbors.setTimeToNext(timeToNext);
    }

    public int getTimeToTransferStation() {
        return timetoNeighbors.getTimeToTransferStation();
    }


    public int getTimeTo(Station station) {

        if (Objects.equals(station, getNext())) {
            return getTimeToNext();
        } else if (Objects.equals(station, getPrev())) {
            return getTimeToPrev();
        } else if (station.getStationName().equals(getTransferStation().getStationName())) {
            return getTimeToTransferStation();
        }

        return Integer.MAX_VALUE;
    }

    public boolean transferStationIsTheStationItSelf(TransferStation transferStation) {
        return getStationName().equals(transferStation.getStationName());
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(stationName);

        if (getTransferStation() != null ) {
            String name = getTransferStation().getStationName();
            String line = getTransferStation().getLineName();

            sb.append(String.format(" - %s (%s)", name, line));
        }

        return sb.toString();
    }
    public static Station parseFromJsonObject(JsonObject stationJsonObject) {
        String stationName = stationJsonObject.get("name").getAsString();
        int timeToNext = Integer.MAX_VALUE;

        if (stationJsonObject.get("time") != JsonNull.INSTANCE && stationJsonObject.get("time") != null) {
            timeToNext = stationJsonObject.get("time").getAsInt();
        }

        Station station = new Station(stationName, timeToNext);

        JsonArray transferStationJsonArray = stationJsonObject.get("transfer").getAsJsonArray();
        for (int i = 0; i < transferStationJsonArray.size(); i++) {
             TransferStation transferStation = TransferStation.createInstanceFromJsonObject(transferStationJsonArray.get(i).getAsJsonObject());

             station.setTransferStation(transferStation);

        }

        return station;
    }

}