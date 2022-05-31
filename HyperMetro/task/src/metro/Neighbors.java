package metro;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Neighbors {
    private Station prev;
    private Station next;
    private TransferStation transferStation;

    public Station getPrev() {
        return prev;
    }

    public void setPrev(Station prev) {
        this.prev = prev;
    }

    public Station getNext() {
        return next;
    }

    public void setNext(Station next) {
        this.next = next;
    }

    public TransferStation getTransferStation() {
        return transferStation;
    }

    public void setTransferStation(TransferStation transferStation) {
        this.transferStation = transferStation;
    }


    public List<Station> getNeighborList() {
        List<Station> list = new ArrayList<Station>();

        if (transferStation != null) {
            String line = transferStation.getLineName();
            String name = transferStation.getStationName();

            Station station = Metro.getInstance().getStation(line, name);
            list.add(station);
        }

        list.add(prev);
        list.add(next);

        list.removeIf(Objects::isNull);

        return list;
    }
}
