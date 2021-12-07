package paymentsservice.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import com.angel.models.states.PaymentState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment {



    @Id
    @Column(name = "Id", unique = true, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "State")
    @Enumerated(value = EnumType.STRING)
    private PaymentState state;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private User userId;

    @Column(name = "amount")
    private double amount;

    public Payment(){}

    public Payment(PaymentState state, double amount){
        this.state = state;
        this.amount = amount;
    }

}
