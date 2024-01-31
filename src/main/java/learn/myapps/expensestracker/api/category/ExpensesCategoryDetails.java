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
@Table(name = "expenses_category_details", schema = "expenses_tracker")
public class ExpensesCategoryDetails {

    @JsonProperty("id")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence_generator")
    @SequenceGenerator(name = "id_sequence_generator", sequenceName = "gen_id_sequence", allocationSize = 1)
    private Long id;

    @JsonProperty("expensesCategory")
    @Column(name = "expenses_category")
    private String expensesCategory;

    @JsonProperty("alias")
    @Column(name = "alias")
    private List<String> alias;

    @JsonProperty("basicDetails")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "basic_id", referencedColumnName = "basic_id")
    private BasicDetails basicDetails;
}