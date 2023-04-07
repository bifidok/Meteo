package alex.meteo.Meteo.util;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class ResponseError {
    @NonNull
    private String message;
    @NonNull
    private LocalDateTime timestamp;
}
