package com.strawhatdev.backend.services;

import com.strawhatdev.backend.dtos.PostDto;
import com.strawhatdev.backend.exceptions.PostException;
import com.strawhatdev.backend.models.Post;
import com.strawhatdev.backend.respository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto getPostById(String id) throws PostException {
        return PostDto.packDto(getAPostById(id));
    }

    private Post getAPostById(String id) throws PostException {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostException("No post found with that Id"));
    }

    @Override
    public PostDto getPostBySlug(String slug) throws PostException {
        return PostDto.packDto(getAPostBySlug(slug));
    }

    private Post getAPostBySlug(String slug) throws PostException {
        return postRepository.findPostBySlug(slug)
                .orElseThrow(() -> new PostException("No Post found with that Slug"));
    }

    @Override
    public void createPost(PostDto postDto) {

    }

    @Override
    public Slice<PostDto> getAllPosts(Pageable pageable) {
        return null;
    }

    @Override
    public Slice<PostDto> search(String searchPhrase, Pageable pageable) {
        return null;
    }

    @Override
    public void updatePost(String postId, PostDto postDto) {

    }

    @Override
    public void deletePost(String postId) {

    }

    @Override
    public Slice<PostDto> getAllPostsBefore(LocalDate date, Pageable pageable) {
        return null;
    }

    @Override
    public Slice<PostDto> getAllPostsAfter(LocalDate date, Pageable pageable) {
        return null;
    }

    @Override
    public Slice<PostDto> getAllPostsInACategory(String categoryName, Pageable pageable) {
        return null;
    }
}
