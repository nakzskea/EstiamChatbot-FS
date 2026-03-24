package edu.ban7.e3chatbotback.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.ban7.e3chatbotback.dao.AppUserDao;
import edu.ban7.e3chatbotback.model.AppUser;
import edu.ban7.e3chatbotback.view.AppUserView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class AppUserController {

    @Autowired
    protected AppUserDao appUserDao;

    @GetMapping("/user/list")
    @JsonView(AppUserView.class)
    public List<AppUser> getAll() {

        return appUserDao.findAll();

    }

    @GetMapping("/user/{id}")
    @JsonView(AppUserView.class)
    public AppUser get(@PathVariable int id) {

        Optional<AppUser> optionalAppUser = appUserDao.findById(id);

        //si l'id de l'utilisateur n'existe pas en BDD
        if(optionalAppUser.isEmpty()) {
            return null;
        }

        //on retourne l'utilisateur
        return optionalAppUser.get();

    }

    @DeleteMapping("/user/{id}")
    public void delete(@PathVariable int id) {
        appUserDao.deleteById(id);
    }

    @PutMapping("/user/{id}")
    public void update(
            @PathVariable int id,
            @RequestBody @Validated(AppUser.OnUpdate.class) AppUser appUserToBeUpdated) {

        //on force l'id à être le meme que celui de l'URL
        appUserToBeUpdated.setId(id);

        //on recupere l'enregistrement actuel en base de données
        Optional<AppUser> optionalAppUserDb = appUserDao.findById(id);

        //si l'utilisateur n'existe pas
        if(optionalAppUserDb.isEmpty()) {
            return;
        }

        //on empeche la mise à jour du mot de passe
        appUserToBeUpdated.setPassword(optionalAppUserDb.get().getPassword());

        appUserDao.save(appUserToBeUpdated);
    }










}
