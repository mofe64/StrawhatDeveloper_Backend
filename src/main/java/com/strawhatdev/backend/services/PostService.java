package com.strawhatdev.backend.services;

import com.strawhatdev.backend.dtos.PostDto;
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
    void createPost(PostDto postDto);
    Slice<PostDto> getAllPosts(Pageable pageable);
    Slice<PostDto> search(String searchPhrase, Pageable pageable);
    void updatePost(String postId, PostDto postDto);
    void deletePost(String postId);
    Slice<PostDto> getAllPostsBefore(LocalDate date, Pageable pageable);
    Slice<PostDto> getAllPostsAfter(LocalDate date, Pageable pageable);
    Slice<PostDto> getAllPostsInACategory(String categoryName, Pageable pageable);


}
