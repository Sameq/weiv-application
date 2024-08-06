package br.com.ifba.weiv.domain.entity;



import br.com.ifba.weiv.web.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;

@Entity
@Setter
@Getter
@Builder
@Table(name="Users")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Users  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "email",nullable = false, unique = true)
    private String email;

    @Column(name = "user_name",nullable = false, unique = true)
    private String user_name;

    @Column(name = "password",nullable = false)
    private String password;

}

