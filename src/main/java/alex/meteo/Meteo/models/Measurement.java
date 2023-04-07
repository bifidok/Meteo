package alex.meteo.Meteo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Measurement")
@Getter
@Setter
@NoArgsConstructor
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    @NotNull
    private Sensor sensor;
    @Column(name = "temperature")
    @NotNull(message = "Temperature cant be null")
    @Min(value = -100, message = "Temperature should be greater than -100")
    @Max(value = 100, message = "Temperature should be less than 100")
    private Double temperature;
    @NotNull(message = "Rain cant be null")
    @Column(name = "raining")
    private Boolean isRaining;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
