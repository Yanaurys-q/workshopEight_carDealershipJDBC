package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.model.Dealership;
import java.sql.*;
import java.util.*;

public class DealershipDao {
    private final javax.sql.DataSource ds;
    public DealershipDao(javax.sql.DataSource ds){this.ds=ds;}
    private Dealership map(ResultSet rs)throws SQLException{
        return new Dealership(rs.getInt("id"),rs.getString("name"),rs.getString("address"),rs.getString("phone"));
    }
    public List<Dealership> findAll()throws SQLException{
        List<Dealership> list=new ArrayList<>();
        try(var c=ds.getConnection();var st=c.createStatement();var rs=st.executeQuery("SELECT * FROM dealerships")){
            while(rs.next())list.add(map(rs));
        }
        return list;
    }
    public Optional<Dealership> findById(int id)throws SQLException{
        try(var c=ds.getConnection();var ps=c.prepareStatement("SELECT * FROM dealerships WHERE id=?")){
            ps.setInt(1,id);
            try(var rs=ps.executeQuery()){return rs.next()?Optional.of(map(rs)):Optional.empty();}
        }
    }
    public void create(Dealership d)throws SQLException{
        try(var c=ds.getConnection();var ps=c.prepareStatement("INSERT INTO dealerships(name,address,phone) VALUES (?,?,?)")){
            ps.setString(1,d.getName());ps.setString(2,d.getAddress());ps.setString(3,d.getPhone());ps.executeUpdate();
        }
    }
    public void update(Dealership d)throws SQLException{
        try(var c=ds.getConnection();var ps=c.prepareStatement("UPDATE dealerships SET name=?,address=?,phone=? WHERE id=?")){
            ps.setString(1,d.getName());ps.setString(2,d.getAddress());ps.setString(3,d.getPhone());ps.setInt(4,d.getId());ps.executeUpdate();
        }
    }
    public boolean delete(int id)throws SQLException{
        try(var c=ds.getConnection();var ps=c.prepareStatement("DELETE FROM dealerships WHERE id=?")){
            ps.setInt(1,id);return ps.executeUpdate()>0;
        }
    }
}
