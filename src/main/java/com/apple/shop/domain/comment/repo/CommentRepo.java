package com.apple.shop.domain.comment.repo;

import com.apple.shop.domain.comment.Entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Long> {
        public List<Comment> findAllByParentId(Long a);
        public Page<Comment> findPageBy(Pageable page);

}
