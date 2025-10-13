package org.lab6.entity;

public class StudentEntity {
    private int id;
    private String name;
    private String group;
    private double avgGrade;

    public StudentEntity() {}

    public StudentEntity(int id, String name, String group, double avgGrade) {
        this.id = id;
        this.name = name;
        this.group = group;
        this.avgGrade = avgGrade;
    }

    public StudentEntity(String name, String group, double avgGrade) {
        this.name = name;
        this.group = group;
        this.avgGrade = avgGrade;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGroup() { return group; }
    public void setGroup(String group) { this.group = group; }

    public double getAvgGrade() { return avgGrade; }
    public void setAvgGrade(double avgGrade) { this.avgGrade = avgGrade; }
}
