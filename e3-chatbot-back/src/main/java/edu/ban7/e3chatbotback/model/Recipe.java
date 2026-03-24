package edu.ban7.e3chatbotback.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.ban7.e3chatbotback.view.AppUserView;
import edu.ban7.e3chatbotback.view.RecipeView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(RecipeView.class)
    private Integer id;

    @Column(nullable = false, unique = true)
    @JsonView(RecipeView.class)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    @JsonView(RecipeView.class)
    private String description;

    @ManyToOne(optional = false)
    @JsonView(RecipeView.class)
    private AppUser creator;

}
