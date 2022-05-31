package metro;

public class TimeToNeighbors {
    private int timeToPrev = Integer.MAX_VALUE;
    private int timeToNext = Integer.MAX_VALUE;
    private int timeToTransferStation = 5;

    public int getTimeToPrev() {
        return timeToPrev;
    }

    public void setTimeToPrev(int timeToPrev) {
        this.timeToPrev = timeToPrev;
    }

    public int getTimeToNext() {
        return timeToNext;
    }

    public void setTimeToNext(int timeToNext) {
        this.timeToNext = timeToNext;
    }

    public int getTimeToTransferStation() {
        return timeToTransferStation;
    }

    public void setTimeToTransferStation(int timeToTransferStation) {
        this.timeToTransferStation = timeToTransferStation;
    }
}
