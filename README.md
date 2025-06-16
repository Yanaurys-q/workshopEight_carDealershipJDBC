# Car Dealership Management System (JDBC Edition)

A **console-based** dealership manager written in Java 17.  
Unlike the CSV version from Workshop 5, this edition stores everything in a **MySQL** database via JDBC + Apache DBCP2.

---

## Features

* Add new vehicles to inventory  
* Remove vehicles by VIN  
* List all vehicles  
* Filter vehicles by price, make/model, year, color, mileage, or type  
* Maintain multiple dealership locations  
* Record **sales** and **lease** contracts (auto-timestamped)  
* All data lives in MySQL—no more manual CSV edits

---

## How It Works

1. On startup the app opens a pooled connection to the DB (`DataSourceFactory`).  
2. DAO classes load vehicles, dealerships, and existing contracts.  
3. Users navigate a **menu** to view, filter, add, update, or delete records.  
4. When a sale or lease is processed, the contract row is inserted and the vehicle row is removed from inventory.  
5. Every action is a live SQL operation—no restart needed to see changes.

---

## Interesting Code

The shared **row-mapping** method keeps every DAO tiny:

```java
private List<Vehicle> mapAll(ResultSet rs) throws SQLException {
    List<Vehicle> list = new ArrayList<>();
    while (rs.next()) {
        list.add(new Vehicle(
            rs.getInt("id"),
            rs.getString("vin"),
            rs.getString("make"),
            rs.getString("model"),
            rs.getInt("year"),
            rs.getDouble("price"),
            rs.getString("color"),
            rs.getInt("mileage"),
            rs.getString("type")
        ));
    }
    return list;
}


Each query (findAll, findByPrice, etc.) is just SQL + this mapper—simple, reusable, and safe against SQL-injection thanks to prepared statements.
