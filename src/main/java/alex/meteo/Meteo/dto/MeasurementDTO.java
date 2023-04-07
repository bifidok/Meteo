package alex.meteo.Meteo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Schema(name = "Measurement", description = "Measurement dto")
public class MeasurementDTO {

    @NotNull
    @Schema(description = "Measurement sensor")
    private SensorDTO sensor;
    @NotNull(message = "Temperature cant be null")
    @Min(value = -100,message = "Temperature should be greater than -100")
    @Max(value = 100,message = "Temperature should be less than 100")
    @Schema(description = "Temperature")
    private Double temperature;
    @NotNull(message = "Rain cant be null")
    @Schema(description = "Is raining")
    private Boolean isRaining;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementDTO that = (MeasurementDTO) o;
        return Objects.equals(sensor, that.sensor) && Objects.equals(temperature, that.temperature) && Objects.equals(isRaining, that.isRaining);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensor, temperature, isRaining);
    }
}
