package com.dejun.commonsdk.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Author:DoctorWei
 * Time:2018/12/12 17:57
 * Description:
 * email:1348172474@qq.com
 */
@Entity
public class PersonRes {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private int age;

    @Generated(hash = 436788230)
    public PersonRes(Long id, @NotNull String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Generated(hash = 30585480)
    public PersonRes() {
    }

    @Override
    public String toString() {
        return "PersonRes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
