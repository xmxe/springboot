package com.xmxe.spring2.common.entity;


/**
 * 一个简单的Message模型
 */
public class MyMessage {

    /**
     * long 类型
     */
    private long id;

    /**
     * name
     */
    private String name;

    /**
     * age
     */
    private int type;

    /**
     * 创建时间搓
     */
    private long createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
