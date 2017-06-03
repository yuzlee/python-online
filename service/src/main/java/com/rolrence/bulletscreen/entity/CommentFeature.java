package com.rolrence.bulletscreen.entity;

import javax.persistence.*;
import javax.validation.groups.ConvertGroup;
import java.io.Serializable;

/**
 * Created by Rolrence on 2017/5/29.
 *
 */
@Entity
@Table(name = "comment_feature")
public class CommentFeature implements Serializable {
    public CommentFeature() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int ID;

    @Column(name = "name")
    private String name;

    @Column(name = "font")
    private String font;

    @Column(name = "font_size")
    private String fontSize;

    @Column(name = "speed")
    private int speed; // ms

    @Column(name = "color")
    private String color;

    public int getID() {
        return this.ID;
    }
    public void setID(int id) {
        this.ID = id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getFont() {
        return this.font;
    }
    public void setFont(String font) {
        this.font = font;
    }

    public String getFontSize() {
        return this.fontSize;
    }
    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public int getSpeed() {
        return this.speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getColor() {
        return this.color;
    }
    public void setColor(String color) {
        this.color = color;
    }
}
