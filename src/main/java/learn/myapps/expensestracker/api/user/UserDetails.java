package learn.myapps.expensestracker.api.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import learn.myapps.expensestracker.api.basic.BasicDetails;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "user_details", schema = "expenses_tracker")
public class UserDetails {

    @JsonProperty("id")
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence_generator")
    @SequenceGenerator(name = "id_sequence_generator", sequenceName = "gen_id_sequence", allocationSize = 1)
    private Long id;

    @JsonProperty("firstName")
    @Column(name = "first_name")
    private String firstName;

    @JsonProperty("lastName")
    @Column(name = "last_name")
    private String lastName;

    @JsonProperty("emailId")
    @Column(name = "email_id")
    private String emailId;

    @JsonProperty("mobileNo")
    @Column(name = "mobile_no")
    private String mobileNo;

    @JsonProperty("basicDetails")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "basic_id", referencedColumnName = "basic_id")
    private BasicDetails basicDetails;
}
