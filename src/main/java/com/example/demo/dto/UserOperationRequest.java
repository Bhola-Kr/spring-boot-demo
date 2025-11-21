package com.example.demo.dto;

public class UserOperationRequest {
    private String type;   // GET_BY_ID, GET_ALL_PAGED, FILTER_BY_NAME
    private Integer id;
    private String name;
    private Integer page;
    private Integer size;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }   

    public Integer getId() {
        return id;
    }   
    
    public void setId(Integer id) {
        this.id = id;
    }   

    public String getName() {
        return name;
    }   

    public void setName(String name) {
        this.name = name;
    }   

    public Integer getPage() {
        return page;
    }   

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

}
