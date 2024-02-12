package learn.myapps.expensestracker.api.expenses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import learn.myapps.expensestracker.api.basic.BasicDetails;
import learn.myapps.expensestracker.api.category.ExpensesCategoryDetails;
import learn.myapps.expensestracker.api.currency.CurrencyDetails;
import learn.myapps.expensestracker.api.payment.PaymentModeDetails;
import learn.myapps.expensestracker.api.user.UserDetails;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "expenses_details", schema = "expenses_tracker")
public class ExpensesDetails {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence_generator")
    @SequenceGenerator(name = "id_sequence_generator", sequenceName = "gen_id_sequence", allocationSize = 1)
    private Long id;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("paidBy")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private UserDetails paidBy;

    @JsonProperty("paidTo")
    private String paidTo;

    @JsonProperty("dateAndTime")
    private LocalDateTime dateAndTime;

    @JsonProperty("paymentMode")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private PaymentModeDetails paymentMode;

    @JsonProperty("category")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private ExpensesCategoryDetails category;

    @JsonProperty("currency")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private CurrencyDetails currency;

    @JsonProperty("basicDetails")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "basic_id", referencedColumnName = "basic_id")
    private BasicDetails basicDetails;
}
