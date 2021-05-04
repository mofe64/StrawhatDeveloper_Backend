package com.strawhatdev.backend.services;

import com.strawhatdev.backend.dtos.PostDto;
import com.strawhatdev.backend.exceptions.CategoryException;
import com.strawhatdev.backend.exceptions.PostException;
import com.strawhatdev.backend.models.Category;
import com.strawhatdev.backend.models.Post;
import com.strawhatdev.backend.respository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final int pageSize = 12;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, CategoryService categoryService) {
        this.postRepository = postRepository;
        this.categoryService = categoryService;

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

    @Override
    public PostDto findPostByTitle(String title) throws PostException {
        return PostDto.packDto(findAPostByTitle(title));
    }

    private Post findAPostByTitle(String title) throws PostException {
        return postRepository.findPostByTitle(title)
                .orElseThrow(() -> new PostException("No Post found with that title"));
    }

    private Post getAPostBySlug(String slug) throws PostException {
        return postRepository.findPostBySlug(slug)
                .orElseThrow(() -> new PostException("No Post found with that Slug"));
    }

    @Override
    public void createPost(PostDto postDto) {
        savePost(PostDto.unpackDto(postDto));
    }

    private void savePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public List<PostDto> getAllPosts(int pageNumber) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        return getPosts(pageRequest).stream().map(PostDto::packDto).collect(Collectors.toList());
    }

    private Slice<Post> getPosts(Pageable pageRequest) {
        return postRepository.findAll(pageRequest);
    }

    @Override
    public List<PostDto> search(String searchPhrase, int pageNumber) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        return searchPosts(searchPhrase, page).stream().map(PostDto::packDto).collect(Collectors.toList());
    }

    private Slice<Post> searchPosts(String searchPhrase, Pageable pageRequest) {
        Slice<Post> postsByTitle = postRepository.findPostsByTitleContaining(searchPhrase, pageRequest);
        if (postsByTitle.isEmpty()) {
            return postRepository.findPostsByBodyContaining(searchPhrase, pageRequest);
        }
        return postsByTitle;
    }

    @Override
    public void updatePost(String postId, PostDto postDto) throws PostException {
        Post postToUpdate = getAPostById(postId);
        postToUpdate.setTitle(postDto.getTitle());
        postToUpdate.setBody(postDto.getBody());
        postToUpdate.setIntro(postDto.getIntro());
        postToUpdate.setFeatured(postDto.isFeatured());
        postToUpdate.setCoverImage(postDto.getCoverImage());
        //ToDo add functionality to update categories
        savePost(postToUpdate);
    }

    @Override
    public void deletePost(String postId) throws PostException {
        Post postToDelete = getAPostById(postId);
        deletePost(postToDelete);
    }

    private void deletePost(Post post) {
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getAllPostsBefore(LocalDate date, int pageNumber) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        return getPostsBefore(date, page).stream()
                .map(PostDto::packDto)
                .collect(Collectors.toList());
    }

    private Slice<Post> getPostsBefore(LocalDate date, Pageable pageRequest) {
        return postRepository.findPostByPublishDateBefore(date, pageRequest);
    }

    @Override
    public List<PostDto> getAllPostsAfter(LocalDate date, int pageNumber) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Slice<Post> posts = getPostsAfter(date, page);
        return posts.stream().map(PostDto::packDto).collect(Collectors.toList());
    }

    private Slice<Post> getPostsAfter(LocalDate date, Pageable pageRequest) {
        return postRepository.findPostByPublishDateAfter(date, pageRequest);
    }

    @Override
    public List<PostDto> getAllPostsInACategory(String categoryName, int pageNumber) throws CategoryException {
        Category postCategory = categoryService.getCategoryByName(categoryName);
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        return getPostsForCategory(postCategory, pageRequest)
                .stream()
                .map(PostDto::packDto)
                .collect(Collectors.toList());
    }

    private Slice<Post> getPostsForCategory(Category category, Pageable pageRequest) {
        return postRepository.findPostsByCategory(category, pageRequest);
    }
}
