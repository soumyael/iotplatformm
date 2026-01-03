package com.example.iotplatform.service;

import com.example.iotplatform.dao.SensorReadingDAO;
import com.example.iotplatform.model.SensorReading;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class IoTSensorManagerService {

    @Inject
    private SensorReadingDAO sensorReadingDAO;

    public void ingestSensorReading(SensorReading reading) {
        // Business logic could be here: validation, enrichment, etc.
        sensorReadingDAO.addReading(reading);
    }

    public List<SensorReading> retrieveAllSensorReadings() {
        return sensorReadingDAO.getAllReadings();
    }

    public SensorReading findReadingById(String readingId) {
        return sensorReadingDAO.getReadingById(readingId);
    }

    public List<SensorReading> filterReadingsBySensorType(String sensorType) {
        return sensorReadingDAO.getReadingsBySensorType(sensorType);
    }

    public List<SensorReading> getLatestReadings(int limit) {
        return sensorReadingDAO.getAllReadings().stream()
                .sorted(Comparator.comparingLong(SensorReading::getTimestamp).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }
}
