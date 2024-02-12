package learn.myapps.expensestracker.api.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import learn.myapps.expensestracker.api.basic.BasicDetails;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "payment_mode_details", schema = "expenses_tracker")
public class PaymentModeDetails {

    @JsonProperty("id")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence_generator")
    @SequenceGenerator(name = "id_sequence_generator", sequenceName = "gen_id_sequence", allocationSize = 1)
    private Long id;

    @JsonProperty("paymentMode")
    @Column(name = "payment_mode")
    private String paymentMode;

    @JsonProperty("cardDetails")
    @Column(name = "card_details")
    private String cardDetails;

    @JsonProperty("cardType")
    @Column(name = "card_type")
    private String cardType;

    @JsonProperty("upiDetails")
    @Column(name = "upi_details")
    private String upiDetails;

    @JsonProperty("accountDetails")
    @Column(name = "account_details")
    private String accountDetails;

    @JsonProperty("basicDetails")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "basic_id", referencedColumnName = "basic_id")
    private BasicDetails basicDetails;
}
