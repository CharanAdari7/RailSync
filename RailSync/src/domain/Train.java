package domain;

public class Train {

    private String trainNumber;
    private String trainName;
    private String sourceStationCode;
    private String destinationStationCode;
    private String trainType;
    private ServiceStatus status;

    public Train() {
        this.status = ServiceStatus.ACTIVE;
    }

    public Train(
            String trainNumber,
            String trainName,
            String sourceStationCode,
            String destinationStationCode,
            String trainType) {

        if (trainNumber == null || trainNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Train number is required.");
        }

        this.trainNumber = trainNumber.trim();
        this.trainName = trainName;
        this.sourceStationCode =
                sourceStationCode == null ? "" : sourceStationCode.trim().toUpperCase();
        this.destinationStationCode =
                destinationStationCode == null ? "" : destinationStationCode.trim().toUpperCase();
        this.trainType = trainType;
        this.status = ServiceStatus.ACTIVE;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getSourceStationCode() {
        return sourceStationCode;
    }

    public void setSourceStationCode(String sourceStationCode) {
        this.sourceStationCode = sourceStationCode;
    }

    public String getDestinationStationCode() {
        return destinationStationCode;
    }

    public void setDestinationStationCode(String destinationStationCode) {
        this.destinationStationCode = destinationStationCode;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return trainNumber + " - " + trainName;
    }
}