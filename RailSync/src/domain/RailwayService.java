package domain;

import java.util.Objects;

public class RailwayService {

    private String serviceId;
    private String serviceName;
    private String trainId;
    private String routeId;
    private String serviceType;
    private ServiceStatus status;

    public RailwayService() {
        this.status = ServiceStatus.ACTIVE;
    }

    public RailwayService(String serviceId, String serviceName,
                          String trainId, String routeId,
                          String serviceType) {

        setServiceId(serviceId);
        setServiceName(serviceName);
        setTrainId(trainId);
        setRouteId(routeId);
        setServiceType(serviceType);

        this.status = ServiceStatus.ACTIVE;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        if (serviceId == null || serviceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Service ID cannot be empty.");
        }
        this.serviceId = serviceId.trim();
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        if (serviceName == null || serviceName.trim().isEmpty()) {
            throw new IllegalArgumentException("Service name cannot be empty.");
        }
        this.serviceName = serviceName.trim();
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType == null ? "" : serviceType.trim();
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = Objects.requireNonNull(status);
    }

    @Override
    public String toString() {
        return serviceId + " - " + serviceName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof RailwayService)) return false;

        RailwayService other = (RailwayService) obj;
        return Objects.equals(serviceId, other.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId);
    }
}