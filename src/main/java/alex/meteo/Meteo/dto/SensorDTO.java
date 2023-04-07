package alex.meteo.Meteo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "Sensor", description = "Sensor dto")
public class SensorDTO {
    @NotNull(message = "Name cant be null")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters")
    @Schema(description = "Name")
    private String name;
}
