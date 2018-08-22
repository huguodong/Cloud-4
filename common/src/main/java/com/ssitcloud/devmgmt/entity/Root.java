package com.ssitcloud.devmgmt.entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.ssitcloud.common.util.CDataAdapter;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Root {
//绑定自己的适配器类，适配希望包含在CData数据块中的javabean成员变量。
//这里的空值是为了测试，无其他涵义。
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String name = "";
    @XmlJavaTypeAdapter(CDataAdapter.class)
    private String surname;
    private String id;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}