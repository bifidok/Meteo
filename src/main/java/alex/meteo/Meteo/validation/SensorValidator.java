package alex.meteo.Meteo.validation;

import alex.meteo.Meteo.dto.SensorDTO;
import alex.meteo.Meteo.models.Sensor;
import alex.meteo.Meteo.services.impl.SensorsServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {
    private final SensorsServiceImpl sensorsServiceImpl;

    public SensorValidator(SensorsServiceImpl sensorsServiceImpl) {
        this.sensorsServiceImpl = sensorsServiceImpl;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) target;
        Sensor sensor = sensorsServiceImpl.getSensorByName(sensorDTO.getName());
        if(sensor != null){
            errors.rejectValue("name","","Sensor name should be unique");
        }
    }
}
