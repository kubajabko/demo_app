package com.heroku.kubajabkodemoapp.controllers;

import com.heroku.kubajabkodemoapp.model.User;
import com.heroku.kubajabkodemoapp.persistence.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    UserService userService;
    public void setUserService(UserService userService){
        this.userService=userService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        Collection<User> users = userService.getAll();
        return new ResponseEntity<Collection<User>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable("id") String login) {
        User user = userService.findByLogin(login);
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody User user) {
        if (userService.findByLogin(user.getLogin()) != null) {
            return new ResponseEntity(
                    "Unable to create. A participant with login " + user.getLogin() + " already exist.",
                    HttpStatus.CONFLICT);
        }
        userService.add(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") String login) {
        User user = userService.findByLogin(login);
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        userService.delete(user);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("id") String login,
                                    @RequestBody User updatedUser) {
        if (userService.findByLogin(login) != null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        updatedUser.setLogin(login); // in case of login!=updatedParticipant.getLogin()
        userService.update(updatedUser);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

}
