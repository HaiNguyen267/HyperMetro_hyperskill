package metro;

import com.google.gson.JsonObject;

public class TransferStation {
    private String lineName;
    private String stationName;

    public TransferStation(String lineName, String stationName) {
        this.lineName = lineName;
        this.stationName = stationName;
    }

    public String getLineName() {
        return lineName;
    }
    public String getStationName() {
        return stationName;
    }


    public static TransferStation createInstanceFromJsonObject(JsonObject jsonObject) {
        String line = jsonObject.get("line").getAsString();
        String name = jsonObject.get("station").getAsString();

        return new TransferStation(line, name);
    }
}
