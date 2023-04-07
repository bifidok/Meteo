package alex.meteo.Meteo.util;

import alex.meteo.Meteo.dto.MeasurementDTO;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class MeasurementsResponse {
    @NonNull
    private List<MeasurementDTO> measurementDTOS;

}
