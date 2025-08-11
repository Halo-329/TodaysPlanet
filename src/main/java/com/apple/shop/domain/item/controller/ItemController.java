package com.apple.shop.domain.item.controller;

import com.apple.shop.domain.comment.Entity.Comment;
import com.apple.shop.domain.comment.Service.CommentService;
import com.apple.shop.domain.comment.repo.CommentRepo;
import com.apple.shop.domain.item.service.ItemService;
import com.apple.shop.domain.item.entity.Item;
import com.apple.shop.domain.item.service.S3Service;
import com.apple.shop.domain.sales.service.SalesService;
import com.apple.shop.domain.member.service.MyUserDetailsService;
import com.apple.shop.view.ViewPath;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;
    private final S3Service s3Service;
    private final CommentService commentService;
    private final CommentRepo commentRepo;
    private final SalesService salesService;


    // 0. 초기 페이지

    @GetMapping("/list")
    String list() {
        return ViewPath.REDIRECT_ITEM_LIST + "/page/1";
    }

    @GetMapping("/list/page/{num}")
    String list(Model model, @PathVariable Integer num) {
        Page<Item> result=itemService.getPage(num-1,5); // 서버 입장에서 페이지는 0 부터

        model.addAttribute("itemList", result);
        model.addAttribute("currentPage", num);
        model.addAttribute("totalPages", result.getTotalPages());

        return ViewPath.ITEM_LIST;

    }




    // 1. 상품 등록
    @GetMapping("/write")
    String write() {
        return         ViewPath.ITEM_WRITE; // 성공 시 리스트로
    }

    @PostMapping("/add")
    String add(String title, int price, String imgUrl ,int stock ,Model model, Authentication auth ) {
        String usrid=auth.getName();
        boolean result = itemService.savaItem(title, price, stock, model,usrid, imgUrl);


        if (!result) {
            return ViewPath.ITEM_WRITE; // 실패 시 다시 입력페이지로
        }
        return ViewPath.REDIRECT_ITEM_LIST; // 성공 시 리스트로

    }




    // 2. 상품 수정
    @GetMapping("/modify/{id}")
    String modify(@PathVariable Long id, Model model) {
        Optional<Item> opt = itemService.FindItem(id);

        if (opt.isPresent()) {   // 존재하면 true
            model.addAttribute("data", opt.get());
        }
        return ViewPath.ITEM_MODIFY;
    }

    @PostMapping("/updata")
    String updata(Long id, String title, Integer price, Model model) {
        boolean result;

        result = itemService.ChangeItem(id, title, price, model);

        if (!result) {
            return "/modify/" + id; // 실패 시 다시 입력페이지로
        }
        return ViewPath.REDIRECT_ITEM_LIST; // 성공 시 리스트로

    }




    // 3. 상품 삭제
    @DeleteMapping("/items/{id}")
    @ResponseBody
    public String delete(@PathVariable Long id) {
        itemService.DeleteItem(id);
        return "success";
    }




    // 4. 자세히 보기
    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model) {
        Optional<Item> opt = itemService.FindItem(id);
        List<Comment> comment_list = commentService.getCommentsByParentId(id);




        if (opt.isPresent()) {   // 존재하면 true
            model.addAttribute("data", opt.get());
            model.addAttribute("commentList",comment_list);
            return ViewPath.ITEM_DETAIL;
        } else {
            return ViewPath.REDIRECT_MEMBER_LIST;
        }
    }




    // 5. 이미지
    @GetMapping("/presigned-url")
    @ResponseBody
    String getPresignedUrl(@RequestParam String filename){

        var result = s3Service.createPresignedUrl("test/"+filename);

        return result;
    }




    // 6. 댓글
    @PostMapping("/comment")
    String comment(@RequestParam String content, @RequestParam Long parentId, Authentication auth, Model model){   //input name = "content"
        MyUserDetailsService.CustomUser usr = (MyUserDetailsService.CustomUser) auth.getPrincipal();    //스프링 시큐리티에서  customusr로 타입캐스팅해놓음.

        var comment = new Comment();

        comment.setUsername(usr.getUsername());
        comment.setContent(content);
        comment.setParentId(parentId);

        commentRepo.save(comment);

        return ViewPath.REDIRECT_ITEM_DETAIL+parentId;

        //http://localhost:8080/item/detail/55
    }




    // 7.1 검색
    @PostMapping("/search")
    String search(@RequestParam String searchText) throws Exception{
//        List<Item> res=itemService.getSearchItemsList(searchText);
        searchText= URLEncoder.encode(searchText, "UTF-8");
        return ViewPath.REDIRECT_ITEM_SEARCH+searchText;
    }

    // 7.2 검색 결과 뷰
    @GetMapping("/search/{page}")
    String showSearchResults(Model model, @PathVariable Integer page, @RequestParam String searchText) {
        var res = itemService.findItemByTitle(searchText,page-1, 5);
        model.addAttribute("itemList", res);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", res.getTotalPages());

        return ViewPath.ITEM_SEARCH;
    }







}








