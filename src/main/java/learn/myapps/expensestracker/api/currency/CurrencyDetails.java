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
@Table(name = "currency_details", schema = "expenses_tracker")
public class CurrencyDetails {

    @JsonProperty("id")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence_generator")
    @SequenceGenerator(name = "id_sequence_generator", sequenceName = "gen_id_sequence", allocationSize = 1)
    private Long id;

    @JsonProperty("currency")
    @Column(name = "currency")
    private String currency;

    @JsonProperty("exchangeRate")
    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;

    @JsonProperty("defaultCurrency")
    @Column(name = "default_currency")
    private String defaultCurrency;

    @JsonProperty("basicDetails")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "basic_id", referencedColumnName = "basic_id")
    private BasicDetails basicDetails;
}
