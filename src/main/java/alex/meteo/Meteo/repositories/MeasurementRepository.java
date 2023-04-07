package alex.meteo.Meteo.repositories;

import alex.meteo.Meteo.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {
    Integer countMeasurementsByIsRainingIs(boolean isRainy);
}
