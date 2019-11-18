package com.example.demo.entity;



import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author YM
 */
@Data
public class Users {

    @Id
    private String dbUser;

    private String userId;

    private String userName;

    private String userDept;
    private Date createDate;

    private String password;

}
