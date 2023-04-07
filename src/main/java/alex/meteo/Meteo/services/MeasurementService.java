package alex.meteo.Meteo.services;

import alex.meteo.Meteo.models.Measurement;

import java.util.List;

public interface MeasurementService {
    List<Measurement> getMeasurements();

    Measurement getMeasurement(int id);

    Integer getRainyDays();

    void createMeasurement(Measurement measurement);
}
