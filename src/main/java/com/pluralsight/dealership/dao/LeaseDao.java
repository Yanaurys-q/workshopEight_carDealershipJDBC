package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.model.LeaseContract;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class LeaseDao {
    private final javax.sql.DataSource ds;
    public LeaseDao(javax.sql.DataSource ds){this.ds=ds;}
    private LeaseContract map(ResultSet rs)throws SQLException{
        return new LeaseContract(rs.getInt("id"),rs.getString("vin"),rs.getString("customer"),rs.getDouble("amount"),rs.getDate("date").toLocalDate());
    }
    public List<LeaseContract> findAll()throws SQLException{
        List<LeaseContract>L=new ArrayList<>();
        try(var c=ds.getConnection();var st=c.createStatement();var rs=st.executeQuery("SELECT * FROM lease_contracts")){
            while(rs.next())L.add(map(rs));}
        return L;
    }
    public Optional<LeaseContract> findById(int id)throws SQLException{
        try(var c=ds.getConnection();var ps=c.prepareStatement("SELECT * FROM lease_contracts WHERE id=?")){ps.setInt(1,id);
            try(var rs=ps.executeQuery()){return rs.next()?Optional.of(map(rs)):Optional.empty();}}
    }
    public void create(LeaseContract l)throws SQLException{
        try(var c=ds.getConnection();var ps=c.prepareStatement("INSERT INTO lease_contracts(vin,customer,amount,date) VALUES (?,?,?,?)")){
            ps.setString(1,l.getVin());ps.setString(2,l.getCustomer());ps.setDouble(3,l.getAmount());ps.setDate(4,Date.valueOf(l.getDate()));ps.executeUpdate();}
    }
    public void update(LeaseContract l)throws SQLException{
        try(var c=ds.getConnection();var ps=c.prepareStatement("UPDATE lease_contracts SET customer=?,amount=?,date=? WHERE id=?")){
            ps.setString(1,l.getCustomer());ps.setDouble(2,l.getAmount());ps.setDate(3, Date.valueOf(l.getDate()));ps.setInt(4,l.getId());ps.executeUpdate();}
    }
    public boolean delete(int id)throws SQLException{
        try(var c=ds.getConnection();var ps=c.prepareStatement("DELETE FROM lease_contracts WHERE id=?")){ps.setInt(1,id);return ps.executeUpdate()>0;}
    }
}
