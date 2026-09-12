package com.railsync.backend.station;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StationService {

    private final StationRepository repository;

    public StationService(StationRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Station> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Station getByCode(String stationCode) {
        return repository.findById(normalizeCode(stationCode))
                .orElseThrow(() -> new StationNotFoundException(stationCode));
    }

    @Transactional(readOnly = true)
    public List<Station> search(String text) {
        String value = text == null ? "" : text.trim();
        if (value.isEmpty()) {
            return repository.findAll();
        }
        return repository.findByStationCodeContainingIgnoreCaseOrStationNameContainingIgnoreCase(value, value);
    }

    public Station create(StationRequest request) {
        String code = normalizeCode(request.stationCode());
        if (repository.existsById(code)) {
            throw new IllegalStateException("Station code already exists: " + code);
        }
        return repository.save(new Station(
                code,
                request.stationName(),
                request.city(),
                request.state(),
                request.latitude(),
                request.longitude(),
                request.platformCount()));
    }

    public Station update(String stationCode, StationRequest request) {
        Station station = getByCode(stationCode);
        station.update(
                request.stationName(),
                request.city(),
                request.state(),
                request.latitude(),
                request.longitude(),
                request.platformCount(),
                request.status());
        return repository.save(station);
    }

    public void delete(String stationCode) {
        Station station = getByCode(stationCode);
        repository.delete(station);
    }

    private String normalizeCode(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Station code is required.");
        }
        return value.trim().toUpperCase();
    }
}
