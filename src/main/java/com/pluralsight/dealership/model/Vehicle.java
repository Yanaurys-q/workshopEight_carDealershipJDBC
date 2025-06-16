package com.pluralsight.dealership.model;

public class Vehicle {
    private int id;
    private String vin;
    private String make;
    private String model;
    private int year;
    private double price;
    private String color;
    private int mileage;
    private String type;

    public Vehicle(){}
    public Vehicle(int id,String vin,String make,String model,int year,double price,String color,int mileage,String type){
        this.id=id;this.vin=vin;this.make=make;this.model=model;this.year=year;this.price=price;this.color=color;this.mileage=mileage;this.type=type;
    }
    public Vehicle(String vin,String make,String model,int year,double price,String color,int mileage,String type){
        this(0,vin,make,model,year,price,color,mileage,type);
    }
    public int getId(){return id;}
    public String getVin(){return vin;}
    public String getMake(){return make;}
    public String getModel(){return model;}
    public int getYear(){return year;}
    public double getPrice(){return price;}
    public String getColor(){return color;}
    public int getMileage(){return mileage;}
    public String getType(){return type;}

    public void setMake(String m){make=m;}
    public void setModel(String m){model=m;}
    public void setYear(int y){year=y;}
    public void setPrice(double p){price=p;}
    public void setColor(String c){color=c;}
    public void setMileage(int m){mileage=m;}
    public void setType(String t){type=t;}

    @Override public String toString(){
        return String.format("%-17s | %-10s %-10s | %4d | $%,10.2f | %-6s | %,7d mi | %-6s",vin,make,model,year,price,color,mileage,type);
    }
}
