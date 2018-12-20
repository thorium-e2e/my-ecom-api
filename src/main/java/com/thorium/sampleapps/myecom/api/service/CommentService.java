package com.thorium.sampleapps.myecom.api.service;

import com.thorium.sampleapps.myecom.api.domain.Comment;

import java.util.List;

public interface CommentService {

    Comment saveComment(Comment c);

    Comment findById(String id);

    void deleteById(String id);

    void updateComment(Comment c);

    boolean commentExists(Comment c);

    List<Comment> findAll();

    void deleteAll();
}
