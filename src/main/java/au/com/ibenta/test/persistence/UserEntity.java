package au.com.ibenta.test.persistence;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@Data
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, name = "first_name")
    @NotBlank(message = "first_name should not be blank")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    @NotBlank(message = "last_name should not be blank")
    private String lastName;

    @Email
    @NotBlank(message = "email must not be blank")
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "password should not be blank")
    private String password;
}
