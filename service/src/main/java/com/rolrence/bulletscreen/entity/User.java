package com.rolrence.bulletscreen.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Rolrence on 2017/5/29.
 *
 */
@Entity
@Table(name = "user")
public class User implements Serializable {
    // private static final long serialVersionUID = 1L;
    public User() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id = 0;

    @Column(name = "name", length = 40)
    private String name;

    @Column(name = "password", length = 30)
    private String password;

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "Name:" + this.getName() + ", Password:" + this.getPassword();
    }

    public int hashCode() {
        if (this.id != 0) {
            return this.id;
        } else {
            return super.hashCode();
        }
    }
}