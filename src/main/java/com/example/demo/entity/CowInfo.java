package com.example.demo.entity;


import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "cow_info")
public class CowInfo {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String name;

    private String phone;

    private Integer amount;

    private String address;

    /**
     * 添加和修改不加入sql语句
     */
    @Column(insertable = false, updatable = false)
    private Date createDate;

}
