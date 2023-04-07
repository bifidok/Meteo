package alex.meteo.Meteo.validation;

import alex.meteo.Meteo.dto.MeasurementDTO;
import alex.meteo.Meteo.models.Sensor;
import alex.meteo.Meteo.services.impl.SensorsServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidator implements Validator {
    private SensorsServiceImpl sensorsServiceImpl;

    public MeasurementValidator(SensorsServiceImpl sensorsServiceImpl) {
        this.sensorsServiceImpl = sensorsServiceImpl;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MeasurementDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;
        if(measurementDTO.getSensor() == null) {
            errors.rejectValue("sensor","","Sensor cant be null");
            return;
        }
        Sensor sensor = sensorsServiceImpl.getSensorByName(measurementDTO.getSensor().getName());
        if(sensor == null) errors.rejectValue("sensor","","Sensor not found");
    }
}
