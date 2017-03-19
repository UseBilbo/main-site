package com.usebilbo.node.domain;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import com.usebilbo.vertx.annotation.ID;
import com.usebilbo.vertx.annotation.Persistent;

@Persistent
public class AccountBase {
    @ID
    private Long id;

    @QuerySqlField
    private String email;
    private String password;
    
    public static AccountBase of(String email) {
        return new AccountBase().setEmail(email);
    }

    public Long getId() {
        return id;
    }

    public AccountBase setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AccountBase setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AccountBase setPassword(String password) {
        this.password = password;
        return this;
    }
}
