package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.model.Vehicle;
import java.sql.*;
import java.util.*;

public class VehicleDao {
    private static final String SEL="SELECT * FROM vehicles";
    private final javax.sql.DataSource ds;
    public VehicleDao(javax.sql.DataSource ds){this.ds=ds;}

    private Vehicle map(ResultSet rs)throws SQLException{
        return new Vehicle(rs.getInt("id"),rs.getString("vin"),rs.getString("make"),rs.getString("model"),
                rs.getInt("year"),rs.getDouble("price"),rs.getString("color"),rs.getInt("mileage"),rs.getString("type"));
    }
    private List<Vehicle> mapAll(ResultSet rs)throws SQLException{ List<Vehicle>L=new ArrayList<>();while(rs.next())L.add(map(rs));return L;}

    public List<Vehicle> findAll()throws SQLException{try(var c=ds.getConnection();var st=c.createStatement();var rs=st.executeQuery(SEL)){return mapAll(rs);}}
    public Optional<Vehicle> findByVin(String vin)throws SQLException{String q=SEL+" WHERE vin=?";try(var c=ds.getConnection();var ps=c.prepareStatement(q)){ps.setString(1,vin);try(var rs=ps.executeQuery()){return rs.next()?Optional.of(map(rs)):Optional.empty();}}}
    public void create(Vehicle v)throws SQLException{String q="INSERT INTO vehicles(vin,make,model,year,price,color,mileage,type) VALUES (?,?,?,?,?,?,?,?)";
        try(var c=ds.getConnection();var ps=c.prepareStatement(q)){ps.setString(1,v.getVin());ps.setString(2,v.getMake());ps.setString(3,v.getModel());
            ps.setInt(4,v.getYear());ps.setDouble(5,v.getPrice());ps.setString(6,v.getColor());ps.setInt(7,v.getMileage());ps.setString(8,v.getType());ps.executeUpdate();}}
    public void update(Vehicle v)throws SQLException{String q="UPDATE vehicles SET make=?,model=?,year=?,price=?,color=?,mileage=?,type=? WHERE vin=?";
        try(var c=ds.getConnection();var ps=c.prepareStatement(q)){ps.setString(1,v.getMake());ps.setString(2,v.getModel());ps.setInt(3,v.getYear());
            ps.setDouble(4,v.getPrice());ps.setString(5,v.getColor());ps.setInt(6,v.getMileage());ps.setString(7,v.getType());ps.setString(8,v.getVin());ps.executeUpdate();}}
    public boolean delete(String vin)throws SQLException{try(var c=ds.getConnection();var ps=c.prepareStatement("DELETE FROM vehicles WHERE vin=?")){ps.setString(1,vin);return ps.executeUpdate()>0;}}
}
