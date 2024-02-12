package learn.myapps.expensestracker.api.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "basic_details", schema = "expenses_tracker")
public class BasicDetails {

    @JsonProperty("basicId")
    @Column(name = "basic_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence_generator")
    @SequenceGenerator(name = "id_sequence_generator", sequenceName = "gen_id_sequence", allocationSize = 1)
    private Long basicId;
    @JsonProperty("description")
    @Column(name = "description")
    private String description;
    @JsonProperty("isDeleted")
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @JsonProperty("lastUpdatedBy")
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;
    @JsonProperty("lastUpdatedDateAndTime")
    @Column(name = "last_updated_date_and_time")
    private LocalDateTime lastUpdatedDateAndTime;
}
