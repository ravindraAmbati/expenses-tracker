package learn.myapps.expensestracker.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class ApiRequestBody {
    @JsonProperty("searchEntity")
    private String searchEntity;

    @JsonProperty("searchAttributes")
    private List<String> searchAttributes;
}
