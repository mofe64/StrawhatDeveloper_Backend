package com.strawhatdev.backend.respository;

import com.strawhatdev.backend.models.Category;
import com.strawhatdev.backend.models.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    Optional<Post> findPostByTitle(String title);

    Slice<Post> findPostsByTitleContaining(String searchPhrase, Pageable pageable);

    Slice<Post> findPostsByBodyContaining(String searchPhrase, Pageable pageable);

    Slice<Post> findPostsByCategory(Category category, Pageable pageable);

    Slice<Post> findPostByPublishDateBefore(LocalDate startDate, Pageable pageable);

    Slice<Post> findPostByPublishDateAfter(LocalDate startDate, Pageable pageable);

    Optional<Post> findPostBySlug(String slug);
}
