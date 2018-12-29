package com.smart.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Product {
    private String productID;
    private String productName;
    private String pictureurlpath;
    private String product_type;
    private String product_type_id;
    private Date createtime;
    private Date updatetime;
    private Date deletetime;
    private int is_delete;
}
