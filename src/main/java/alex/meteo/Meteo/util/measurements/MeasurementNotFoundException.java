package alex.meteo.Meteo.util.measurements;

public class MeasurementNotFoundException extends RuntimeException{
    public MeasurementNotFoundException(String message) {
        super(message);
    }
}
