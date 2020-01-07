package com.orochi.vertx.entity;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@DataObject(generateConverter = true)
@Table(name = "trade_order_coupon")
public class User {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;

    private String phone;

    public User() {
    }

    public User(JsonObject jsonObject) {
           this.id = jsonObject.getLong("id");
           this.name = jsonObject.getString("name");
           this.age = jsonObject.getInteger("age");
           this.phone = jsonObject.getString("phone");
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public JsonObject toJson() {
        return JsonObject.mapFrom(this);
    }

}