package com.heroku.kubajabkodemoapp.controllers;

import com.heroku.kubajabkodemoapp.model.Comment;
import com.heroku.kubajabkodemoapp.model.User;
import com.heroku.kubajabkodemoapp.persistence.CommentService;
import com.heroku.kubajabkodemoapp.persistence.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
@RequestMapping("/api/comments")
public class CommentRestController {

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getComments() {

        Collection<Comment> comments = commentService.getAll();
        return new ResponseEntity<Collection<Comment>>(comments, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> registerComment(@RequestBody Comment comment) {

        Comment foundComment = commentService.findById(comment.getId());
        if (foundComment != null) {
            return new ResponseEntity("Unable to create. A comment with Id " + comment.getId() + " already exist.", HttpStatus.CONFLICT);
        }
        commentService.createComment(comment);
        return new ResponseEntity<Comment>(comment, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}/likes", method = RequestMethod.GET)
    public ResponseEntity<?> getMeetingParticipants(@PathVariable("id") long id) {
        Comment comment = commentService.findById(id);
        Collection<User> likes = comment.getLikes();
        if (likes == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<User>>(likes, HttpStatus.OK);
    }

    @RequestMapping(value = "/{commentId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeComment(@PathVariable long commentId) {
        Comment comment = commentService.findById(commentId);
        if (comment == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        commentService.removeComment(comment);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{commentId}/likes", method = RequestMethod.POST)
    public ResponseEntity<?> addLike(@PathVariable long commentId) {
        Comment comment = commentService.findById(commentId);
        if (comment == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = commentService.addLike(username, comment);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{commentId}/likes/unlike", method = RequestMethod.DELETE)
    public ResponseEntity<?> unlike(@PathVariable long commentId) {
        Comment comment = commentService.findById(commentId);
        if (comment == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        commentService.removeLike(username, comment);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
