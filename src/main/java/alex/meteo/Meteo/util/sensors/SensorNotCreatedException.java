package alex.meteo.Meteo.util.sensors;

public class SensorNotCreatedException extends  RuntimeException{
    public SensorNotCreatedException(String message) {
        super(message);
    }
}
