package com.strawhatdev.backend.dtos;

import com.strawhatdev.backend.models.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private String id;
    @NotNull
    @NotBlank(message = "title field cannot be empty")
    private String title;
    @NotNull
    @NotBlank(message = "intro field cannot be empty")
    private String intro;
    private boolean isFeatured;
    private LocalDate publishDate;
    @NotNull
    @NotBlank(message = "body field cannot be empty")
    private String body;
    @NotNull
    @NotBlank(message = "category field cannot be empty")
    private String categoryName;
    private String slug;

    public static Post unpackDto(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setIntro(postDto.getIntro());
        post.setFeatured(postDto.isFeatured());
        post.setBody(postDto.getBody());
        return post;
    }

    public static PostDto packDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setFeatured(post.isFeatured());
        postDto.setTitle(post.getTitle());
        postDto.setIntro(post.getIntro());
        postDto.setBody(post.getBody());
        postDto.setPublishDate(post.getPublishDate());
        postDto.setCategoryName(post.getCategory().getName());
        postDto.setSlug(post.getSlug());
        return postDto;
    }
}
