package com.petconnectbe.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@Entity
@Data
@Table(name = "user_tb")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "type", nullable = false, length = 30)
    private String type;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "email", nullable = false, length = 254)
    private String email;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "birth_fundation_date", nullable = false)
    private LocalDate birthOrFoundationDate;

    @Column(name = "cpf_cnpj", nullable = false, length = 18)
    private String cpfOrCnpj;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adress_id")
    private Address address;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    // Métodos da interface UserDetails (obrigatorios no Spring Security)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // Por enquanto, não vamos usar papéis (roles), então retornamos null.
    }

    @Override
    public String getUsername() {
        return email; // O Spring Security vai usar o e-mail como nome de usuário.
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // A conta não expira.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // A conta não está bloqueada.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // As credenciais não expiram.
    }

    @Override
    public boolean isEnabled() {
        return true; // A conta está habilitada.
    }
}