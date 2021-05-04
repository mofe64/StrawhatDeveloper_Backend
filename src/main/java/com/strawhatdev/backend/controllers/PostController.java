package com.strawhatdev.backend.controllers;

import com.strawhatdev.backend.dtos.APIResponse;
import com.strawhatdev.backend.dtos.PostDto;
import com.strawhatdev.backend.exceptions.CategoryException;
import com.strawhatdev.backend.exceptions.PostException;
import com.strawhatdev.backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/post/")
public class PostController {
    private final PostService postService;
    private final String INVALID_PAGE_NUMBER_MESSAGE = "Invalid Page Number";

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("all/{pageNumber}")
    public ResponseEntity<?> getAllPosts(@PathVariable String pageNumber) {
        try {
            int pageNo = Integer.parseInt(pageNumber);
            List<PostDto> posts = postService.getAllPosts(pageNo);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (NumberFormatException numberFormatException) {
            return new ResponseEntity<>(new APIResponse(false, INVALID_PAGE_NUMBER_MESSAGE), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("new")
    public ResponseEntity<?> createNewPost(@RequestBody PostDto postDto) {
        try {
            postService.createPost(postDto);
            return new ResponseEntity<>(new APIResponse(true, "Post Created successfully"), HttpStatus.CREATED);
        } catch (CategoryException e) {
            return new ResponseEntity<>(new APIResponse(false, "Invalid Category selected"), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("{postId}")
    public ResponseEntity<?> editPost(@PathVariable String postId, @RequestBody PostDto postDto) {
        try {
            postService.updatePost(postId, postDto);
            return new ResponseEntity<>(new APIResponse(true, "Post updated successfully"), HttpStatus.OK);
        } catch (PostException e) {
            return new ResponseEntity<>(new APIResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<?> deletePost(@PathVariable String postId) {
        try {
            postService.deletePost(postId);
            return new ResponseEntity<>(new APIResponse(true, "Post Deleted successfully"), HttpStatus.NO_CONTENT);
        } catch (PostException e) {
            return new ResponseEntity<>(new APIResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("all/{categoryName}/{pageNumber}")
    public ResponseEntity<?> getAllPostsInACategory(@PathVariable String categoryName, @PathVariable String pageNumber) {
        try {
            int pageNo = Integer.parseInt(pageNumber);
            List<PostDto> postsInCategory = postService.getAllPostsInACategory(categoryName, pageNo);
            return new ResponseEntity<>(postsInCategory, HttpStatus.OK);
        } catch (CategoryException e) {
            return new ResponseEntity<>(new APIResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (NumberFormatException numberFormatException) {
            return new ResponseEntity<>(new APIResponse(false, INVALID_PAGE_NUMBER_MESSAGE), HttpStatus.BAD_REQUEST);
        }
    }
}
