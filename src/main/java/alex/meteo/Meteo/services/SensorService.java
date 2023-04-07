package alex.meteo.Meteo.services;

import alex.meteo.Meteo.models.Sensor;

import java.util.List;

public interface SensorService {
     List<Sensor> getSensors();
     Sensor getSensor(int id);
     Sensor getSensorByName(String name);

     void createSensor(Sensor sensor);
}
