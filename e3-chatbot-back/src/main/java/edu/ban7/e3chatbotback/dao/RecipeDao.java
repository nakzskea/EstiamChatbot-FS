package edu.ban7.e3chatbotback.dao;

import edu.ban7.e3chatbotback.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeDao extends JpaRepository<Recipe, Integer> {
}
