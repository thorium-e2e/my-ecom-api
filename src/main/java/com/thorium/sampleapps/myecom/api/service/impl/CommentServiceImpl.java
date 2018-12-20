package com.thorium.sampleapps.myecom.api.service.impl;

import com.thorium.sampleapps.myecom.api.domain.Comment;
import com.thorium.sampleapps.myecom.api.repository.CommentRepository;
import com.thorium.sampleapps.myecom.api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository repository;

    @Override
    public Comment saveComment(Comment c) {
        return repository.save(c);
    }

    @Override
    public Comment findById(String id) {
        return repository.findOne(id);
    }

    @Override
    public void deleteById(String id) {
        repository.delete(id);
    }

    @Override
    public void updateComment(Comment c) {
        repository.save(c);
    }

    @Override
    public boolean commentExists(Comment c) {
        return repository.exists(Example.of(c));
    }

    @Override
    public List<Comment> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
