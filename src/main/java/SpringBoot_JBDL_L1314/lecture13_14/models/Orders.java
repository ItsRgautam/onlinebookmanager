package SpringBoot_JBDL_L1314.lecture13_14.models;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    /**
     * whenever you are trying to update the same entity version would increment
     *
     * --> update order set transactionType= ? and version++ where id = ? and version = ?
     *
     *  This helps in locking
     *      (Optimistic lock and pessimistic lock)
     *
     */
    @Version
    long version;

    /**
     *
     *  order entity
     *
     */
    String orderReference;

    double amount;

    int fine;

    LocalDate orderDate;


    LocalDate dueDate;

    @Enumerated(value = EnumType.STRING)
    SubmissionStatus submitStatus;

    @Enumerated(value = EnumType.STRING)
    OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    UserInfo user;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    Book associatedBook;

}