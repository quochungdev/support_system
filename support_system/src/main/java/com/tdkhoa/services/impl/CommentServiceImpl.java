/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.services.impl;

import com.tdkhoa.pojo.Article;
import com.tdkhoa.pojo.Comment;
import com.tdkhoa.pojo.User;
import com.tdkhoa.repository.CommentRepository;
import com.tdkhoa.services.CommentService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Khoa Tran
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository cmtRepo;

    @Override
    public Comment addOrUpdateComment(Map<String, String> params, Article article, User user) {
        Date currentDate = new Date();
        int cmtId;
        Comment cmt;
        if(params.get("id") != null) {
            cmtId = Integer.parseInt(params.get("id"));
            cmt = this.cmtRepo.getConmmentById(cmtId);
        }
        else {
            cmt = new Comment();
            cmt.setDate(currentDate);
        }
        if(params.containsKey("content")) {
            cmt.setContent(params.get("content"));
        }
        if(params.containsKey("date")) {
            cmt.setDate(currentDate);
        }
        if(params.containsKey("articleId")) {
            cmt.setArticleId(article);
        }
        if(params.containsKey("userId")) {
            cmt.setUserId(user);
        }
//        Comment c = new Comment();
//        
//        c.setContent(params.get("content"));
//        c.setDate(currentDate);
//        c.setArticleId(article);
//        c.setUserId(user);
        
        this.cmtRepo.addOrUpdateComment(cmt);
        return cmt;
    }

    @Override
    public List<Comment> getCommentsByArticleId(int id) {
        return cmtRepo.getCommentsByArticleId(id);
    }

    @Override
    public Comment getCommentById(int id) {
        return cmtRepo.getConmmentById(id);
    }

    @Override
    public boolean deleteConment(int id) {
        return this.cmtRepo.deleteComment(id);
    }
    
}
