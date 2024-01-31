package learn.myapps.expensestracker.api.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import learn.myapps.expensestracker.api.basic.BasicDetails;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@SuperBuilder(toBuilder = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "currency_details", schema = "expenses_tracker")
public class CurrencyDetails extends BasicDetails {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_id_sequence")
    private Long id;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("exchangeRate")
    private BigDecimal exchangeRate;
    @JsonProperty("defaultCurrency")
    private String defaultCurrency;
}
