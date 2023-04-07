package alex.meteo.Meteo.controllers;

import alex.meteo.Meteo.dto.MeasurementDTO;
import alex.meteo.Meteo.services.MeasurementService;
import alex.meteo.Meteo.util.MeasurementsResponse;
import alex.meteo.Meteo.models.Measurement;
import alex.meteo.Meteo.services.impl.MeasurementServiceImpl;
import alex.meteo.Meteo.util.ResponseError;
import alex.meteo.Meteo.util.measurements.MeasurementNotCreatedException;
import alex.meteo.Meteo.util.measurements.MeasurementNotFoundException;
import alex.meteo.Meteo.validation.MeasurementValidator;
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
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;

    public MeasurementController(MeasurementServiceImpl measurementServiceImpl, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
        this.measurementService = measurementServiceImpl;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }

    @Operation(description = "Find all measurements")
    @ApiResponse(responseCode = "200", description = "Measurements found")
    @GetMapping
    public MeasurementsResponse getMeasurements(){
        return new MeasurementsResponse(measurementService.getMeasurements().stream()
                .map(this::convertMeasurementToDTO).collect(Collectors.toList()));
    }
    @Operation(description = "Find one measurement")
    @ApiResponse(responseCode = "200", description = "Measurement found")
    @GetMapping("/{id}")
    public MeasurementDTO getMeasurement(@PathVariable("id") int id){
        MeasurementDTO measurementDTO = convertMeasurementToDTO(measurementService.getMeasurement(id));
        return measurementDTO;
    }
    @Operation(description = "Count rainy days")
    @GetMapping("/rainyDaysCount")
    public Integer rainyDays(){
        return measurementService.getRainyDays();
    }

    @Operation(description = "Add new measurement")
    @ApiResponse(responseCode = "200", description = "Measurement added")
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult){
        measurementValidator.validate(measurementDTO,bindingResult);
        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            StringBuilder message = new StringBuilder();

            for(FieldError error : errors){
                message.append(error.getField() + " - ").append(error.getDefaultMessage()).append(";");
            }
            throw new MeasurementNotCreatedException(message.toString());
        }
        measurementService.createMeasurement(convertDTOtoMeasurement(measurementDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private MeasurementDTO convertMeasurementToDTO(Measurement measurement){
        return modelMapper.map(measurement,MeasurementDTO.class);
    }
    private Measurement convertDTOtoMeasurement(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO,Measurement.class);
    }
    @ExceptionHandler
    private ResponseEntity<ResponseError> exceptionHandler(MeasurementNotCreatedException e){
        ResponseError responseError = new ResponseError(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(responseError,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<ResponseError> exceptionHandler(MeasurementNotFoundException e){
        ResponseError responseError = new ResponseError(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(responseError,HttpStatus.NOT_FOUND);
    }
}
