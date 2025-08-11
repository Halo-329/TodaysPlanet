package com.apple.shop.domain.item.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(indexes = @Index(columnList = "title", name="작명") )
public class Item {     //테이블 속성 메타 데이터
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column
    private String title;
    private Integer price;
    private String usrID;
    private String ImageURL;
    private Integer stock;

    @Override
    public String toString() {
        return "Item{id=" + id + ", title='" + title + "', price=" + price + ". usrID="+ usrID+", ImageURL=" +ImageURL+"}";


    }


}
