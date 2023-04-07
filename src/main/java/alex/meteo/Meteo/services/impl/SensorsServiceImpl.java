package alex.meteo.Meteo.services.impl;

import alex.meteo.Meteo.models.Sensor;
import alex.meteo.Meteo.repositories.SensorsRepository;
import alex.meteo.Meteo.services.SensorService;
import alex.meteo.Meteo.util.sensors.SensorNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SensorsServiceImpl implements SensorService {
    private final SensorsRepository sensorsRepository;

    public SensorsServiceImpl(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }
    public List<Sensor> getSensors(){
        return sensorsRepository.findAll();
    }
    public Sensor getSensor(int id){
        return sensorsRepository.findById(id).orElseThrow(() -> new SensorNotFoundException("Sensor not found"));
    }
    public Sensor getSensorByName(String name){
        return sensorsRepository.findByName(name);
    }
    @Transactional
    public void createSensor(Sensor sensor){
        sensorsRepository.save(sensor);
    }

}
