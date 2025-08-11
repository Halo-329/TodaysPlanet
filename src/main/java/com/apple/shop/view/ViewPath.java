package com.apple.shop.view;

public class ViewPath {

    private static final String FEATURE_PATH="feature/";

    private static final String CART_PATH = FEATURE_PATH + "cart/";
    private static final String COMMENT_PATH = FEATURE_PATH + "comment/";
    private static final String COMMON_PATH = FEATURE_PATH + "common/";
    private static final String ITEM_PATH = FEATURE_PATH + "item/";
    private static final String LAYOUT_PATH = FEATURE_PATH + "layout/";
    private static final String MEMBER_PATH = FEATURE_PATH + "member/";
    private static final String SALES_PATH = FEATURE_PATH + "sales/";



    // 장바구니
    public static final String CART_VIEW = CART_PATH + "cartView";


    // 댓글
    public static final String COMMENT = COMMENT_PATH + "comment";


    // 에러
    public static final String ERROR = COMMON_PATH + "error";


    // item
    public static final String ITEM_DETAIL = ITEM_PATH + "detail";
    public static final String ITEM_LIST = ITEM_PATH + "list";
    public static final String ITEM_MODIFY = ITEM_PATH + "modify";
    public static final String ITEM_SEARCH = ITEM_PATH + "search";
    public static final String ITEM_WRITE = ITEM_PATH + "write";

    //
    public static final String LAYOUT_NAV = LAYOUT_PATH + "nav";
    public static final String LAYOUT_PAGINATION = LAYOUT_PATH + "pagination";
    public static final String LAYOUT_SEARCH_BAR = LAYOUT_PATH + "searchBar";

    // login
    public static final String MEMBER_LOGIN = MEMBER_PATH + "login";
    public static final String MEMBER_MY_PAGE = MEMBER_PATH + "my-page";
    public static final String MEMBER_SIGNUP = MEMBER_PATH + "signup";

    // salse
    public static final String SALES_LIST = SALES_PATH + "list";
    public static final String SALES_ORDER = SALES_PATH + "order";

    // redirects
    public static final String REDIRECT_ITEM_LIST = "redirect:/item/list";
    public static final String REDIRECT_ITEM_DETAIL = "redirect:/item/detail/";
    public static final String REDIRECT_ITEM_SEARCH = "redirect:/item/search/1?searchText=";
    public static final String REDIRECT_MEMBER_LIST = "redirect:/member/list";
    public static final String REDIRECT_SALES_ORDER = "redirect:/sales/order";
}