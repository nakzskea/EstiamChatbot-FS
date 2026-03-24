package edu.ban7.e3chatbotback.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.ban7.e3chatbotback.view.ProductView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ProductView.class)
    private Integer id;

    @Column(nullable = false, unique = true)
    @JsonView(ProductView.class)
    private String name;

}
