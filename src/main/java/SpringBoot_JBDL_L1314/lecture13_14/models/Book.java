package SpringBoot_JBDL_L1314.lecture13_14.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Slf4j
//@Table(name = "my_book" , indexes = {@Index(name = "uniqueISBN" , columnList = "isbn"),
//                                        @Index(name = "uniqueName", columnList = "name")})
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    String isbn;

    int cost;

    @Enumerated(value = EnumType.STRING)
    BookStatus bookStatus;

    /**
     * JPA relations
     *  Unidirectional  keep things loosely coupled
     *  Bi directional--- keep things tightly coupled
     *
     */

    @ManyToOne
    @JoinColumn
    @JsonIgnore
//    @JsonIgnoreProperties(value = "bookList")
    private Author author1;

    @ManyToOne
    @JoinColumn
    @ToString.Exclude
    @JsonIgnore
    private UserInfo user;



    @OneToMany(mappedBy = "associatedBook")
    @JsonIgnore
    @ToString.Exclude
    List<Orders> ordersList;


    @CreationTimestamp
    LocalDateTime creationDate;

    @UpdateTimestamp
    LocalDateTime modifiedDate;


    /**
     * @Audited which listens to events that are being generate when update happens to an entity
     * -- @Prepersist
     * {@link  PreUpdate}
     *
     */
    @PrePersist
    public void prePersistCheck(){
        log.info("--------> pre persist check {} ", this.bookStatus);
        if(Objects.isNull(this.bookStatus)){
            this.bookStatus = BookStatus.AVAILABLE;
        }
    }


}
