package com.thorium.sampleapps.myecom.api.controller;

import com.thorium.sampleapps.myecom.api.domain.Comment;
import com.thorium.sampleapps.myecom.api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public HttpEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.findAll();
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            comments.forEach(comment -> comment.add(linkTo(methodOn(CommentController.class).getAllComments()).withRel("comments")));
            comments.forEach(comment -> comment.add(linkTo(methodOn(CommentController.class).getCommentById(comment.getCommentId())).withSelfRel()));
            return new ResponseEntity<>(comments, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<Comment> getCommentById(@PathVariable("id") String commentId) {
        Comment existingComment = commentService.findById(commentId);
        if (existingComment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            existingComment.add(linkTo(methodOn(CommentController.class).getCommentById(existingComment.getCommentId())).withSelfRel());
            return new ResponseEntity<>(existingComment, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public HttpEntity<?> saveComment(@RequestBody Comment comment) {
        if (commentService.commentExists(comment)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            Comment newComment = commentService.saveComment(comment);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/comments/{id}")
                    .buildAndExpand(newComment.getCommentId()).toUri();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(location);
            return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public HttpEntity<?> updateComment(@PathVariable("id") String id, @RequestBody Comment comment) {
        Comment existingComment = commentService.findById(id);
        if(existingComment == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            existingComment.setCommentType(comment.getCommentType());
            existingComment.setContent(comment.getContent());
            existingComment.setEditor(comment.getEditor());
            commentService.updateComment(existingComment);
            existingComment.add(linkTo(methodOn(CommentController.class).getCommentById(existingComment.getCommentId())).withSelfRel());
            return new ResponseEntity<>(existingComment, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteComment(@PathVariable("id") String commentId) {
        commentService.deleteById(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAll() {
        commentService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
