package com.thorium.sampleapps.myecom.api.domain;

import com.thorium.sampleapps.myecom.api.domain.enums.CommentType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

import java.util.Objects;

@Document
public class Comment extends ResourceSupport {

    @Id
    private String commentId;
    private CommentType commentType;
    private String content;
    @DBRef
    private User editor;

    public Comment() {
    }

    public Comment(String idCommentaire, CommentType typeCommentaire, String contenu, User redacteur) {
        this.commentId = idCommentaire;
        this.commentType = typeCommentaire;
        this.content = contenu;
        this.editor = redacteur;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public CommentType getCommentType() {
        return commentType;
    }

    public void setCommentType(CommentType commentType) {
        this.commentType = commentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getEditor() {
        return editor;
    }

    public void setEditor(User editor) {
        this.editor = editor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment that = (Comment) o;
        return Objects.equals(getCommentId(), that.getCommentId()) &&
                getCommentType() == that.getCommentType() &&
                Objects.equals(getContent(), that.getContent()) &&
                Objects.equals(getEditor(), that.getEditor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommentId(), getCommentType(), getContent(), getEditor());
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId='" + commentId + '\'' +
                ", commentType=" + commentType +
                ", content='" + content + '\'' +
                ", editor=" + editor +
                '}';
    }
}
