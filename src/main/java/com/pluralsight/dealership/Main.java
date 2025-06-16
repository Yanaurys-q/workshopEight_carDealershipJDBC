package com.pluralsight.dealership;

import com.pluralsight.dealership.dao.*;
import com.pluralsight.dealership.model.*;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private final VehicleDao vehicleDao;
    private final DealershipDao dealershipDao;
    private final SalesDao salesDao;
    private final LeaseDao leaseDao;
    private final Scanner sc = new Scanner(System.in);

    public Main(DataSource ds) {
        vehicleDao    = new VehicleDao(ds);
        dealershipDao = new DealershipDao(ds);
        salesDao      = new SalesDao(ds);
        leaseDao      = new LeaseDao(ds);
    }

    public static void main(String[] args) {
        new Main(DataSourceFactory.get()).run();
    }

    private void run() {
        while (true) {
            printMenu();
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1"  -> vehicleDao.findAll().forEach(System.out::println);
                    case "2"  -> addVehicle();
                    case "3"  -> updateVehicle();
                    case "4"  -> deleteVehicle();
                    case "5"  -> dealershipDao.findAll().forEach(System.out::println);
                    case "6"  -> addDealership();
                    case "7"  -> updateDealership();
                    case "8"  -> deleteDealership();
                    case "9"  -> salesDao.findAll().forEach(System.out::println);
                    case "10" -> addSales();
                    case "11" -> updateSales();
                    case "12" -> deleteSales();
                    case "13" -> leaseDao.findAll().forEach(System.out::println);
                    case "14" -> addLease();
                    case "15" -> updateLease();
                    case "16" -> deleteLease();
                    case "0"  -> { System.out.println("Good-bye!"); return; }
                    default   -> System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        String hr = "--------------------------------------------------------------";
        System.out.println(hr);
        System.out.println("                   CAR DEALERSHIP MANAGER");
        System.out.println(hr);

        section("Vehicles");
        item("1",  "List vehicles");
        item("2",  "Add vehicle");
        item("3",  "Update vehicle");
        item("4",  "Delete vehicle");

        section("Dealerships");
        item("5",  "List dealerships");
        item("6",  "Add dealership");
        item("7",  "Update dealership");
        item("8",  "Delete dealership");

        section("Sales Contracts");
        item("9",  "List sales");
        item("10", "Add sale");
        item("11", "Update sale");
        item("12", "Delete sale");

        section("Lease Contracts");
        item("13", "List leases");
        item("14", "Add lease");
        item("15", "Update lease");
        item("16", "Delete lease");

        System.out.println(hr);
        item("0", "Exit");
        System.out.println(hr);
        System.out.print("Select > ");
    }
    private void section(String title) {
        System.out.println("\n[" + title + "]");
    }
    private void item(String num, String label) {
        System.out.printf("  %-3s %s%n", num + ")", label);
    }

    private void addVehicle() throws SQLException {
        System.out.print("VIN: "); String vin = sc.nextLine();
        System.out.print("Make: "); String make = sc.nextLine();
        System.out.print("Model: "); String model = sc.nextLine();
        System.out.print("Year: "); int year = Integer.parseInt(sc.nextLine());
        System.out.print("Price: "); double price = Double.parseDouble(sc.nextLine());
        System.out.print("Color: "); String color = sc.nextLine();
        System.out.print("Mileage: "); int miles = Integer.parseInt(sc.nextLine());
        System.out.print("Type: "); String type = sc.nextLine();
        vehicleDao.create(new Vehicle(vin, make, model, year, price, color, miles, type));
        System.out.println("Vehicle added");
    }
    private void updateVehicle() throws SQLException {
        System.out.print("VIN to update: "); String vin = sc.nextLine();
        var opt = vehicleDao.findByVin(vin);
        if (opt.isEmpty()) { System.out.println("Not found"); return; }
        Vehicle v = opt.get();
        System.out.print("New price (" + v.getPrice() + "): "); String in = sc.nextLine();
        if (!in.isBlank()) v.setPrice(Double.parseDouble(in));
        vehicleDao.update(v);
        System.out.println("Vehicle updated");
    }
    private void deleteVehicle() throws SQLException {
        System.out.print("VIN to delete: "); String vin = sc.nextLine();
        System.out.println(vehicleDao.delete(vin) ? "Deleted" : "VIN not found");
    }


    private void addDealership() throws SQLException {
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Address: "); String addr = sc.nextLine();
        System.out.print("Phone: "); String phone = sc.nextLine();
        dealershipDao.create(new Dealership(name, addr, phone));
        System.out.println("Dealership added");
    }
    private void updateDealership() throws SQLException {
        System.out.print("ID to update: "); int id = Integer.parseInt(sc.nextLine());
        var opt = dealershipDao.findById(id);
        if (opt.isEmpty()) { System.out.println("Not found"); return; }
        Dealership d = opt.get();
        System.out.print("New phone (" + d.getPhone() + "): "); String ph = sc.nextLine();
        if (!ph.isBlank()) d.setPhone(ph);
        dealershipDao.update(d);
        System.out.println("Updated");
    }
    private void deleteDealership() throws SQLException {
        System.out.print("ID to delete: "); int id = Integer.parseInt(sc.nextLine());
        System.out.println(dealershipDao.delete(id) ? "Deleted" : "ID not found");
    }

    private void addSales() throws SQLException {
        System.out.print("VIN: "); String vin = sc.nextLine();
        System.out.print("Customer: "); String cust = sc.nextLine();
        System.out.print("Amount: "); double amt = Double.parseDouble(sc.nextLine());
        salesDao.create(new SalesContract(vin, cust, amt, LocalDate.now()));
        System.out.println("Sales added");
    }
    private void updateSales() throws SQLException {
        System.out.print("Sales ID: "); int id = Integer.parseInt(sc.nextLine());
        var opt = salesDao.findById(id);
        if (opt.isEmpty()) { System.out.println("Not found"); return; }
        SalesContract s = opt.get();
        System.out.print("New amount (" + s.getAmount() + "): "); String in = sc.nextLine();
        if (!in.isBlank()) s.setAmount(Double.parseDouble(in));
        salesDao.update(s);
        System.out.println("Updated");
    }
    private void deleteSales() throws SQLException {
        System.out.print("Sales ID delete: "); int id = Integer.parseInt(sc.nextLine());
        System.out.println(salesDao.delete(id) ? "Deleted" : "ID not found");
    }

    private void addLease() throws SQLException {
        System.out.print("VIN: "); String vin = sc.nextLine();
        System.out.print("Customer: "); String cust = sc.nextLine();
        System.out.print("Amount: "); double amt = Double.parseDouble(sc.nextLine());
        leaseDao.create(new LeaseContract(vin, cust, amt, LocalDate.now()));
        System.out.println("Lease added");
    }
    private void updateLease() throws SQLException {
        System.out.print("Lease ID: "); int id = Integer.parseInt(sc.nextLine());
        var opt = leaseDao.findById(id);
        if (opt.isEmpty()) { System.out.println("Not found"); return; }
        LeaseContract l = opt.get();
        System.out.print("New amount (" + l.getAmount() + "): "); String in = sc.nextLine();
        if (!in.isBlank()) l.setAmount(Double.parseDouble(in));
        leaseDao.update(l);
        System.out.println("Updated");
    }
    private void deleteLease() throws SQLException {
        System.out.print("Lease ID delete: "); int id = Integer.parseInt(sc.nextLine());
        System.out.println(leaseDao.delete(id) ? "Deleted" : "ID not found");
    }
}
