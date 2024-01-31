package learn.myapps.expensestracker.api.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import learn.myapps.expensestracker.api.basic.BasicDetails;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@SuperBuilder(toBuilder = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "expenses_category_details", schema = "expenses_tracker")
public class ExpensesCategoryDetails extends BasicDetails {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_id_sequence")
    private Long id;
    @JsonProperty("expensesCategory")
    private String expensesCategory;
    @JsonProperty("alias")
    private List<String> alias;
}
