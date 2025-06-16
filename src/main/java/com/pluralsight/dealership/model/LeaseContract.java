package com.pluralsight.dealership.model;

import java.time.LocalDate;

public class LeaseContract {
    private int id;
    private String vin;
    private String customer;
    private double amount;
    private LocalDate date;

    public LeaseContract(){}
    public LeaseContract(int id,String vin,String customer,double amount,LocalDate date){
        this.id=id;this.vin=vin;this.customer=customer;this.amount=amount;this.date=date;
    }
    public LeaseContract(String vin,String customer,double amount,LocalDate date){this(0,vin,customer,amount,date);}
    public int getId(){return id;}
    public String getVin(){return vin;}
    public String getCustomer(){return customer;}
    public double getAmount(){return amount;}
    public LocalDate getDate(){return date;}
    public void setCustomer(String c){customer=c;}
    public void setAmount(double a){amount=a;}
    public void setDate(LocalDate d){date=d;}
    @Override public String toString(){
        return String.format("%3d | %-17s | %-15s | $%,.2f | %s",id,vin,customer,amount,date);
    }
}
