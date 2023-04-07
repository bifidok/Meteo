package alex.meteo.Meteo.controllers;

import alex.meteo.Meteo.dto.MeasurementDTO;
import alex.meteo.Meteo.models.Measurement;
import alex.meteo.Meteo.services.impl.MeasurementServiceImpl;
import alex.meteo.Meteo.util.MeasurementsResponse;
import alex.meteo.Meteo.validation.MeasurementValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class MeasurementControllerTest {
    @InjectMocks
    private MeasurementController measurementController;
    @Mock
    private MeasurementServiceImpl measurementServiceImpl;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private MeasurementValidator measurementValidator;

    @Test
    void getMeasurements_shouldConvertAndReturnMeasurementResponse() {
        List<Measurement> measurements = getMeasurements();
        List<MeasurementDTO> measurementDTOS = new ArrayList<>();
        for (Measurement m : measurements) {
            measurementDTOS.add(getMeasurementDTO(m));
            Mockito.when(modelMapper.map(m,MeasurementDTO.class)).thenReturn(getMeasurementDTO(m));
        }
        MeasurementsResponse measurementsResponse = new MeasurementsResponse(measurementDTOS);

        Mockito.when(measurementServiceImpl.getMeasurements()).thenReturn(measurements);
        MeasurementsResponse measurementsResponse1 = measurementController.getMeasurements();
        Assertions.assertNotNull(measurementsResponse1);
        Assertions.assertTrue(measurementsResponse.getMeasurementDTOS()
                .containsAll(measurementsResponse1.getMeasurementDTOS()));

    }

    @Test
    void getMeasurement_shouldConvertAndFindMeasurement() {
        Measurement measurement = getMeasurement();
        Mockito.when(measurementServiceImpl.getMeasurement(measurement.getId())).thenReturn(measurement);
        Mockito.when(modelMapper.map(measurement, MeasurementDTO.class)).thenReturn(getMeasurementDTO(measurement));
        MeasurementDTO measurementDTO = measurementController.getMeasurement(measurement.getId());

        Assertions.assertNotNull(measurementDTO);
        Assertions.assertEquals(measurement.getSensor(), measurementDTO.getSensor());
        Assertions.assertEquals(measurement.getTemperature(), measurementDTO.getTemperature());
        Assertions.assertEquals(measurement.getIsRaining(), measurementDTO.getIsRaining());
    }


    @Test
    void addMeasurement_shouldCreateMeasurement() {
        BindingResult bindingResult = mock(BindingResult.class);
        Measurement measurement = getMeasurement();
        MeasurementDTO measurementDTO = getMeasurementDTO(measurement);
        Mockito.when(modelMapper.map(measurementDTO, Measurement.class)).thenReturn(measurement);
        measurementController.addMeasurement(measurementDTO,bindingResult);

        Mockito.verify(measurementServiceImpl,Mockito.times(1)).createMeasurement(measurement);
    }

    private Measurement getMeasurement() {
        Measurement measurement = new Measurement();
        measurement.setSensor(null);
        measurement.setId(1);
        measurement.setTemperature(22d);
        measurement.setIsRaining(true);
        measurement.setCreatedAt(LocalDateTime.now());
        return measurement;
    }

    private MeasurementDTO getMeasurementDTO(Measurement measurement) {
        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setIsRaining(measurement.getIsRaining());
        measurementDTO.setTemperature(measurement.getTemperature());
        measurementDTO.setSensor(null);
        return measurementDTO;
    }

    private List<Measurement> getMeasurements() {
        Measurement measurement1 = new Measurement();
        measurement1.setSensor(null);
        measurement1.setId(1);
        measurement1.setTemperature(22d);
        measurement1.setIsRaining(true);
        measurement1.setCreatedAt(LocalDateTime.now());

        Measurement measurement2 = new Measurement();
        measurement2.setSensor(null);
        measurement2.setId(2);
        measurement2.setTemperature(31d);
        measurement2.setIsRaining(true);
        measurement2.setCreatedAt(LocalDateTime.now());

        Measurement measurement3 = new Measurement();
        measurement3.setSensor(null);
        measurement3.setId(3);
        measurement3.setTemperature(55d);
        measurement3.setIsRaining(false);
        measurement3.setCreatedAt(LocalDateTime.now());
        return List.of(measurement1, measurement2, measurement3);
    }
}
