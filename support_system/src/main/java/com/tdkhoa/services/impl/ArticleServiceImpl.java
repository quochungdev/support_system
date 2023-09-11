/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tdkhoa.pojo.Article;
import com.tdkhoa.pojo.Category;
import com.tdkhoa.pojo.Faculty;
import com.tdkhoa.pojo.User;
import com.tdkhoa.services.ArticleService;
import com.tdkhoa.repository.ArticleRepository;
import com.tdkhoa.repository.UserRepository;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Khoa Tran
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Article addArticle(Map<String, String> params, MultipartFile thumbnail, User user, Faculty faculty, Category cate) {
        Date currentDate = new Date();
        int articleId;
        Article article;
        if(params.get("id") != null) {
            articleId = Integer.parseInt(params.get("id"));
            article = this.articleRepository.getArticleById(articleId);
        }
        else {
            article = new Article();
            article.setDate(currentDate);
        }
        if(params.containsKey("title")) {
            article.setTitle(params.get("title"));
        }
        if(params.containsKey("content")) {
            article.setContent(params.get("content"));
        }
        if(params.containsKey("date")) {
            article.setDate(currentDate);
        }
        if(params.containsKey("user_id")) {
            article.setUserId(user);
        }
        if(params.containsKey("facultyId")) {
            article.setFacultyId(faculty);
        }
        if(params.containsKey("cateId")) {
            article.setCategoryId(cate);
        }
        if (!thumbnail.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(thumbnail.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                article.setThumbnail(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        articleRepository.addArticle(article);
        return article;
    }

    @Override
    public List<Article> getArticles() {
        return this.articleRepository.getArticles();
    }

    @Override
    public Article getArticleById(int id) {
        return this.articleRepository.getArticleById(id);
    }

    @Override
    public boolean deleteArticle(int id) {
        return this.articleRepository.deleteArticle(id);
    }

    @Override
    public List<Article> getArticlesByCateId(int id) {
        return this.articleRepository.getArticlesByCateId(id);
    }

    @Override
    public List<Article> getArticlesByFacultyId(int id) {
        return this.articleRepository.getArticlesByFacultyId(id);
    }
}
