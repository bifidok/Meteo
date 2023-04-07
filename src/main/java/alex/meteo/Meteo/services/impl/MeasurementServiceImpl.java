package alex.meteo.Meteo.services.impl;

import alex.meteo.Meteo.models.Measurement;
import alex.meteo.Meteo.models.Sensor;
import alex.meteo.Meteo.repositories.MeasurementRepository;
import alex.meteo.Meteo.services.MeasurementService;
import alex.meteo.Meteo.util.measurements.MeasurementNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class MeasurementServiceImpl implements MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorsServiceImpl sensorsServiceImpl;

    public MeasurementServiceImpl(MeasurementRepository measurementRepository, SensorsServiceImpl sensorsServiceImpl) {
        this.measurementRepository = measurementRepository;
        this.sensorsServiceImpl = sensorsServiceImpl;
    }

    public List<Measurement> getMeasurements(){
        return measurementRepository.findAll();
    }
    public Measurement getMeasurement(int id){
        return measurementRepository.findById(id).orElseThrow(() -> new MeasurementNotFoundException("Measurement not found"));
    }
    public Integer getRainyDays(){
        return measurementRepository.countMeasurementsByIsRainingIs(true);
    }
    @Transactional
    public void createMeasurement(Measurement measurement){
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }
    private void enrichMeasurement(Measurement measurement){
        measurement.setCreatedAt(LocalDateTime.now());
        Sensor sensor = sensorsServiceImpl.getSensorByName(measurement.getSensor().getName());
        measurement.setSensor(sensor);
    }

}
