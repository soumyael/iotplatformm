<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Tableau de Bord IoT</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 900px; margin: 0 auto; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .form-container { background-color: #f9f9f9; padding: 20px; border-radius: 5px; margin-bottom: 20px; }
        .form-group { margin-bottom: 10px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input[type="text"], input[type="number"], select { width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px; }
        button { background-color: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background-color: #45a049; }
    </style>
</head>
<body>
<div class="container">
    <h1>Tableau de Bord IoT</h1>

    <div class="form-container">
        <h2>Simuler une lecture</h2>
        <form action="${pageContext.request.contextPath}/iot-dashboard" method="post">
            <div class="form-group">
                <label for="sensorId">ID Capteur:</label>
                <input type="text" id="sensorId" name="sensorId" required placeholder="Ex: TEMP-001">
            </div>
            <div class="form-group">
                <label for="sensorType">Type:</label>
                <select id="sensorType" name="sensorType">
                    <option value="TEMPERATURE">Temperature</option>
                    <option value="HUMIDITY">Humidity</option>
                    <option value="PRESSURE">Pressure</option>
                    <option value="CO2">CO2</option>
                </select>
            </div>
            <div class="form-group">
                <label for="value">Valeur:</label>
                <input type="number" step="0.01" id="value" name="value" required placeholder="Ex: 23.5">
            </div>
            <div class="form-group">
                <label for="unit">Unité:</label>
                <input type="text" id="unit" name="unit" required placeholder="Ex: °C">
            </div>
            <div class="form-group">
                <label for="location">Emplacement:</label>
                <input type="text" id="location" name="location" required placeholder="Ex: Salle 101">
            </div>
            <button type="submit">Envoyer Donnée</button>
        </form>
    </div>

    <h2>Dernières Lectures</h2>
    <table>
        <thead>
        <tr>
            <th>ID Lecture</th>
            <th>Capteur</th>
            <th>Type</th>
            <th>Valeur</th>
            <th>Emplacement</th>
            <th>Timestamp</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="reading" items="${readings}">
            <tr>
                <td>${reading.readingId}</td>
                <td>${reading.sensorId}</td>
                <td>${reading.sensorType}</td>
                <td>${reading.value} ${reading.unit}</td>
                <td>${reading.location}</td>
                <td>${reading.timestamp}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
