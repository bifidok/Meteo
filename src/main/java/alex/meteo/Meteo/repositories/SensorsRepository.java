package alex.meteo.Meteo.repositories;

import alex.meteo.Meteo.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorsRepository extends JpaRepository<Sensor,Integer> {
    Sensor findByName(String name);
}
