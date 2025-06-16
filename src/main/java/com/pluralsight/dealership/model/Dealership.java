package com.pluralsight.dealership.model;

public class Dealership {
    private int id;
    private String name;
    private String address;
    private String phone;

    public Dealership(){}
    public Dealership(int id,String name,String address,String phone){
        this.id=id;this.name=name;this.address=address;this.phone=phone;
    }
    public Dealership(String name,String address,String phone){
        this(0,name,address,phone);
    }

    public int getId(){return id;}
    public String getName(){return name;}
    public String getAddress(){return address;}
    public String getPhone(){return phone;}

    public void setName(String n){name=n;}
    public void setAddress(String a){address=a;}
    public void setPhone(String p){phone=p;}

    @Override public String toString(){
        return String.format("%3d | %-20s | %-30s | %s",id,name,address,phone);
    }
}
