package alex.meteo.Meteo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="Sensor")
@Getter
@Setter
@NoArgsConstructor
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    @NotNull(message = "Name cant be null")
    @Size(min=3,max = 30,message = "Name should be between 3 and 30 characters")
    private String name;
    @OneToMany(mappedBy = "sensor")
    private List<Measurement> measurements;

}
