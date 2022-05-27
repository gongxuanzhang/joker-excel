package com.gxz.jokerexceltest.controller;

import org.gxz.joker.starter.annotation.DynamicSelect;
import org.gxz.joker.starter.annotation.ExcelData;
import org.gxz.joker.starter.annotation.ExcelField;
import org.gxz.joker.starter.annotation.TextBox;
import org.gxz.joker.starter.element.Checkable;
import org.gxz.joker.starter.exception.CheckValueException;

import java.time.LocalDateTime;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@ExcelData
@DynamicSelect(name = "东汉末年", selector = UserSelector.class)
@TextBox(value = User.a,height = 10)
public class User implements Checkable {
    public static final String a = "sdfsld fjlskdflkasj ldfk jlsadkf jlsakd flkasjflkasd f\nsadlf jsaldf \nasldf " +
            "kjlasd f";

    @ExcelField(name = "姓名", unique = true, width = 7000)
    private String name;
    @ExcelField(select = {"男", "女"})
    private String sex;
    @ExcelField(errorMessage = "第r%行 第c%列 错了 生日解析错误  这个值是v%")
    private LocalDateTime birthday;
    @ExcelField(name = "邮箱", encrypt = "4~@")
    private String email;
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

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
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

    @Override
    public void check() throws CheckValueException {
        if (this.name.contains("1")) {
            throw new CheckValueException("a");
        }
    }
}
