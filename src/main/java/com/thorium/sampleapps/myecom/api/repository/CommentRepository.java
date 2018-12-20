package com.thorium.sampleapps.myecom.api.repository;

import com.thorium.sampleapps.myecom.api.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
