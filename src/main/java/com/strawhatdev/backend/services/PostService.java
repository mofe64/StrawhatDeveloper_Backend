package com.strawhatdev.backend.services;

import com.strawhatdev.backend.dtos.PostDto;
import com.strawhatdev.backend.exceptions.CategoryException;
import com.strawhatdev.backend.exceptions.PostException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface PostService {
    PostDto getPostById(String id) throws PostException;
    PostDto getPostBySlug(String slug) throws PostException;
    PostDto findPostByTitle(String title) throws PostException;
    void createPost(PostDto postDto) throws CategoryException;
    List<PostDto> getAllPosts(int PageNumber);
    List<PostDto> search(String searchPhrase, int pageNumber);
    void updatePost(String postId, PostDto postDto) throws PostException;
    void deletePost(String postId) throws PostException;
    List<PostDto> getAllPostsBefore(LocalDate date, int pageNumber);
    List<PostDto> getAllPostsAfter(LocalDate date, int pageNumber);
    List<PostDto> getAllPostsInACategory(String categoryName, int pageNumber) throws CategoryException;


}
