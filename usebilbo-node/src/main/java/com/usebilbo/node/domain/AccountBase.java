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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
