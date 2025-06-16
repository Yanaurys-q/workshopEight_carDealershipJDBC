package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.model.SalesContract;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class SalesDao {
    private final javax.sql.DataSource ds;
    public SalesDao(javax.sql.DataSource ds){this.ds=ds;}

    private SalesContract map(ResultSet rs)throws SQLException{
        return new SalesContract(rs.getInt("id"),rs.getString("vin"),rs.getString("customer"),rs.getDouble("amount"),rs.getDate("date").toLocalDate());
    }
    public List<SalesContract> findAll()throws SQLException{
        List<SalesContract>L=new ArrayList<>();
        try(var c=ds.getConnection();var st=c.createStatement();var rs=st.executeQuery("SELECT * FROM sales_contracts")){
            while(rs.next())L.add(map(rs));}
        return L;
    }
    public Optional<SalesContract> findById(int id)throws SQLException{
        try(var c=ds.getConnection();var ps=c.prepareStatement("SELECT * FROM sales_contracts WHERE id=?")){ps.setInt(1,id);
            try(var rs=ps.executeQuery()){return rs.next()?Optional.of(map(rs)):Optional.empty();}}
    }
    public void create(SalesContract s)throws SQLException{
        try(var c=ds.getConnection();var ps=c.prepareStatement("INSERT INTO sales_contracts(vin,customer,amount,date) VALUES (?,?,?,?)")){
            ps.setString(1,s.getVin());ps.setString(2,s.getCustomer());ps.setDouble(3,s.getAmount());ps.setDate(4, Date.valueOf(s.getDate()));ps.executeUpdate();}
    }
    public void update(SalesContract s)throws SQLException{
        try(var c=ds.getConnection();var ps=c.prepareStatement("UPDATE sales_contracts SET customer=?,amount=?,date=? WHERE id=?")){
            ps.setString(1,s.getCustomer());ps.setDouble(2,s.getAmount());ps.setDate(3,Date.valueOf(s.getDate()));ps.setInt(4,s.getId());ps.executeUpdate();}
    }
    public boolean delete(int id)throws SQLException{
        try(var c=ds.getConnection();var ps=c.prepareStatement("DELETE FROM sales_contracts WHERE id=?")){ps.setInt(1,id);return ps.executeUpdate()>0;}
    }
}
