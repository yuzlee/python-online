package com.rolrence.bulletscreen.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Rolrence on 2017/5/29.
 *
 */
@Entity
@Table(name = "comment")
public class Comment implements Serializable {
    public Comment() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int ID;

    @Column(name = "user_id")
    private int userID;

    @Column(name = "feature_id")
    private int featureID;

    @Column(name = "date")
    private Date date;

    @Column(name = "content", length = 1024)
    private String content;

    public int getID() {
        return this.ID;
    }
    public void setID(int id) {
        this.ID = id;
    }

    public int getUserID() {
        return this.userID;
    }
    public void setUserID(int id) {
        this.userID = id;
    }

    public int getFeatureID() {
        return this.featureID;
    }
    public void setFeatureID(int id) {
        this.featureID = id;
    }

    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
