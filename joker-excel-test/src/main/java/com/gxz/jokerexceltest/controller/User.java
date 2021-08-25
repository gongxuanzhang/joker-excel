package com.gxz.jokerexceltest.controller;

import com.tincery.starter.annotation.ExcelData;
import com.tincery.starter.annotation.ExcelField;

import java.time.LocalDateTime;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@ExcelData
public class User {


    private String name;
    @ExcelField(select = {"男", "女"})
    private String sex;
    @ExcelField(errorMessage = "第r%行 第c%列 错了 生日解析错误  这个值是v%")
    private LocalDateTime birthday;
    @ExcelField(errorMessage = "年龄解析错误")
    private int age;

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public int getAge() {
        return age;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public User setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
        return this;
    }

    public User setAge(int age) {
        this.age = age;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", age=" + age +
                '}';
    }
}
