package metro;

import java.util.*;

public class Route {
    private Station start;
    private Station dest;

    private Tracker tracker;


    public Route(Station start, Station dest) {
        this.start = start;
        this.dest = dest;
        this.tracker = initTracker();
    }

    private Tracker initTracker() {
        Tracker tracker = new Tracker();
        Deque<Station> queue = new ArrayDeque<>();

        queue.add(start);
        while (!queue.isEmpty()) {
            Station station = queue.pollLast();

            if (!tracker.containsStation(station)) {
                tracker.addStation(station);

                for (Station neighbor : station.getNeighbors()) {
                    queue.offerFirst(neighbor);
                }
            }
        }

        return tracker;
    }
    public void findAndPrintFastestRoute() {
        runDijkstraAlgo();
        List<Station> path = constructPath();
        printFastestRoute(path);
    }

    private void runDijkstraAlgo() {
        while (!tracker.hasVisitedAllStations()) {
            Station station = tracker.getUnvisitedMinTimeStation();

            for (Station neighbor : station.getNeighbors()) {
                int newTime = tracker.getTimeFromStart(station) + station.getTimeTo(neighbor);
                tracker.updateTimeAndSourceIfBetter(neighbor, newTime, station);
            }

            tracker.visit(station);
        }
    }

    private void printFastestRoute(List<Station> path) {
        // after running the dijkstra's algorithm, the path is the fastest one
        printRoute(path);

        int timeFromStart = tracker.getTimeFromStart(dest);
        System.out.println(String.format("Total: %d minutes in the way", timeFromStart));
    }
    public void findAndPrintRoute() {
        runBFSAlgo();
        List<Station> path = constructPath();
        printRoute(path);
    }

    private void runBFSAlgo() {
        Deque<Station> queue = new ArrayDeque<>();
        Map<Station, Station> sourceStation = new HashMap<>();

        queue.add(start);
        sourceStation.put(start, null); // the start station doesn't have the source station, since it is the first station in the path


        while (!queue.isEmpty()) {
            Station station = queue.pollLast();

            if (!tracker.visited(station)) {

                for (Station neighbor : station.getNeighbors()) {
                    queue.offerFirst(neighbor);
                    sourceStation.putIfAbsent(neighbor, station);
                }

                tracker.setSource(station, sourceStation.get(station));
                tracker.visit(station);
            }
        }
    }

    private void printRoute(List<Station> path) {
        String currentLine = start.getLineName();

        for (Station station : path) {
            if (!station.getLineName().equals(currentLine)) {
                currentLine = station.getLineName();
                System.out.println(String.format("Transition to line %s", currentLine));
            }
            System.out.println(station.getStationName());
        }
    }

    private List<Station> constructPath() {
        List<Station> list = new ArrayList<>();

        Station currentStation = dest;

        while (currentStation != null) {
            list.add(currentStation);
            currentStation = tracker.getSource(currentStation);
        }

        // at this point the dest station is the first element, the start station is the last element
        // reverse the list, so that the start element is the first element to print the path more easily
        Collections.reverse(list);

        return list;
    }

}
