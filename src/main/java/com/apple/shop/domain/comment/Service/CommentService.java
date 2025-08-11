package com.apple.shop.domain.comment.Service;

import com.apple.shop.domain.comment.Entity.Comment;
import com.apple.shop.domain.comment.repo.CommentRepo;
import com.apple.shop.domain.item.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class CommentService {

    private final CommentRepo commentRepo;

    // 1. 댓글 리스트 반환
    public List<Comment> getComments(){
        return commentRepo.findAll();
    }
    public List<Comment> getCommentsByParentId(Long parendId){
        return commentRepo.findAllByParentId(parendId);

    }
}
