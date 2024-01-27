package learn.myapps.expensestracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@SuperBuilder(toBuilder = true)
public class BasicDetails {

    @JsonProperty("basicId")
    private Long basicId;
    @JsonProperty("description")
    private String description;
    @JsonProperty("isDeleted")
    private boolean isDeleted;
    @JsonProperty("lastUpdatedBy")
    private String lastUpdatedBy;
    @JsonProperty("lastUpdatedDateAndTime")
    private LocalDateTime lastUpdatedDateAndTime;
}
