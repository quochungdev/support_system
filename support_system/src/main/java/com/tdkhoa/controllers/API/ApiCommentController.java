/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.controllers.API;

import com.tdkhoa.pojo.Article;
import com.tdkhoa.pojo.Category;
import com.tdkhoa.pojo.Comment;
import com.tdkhoa.pojo.Faculty;
import com.tdkhoa.pojo.User;
import com.tdkhoa.services.ArticleService;
import com.tdkhoa.services.CommentService;
import com.tdkhoa.services.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Khoa Tran
 */
@RestController
@RequestMapping("/api")
public class ApiCommentController {
    @Autowired
    private CommentService cmtService;
    @Autowired
    private UserService uServ;
    @Autowired
    private ArticleService articleServ;
    
    @GetMapping("/comments/article/{article_id}/")
    @CrossOrigin
    public ResponseEntity<List<Comment>> getCommentsOfArticle(@PathVariable(value = "article_id") int article_id) {
        return new ResponseEntity<>(this.cmtService.getCommentsByArticleId(article_id), HttpStatus.OK);
    }
    
    @PostMapping("/view_article/{article_id}/comment_article/")
    @CrossOrigin
    public ResponseEntity<Comment> add(@RequestParam Map<String, String> params, @PathVariable(value = "article_id") int articleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = this.uServ.findUserByUsername(userDetails.getUsername());
        
        Article article = this.articleServ.getArticleById(articleId);
        
        Comment comment = this.cmtService.addOrUpdateComment(params, article, currentUser);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/delete_comment/{comment_id}")
    @CrossOrigin
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "comment_id") int comment_id) {
        System.out.println("Xoa");
        this.cmtService.deleteConment(comment_id);
    }
}
