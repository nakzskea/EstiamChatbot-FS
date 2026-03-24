package edu.ban7.e3chatbotback.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.ban7.e3chatbotback.view.AppUserView;
import edu.ban7.e3chatbotback.view.RecipeView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AppUser {

    public interface OnUpdate {}
    public interface OnCreate {}
    public interface OnLogin {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(AppUserView.class)
    private Integer id;

    @Column(nullable = false, unique = true)
    @Email(groups = {OnCreate.class, OnUpdate.class, OnLogin.class})
    @NotBlank(groups = {OnCreate.class, OnUpdate.class, OnLogin.class})
    private String email;

    @Column(nullable = false)
    @NotBlank(groups = {OnCreate.class, OnLogin.class})
    private String password;

    @Column(nullable = false)
    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    @JsonView({AppUserView.class , RecipeView.class})
    private String pseudo;

    private boolean admin = false;
}
