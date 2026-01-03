package com.example.iotplatform.controller;

import com.example.iotplatform.model.SensorReading;
import com.example.iotplatform.service.IoTSensorManagerService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/iot-dashboard")
public class IoTSensorControllerServlet extends HttpServlet {

    @Inject
    private IoTSensorManagerService iotSensorManagerService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<SensorReading> readings = iotSensorManagerService.retrieveAllSensorReadings();
        request.setAttribute("readings", readings);
        request.getRequestDispatcher("/WEB-INF/views/iot-dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sensorId = request.getParameter("sensorId");
        String sensorType = request.getParameter("sensorType");
        String valueStr = request.getParameter("value");
        String unit = request.getParameter("unit");
        String location = request.getParameter("location");

        if (sensorId != null && !sensorId.isEmpty() && valueStr != null && !valueStr.isEmpty()) {
            double value = Double.parseDouble(valueStr);
            SensorReading newReading = new SensorReading(
                    UUID.randomUUID().toString(),
                    sensorId,
                    sensorType,
                    value,
                    unit,
                    System.currentTimeMillis(),
                    location);
            iotSensorManagerService.ingestSensorReading(newReading);
        }

        response.sendRedirect(request.getContextPath() + "/iot-dashboard");
    }
}
