package edu.ban7.e3chatbotback.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.ban7.e3chatbotback.dao.RecipeDao;
import edu.ban7.e3chatbotback.model.AppUser;
import edu.ban7.e3chatbotback.model.Recipe;
import edu.ban7.e3chatbotback.security.IsAdmin;
import edu.ban7.e3chatbotback.security.IsUser;
import edu.ban7.e3chatbotback.view.RecipeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class RecipeController {

    @Autowired
    protected RecipeDao recipeDao;

    @GetMapping("/recipe/list")
    @JsonView(RecipeView.class)
    @IsUser
    public ResponseEntity<List<Recipe>> getAll() {

        return new ResponseEntity<>(recipeDao.findAll(), HttpStatus.OK);
        //return ResponseEntity.ok(recipeDao.findAll()); //identique
    }

    @GetMapping("/recipe/{id}")
    @JsonView(RecipeView.class)
    @IsUser
    public ResponseEntity<Recipe> get(@PathVariable int id) {

        Optional<Recipe> optionalRecipe = recipeDao.findById(id);

        //si l'id de l'utilisateur n'existe pas en BDD on retourne un code 404
        if(optionalRecipe.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //on retourne l'utilisateur
        return new ResponseEntity<>(optionalRecipe.get(), HttpStatus.OK);

        //equivalent expressJS : res.status(200).sendJson(recipe)

    }

    @DeleteMapping("/recipe/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {

        Optional<Recipe> optionalRecipe = recipeDao.findById(id);

        //si l'id de l'utilisateur n'existe pas en BDD on retourne un code 404
        if(optionalRecipe.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        recipeDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PostMapping("/recipe")
    @JsonView(RecipeView.class)
    @IsAdmin
    public ResponseEntity<Recipe> create(@RequestBody Recipe recipe) {

        //TODO : systeme d'authentification (en attendant on affecte le premier utilisateur)
        AppUser fauxUtilisateur = new AppUser();
        fauxUtilisateur.setId(1);
        recipe.setCreator(fauxUtilisateur);

        recipeDao.save(recipe);

        return new ResponseEntity<>(recipe, HttpStatus.CREATED);
    }

    @PutMapping("/recipe/{id}")
    @IsAdmin
    public ResponseEntity<Void> update(
            @PathVariable int id,
            @RequestBody Recipe recipeToBeUpdated) {

        //on force l'id à être le meme que celui de l'URL
        recipeToBeUpdated.setId(id);

        //on recupere l'enregistrement actuel en base de données
        Optional<Recipe> optionalRecipeDb = recipeDao.findById(id);

        //si la recette n'existe pas
        if(optionalRecipeDb.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //on empêche la modification du créateur
        recipeToBeUpdated.setCreator(optionalRecipeDb.get().getCreator());

        recipeDao.save(recipeToBeUpdated);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
