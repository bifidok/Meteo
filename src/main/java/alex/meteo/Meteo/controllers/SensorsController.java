package alex.meteo.Meteo.controllers;

import alex.meteo.Meteo.dto.SensorDTO;
import alex.meteo.Meteo.models.Sensor;
import alex.meteo.Meteo.services.SensorService;
import alex.meteo.Meteo.services.impl.SensorsServiceImpl;
import alex.meteo.Meteo.util.ResponseError;
import alex.meteo.Meteo.util.sensors.SensorNotCreatedException;
import alex.meteo.Meteo.util.sensors.SensorNotFoundException;
import alex.meteo.Meteo.validation.SensorValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorService sensorsService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    public SensorsController(SensorsServiceImpl sensorsServiceImpl, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorsService = sensorsServiceImpl;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }


    @Operation(description = "Find all sensors")
    @ApiResponse(responseCode = "200", description = "Sensors found")
    @GetMapping
    public List<SensorDTO> getSensors(){
        return sensorsService.getSensors().stream().map(this::convertSensortoDTO).collect(Collectors.toList());
    }
    @Operation(description = "Find one sensor")
    @ApiResponse(responseCode = "200", description = "Sensor found")
    @GetMapping("/{id}")
    public SensorDTO getSensor(@PathVariable("id") int id){
        return convertSensortoDTO(sensorsService.getSensor(id));
    }

    @Operation(description = "Create new sensor")
    @ApiResponse(responseCode = "200", description = "Sensors created")
    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registerSensor(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult){
        sensorValidator.validate(sensorDTO,bindingResult);
        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            StringBuilder message = new StringBuilder();
            for(FieldError error : errors){
                message.append(error.getField() + " - ").append(error.getDefaultMessage()).append(";");
            }
            throw new SensorNotCreatedException(message.toString());
        }
        sensorsService.createSensor(convertDTOtoSensor(sensorDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }
    private Sensor convertDTOtoSensor(SensorDTO dto){
        return modelMapper.map(dto,Sensor.class);
    }

    private SensorDTO convertSensortoDTO(Sensor sensor){
        return modelMapper.map(sensor,SensorDTO.class);
    }
    @ExceptionHandler
    private ResponseEntity<ResponseError> exceptionHandler(SensorNotCreatedException e){
        ResponseError responseError = new ResponseError(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(responseError,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<ResponseError> exceptionHandler(SensorNotFoundException e){
        ResponseError responseError = new ResponseError(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(responseError,HttpStatus.NOT_FOUND);
    }
}
