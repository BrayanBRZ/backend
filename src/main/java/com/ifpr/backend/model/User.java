package com.ifpr.backend.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nome é Obrigatório")
    @Size(min = 3, message = "Insira o nome completo")
    private String name;
    @NotBlank(message = "E-mail é obrigatório")
    private String email;
    @NotBlank(message = "Senha é obrigatório")
    private String password;
    @NotEmpty
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(value = AccessLevel.NONE)
    private List<UserProfile> userProfile = new ArrayList<>();

    public void setUserProfile(List<UserProfile> userProfile) {
        this.userProfile.clear();

        if (userProfile != null) {
            for (UserProfile u : userProfile) {
                u.setUser(this);
                this.userProfile.add(u);
            }
        }
    }
}
