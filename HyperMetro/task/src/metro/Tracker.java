package metro;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Tracker {
    private Map<Station, Boolean> isVisited = new LinkedHashMap<Station, Boolean>();
    private Map<Station, Integer> timeFromStart = new LinkedHashMap<Station, Integer>();
    private Map<Station, Station> sourceStation = new LinkedHashMap<Station, Station>();

    /**

                                Tracker
     --------------------------------------------------------------
     |  Station |  isVisited  |  timeFromStart  |  sourceStation  |
     --------------------------------------------------------------
     |          |             |                 |                 |
     |          |             |                 |                 |
     |          |             |                 |                 |
     |          |             |                 |                 |
     |          |             |                 |                 |
     |          |             |                 |                 |
     |          |             |                 |                 |
     |          |             |                 |                 |
     --------------------------------------------------------------


     */


    public void addStation(Station station) {
        if (trackerIsEmpty()) {
            addStartStation(station);
        } else {
            // the default value of each new station for running Dijkstra's algorithm
            isVisited.put(station, false);
            timeFromStart.put(station, Integer.MAX_VALUE);
            sourceStation.put(station, null);
        }
    }

    private boolean trackerIsEmpty() {
        return isVisited.isEmpty();
    }

    public void addStartStation(Station station) {
        isVisited.put(station, false);
        timeFromStart.put(station, 0);
        sourceStation.put(station, null);
    }
    public boolean hasVisitedAllStations() {
        return isVisited.values().stream().allMatch(b -> b == true);
    }

    public Station getUnvisitedMinTimeStation() {
        int minTimeFromStart = Integer.MAX_VALUE;
        Station stationWithMinTime = null;

        for (Map.Entry<Station, Boolean> entry : isVisited.entrySet()) {
            if (entry.getValue() == false) {
                if (getTimeFromStart(entry.getKey()) < minTimeFromStart) {
                    minTimeFromStart = timeFromStart.get(entry.getKey());
                    stationWithMinTime = entry.getKey();
                }
            }
        }

        return stationWithMinTime;
    }

    public boolean visited(Station station) {
        return isVisited.get(station);
    }

    public void visit(Station station) {
        isVisited.put(station, true);
    }

    public int getTimeFromStart(Station station) {
        return timeFromStart.get(station);
    }

    public void setTimeFromStart(Station station, int time) {
        timeFromStart.put(station, time);
    }
    public Station getSource(Station station) {
        return sourceStation.get(station);
    }

    public boolean containsStation(Station station) {
        return isVisited.containsKey(station);
    }

    public void setSource(Station station, Station source) {
        sourceStation.put(station,source);
    }

    public void updateTimeAndSourceIfBetter(Station station, int newTime, Station newSource) {
        if (newTime < getTimeFromStart(station)) {
            setTimeFromStart(station, newTime);
            setSource(station, newSource);
        }
    }
}
