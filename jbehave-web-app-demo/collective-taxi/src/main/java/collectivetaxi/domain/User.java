package collectivetaxi.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_ID_SEQ")
    @SequenceGenerator(name = "USER_ID_SEQ", sequenceName = "USER_ID_SEQ", allocationSize = 100)
    private Integer userId;

    @Column(unique = true)
    private String userName;

    private String password;

    private String firstName;

    private String lastName;

    private Date creationDate;

    private Date modificationDate;

}
