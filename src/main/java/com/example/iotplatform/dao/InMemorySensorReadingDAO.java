package com.example.iotplatform.dao;

import com.example.iotplatform.model.SensorReading;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@ApplicationScoped
public class InMemorySensorReadingDAO implements SensorReadingDAO {

    // Key: readingId, Value: SensorReading
    private final Map<String, SensorReading> readings = new ConcurrentHashMap<>();

    @Override
    public void addReading(SensorReading reading) {
        readings.put(reading.getReadingId(), reading);
    }

    @Override
    public List<SensorReading> getAllReadings() {
        return new ArrayList<>(readings.values());
    }

    @Override
    public SensorReading getReadingById(String readingId) {
        return readings.get(readingId);
    }

    @Override
    public List<SensorReading> getReadingsBySensorId(String sensorId) {
        return readings.values().stream()
                .filter(r -> r.getSensorId().equals(sensorId))
                .collect(Collectors.toList());
    }

    @Override
    public List<SensorReading> getReadingsBySensorType(String sensorType) {
        return readings.values().stream()
                .filter(r -> r.getSensorType().equalsIgnoreCase(sensorType))
                .collect(Collectors.toList());
    }
}
