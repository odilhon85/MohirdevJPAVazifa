package uz.devior.mohirdev_jpa_vazifa.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationResponse<T> {

    private String message;
    private boolean success;
    private Map<String, T> data;

    public ApplicationResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
