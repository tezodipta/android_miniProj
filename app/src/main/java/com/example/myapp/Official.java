package com.example.myapp;

public class Official {
    private String id;
    private String name;
    private String designation;
    private String dept;

    public Official(String id, String name, String designation, String dept) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.dept = dept;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }

    public String getDept() {
        return dept;
    }
}
