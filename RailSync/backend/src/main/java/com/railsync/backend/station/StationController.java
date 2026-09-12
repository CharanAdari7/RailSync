package com.railsync.backend.station;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
public class StationController {

    private final StationService service;

    public StationController(StationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Station> getStations(@RequestParam(required = false) String search) {
        return service.search(search);
    }

    @GetMapping("/{stationCode}")
    public Station getStation(@PathVariable String stationCode) {
        return service.getByCode(stationCode);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Station createStation(@Valid @RequestBody StationRequest request) {
        return service.create(request);
    }

    @PutMapping("/{stationCode}")
    public Station updateStation(@PathVariable String stationCode,
                                 @Valid @RequestBody StationRequest request) {
        return service.update(stationCode, request);
    }

    @DeleteMapping("/{stationCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStation(@PathVariable String stationCode) {
        service.delete(stationCode);
    }
}
