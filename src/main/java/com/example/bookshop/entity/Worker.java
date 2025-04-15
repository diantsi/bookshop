package com.example.bookshop.entity;

//`Tab_number` varchar(30) NOT NULL,
//                                        `Surname` varchar(30) NOT NULL,
//                                        `First_name` varchar(30) NOT NULL,
//                                        `Middle_name` varchar(30) NULL,
//                                        `Occupation` varchar(32) NOT NULL,
//                                        `Salary` decimal(10,2) NOT NULL,
//                                        `Start_working_date` date NOT NULL,
//        `Date_of_birth` date NOT NULL,
//        `Age` int(11) NOT NULL,
//                                        `City` varchar(30) NOT NULL,
//                                        `Street` varchar(50) NOT NULL,
//                                        `Building` varchar(5) NOT NULL,
//                                        `Flat` int(11) NULL,
//                                        `Index` int(11) NOT NULL,
//                                        `Email_address` varchar(30) NOT NULL,
//                                        `password` varchar(255) NOT NULL, -- Increased length for hashed passwords
//                                        `Phone_number` varchar(13) NOT NULL,

import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;
import java.time.Period;

public class Worker {
    private String tabNumber;         //display
    private String surname;         //display
    private String firstName;       //display
    private String middleName; //
    private String occupation;      //display
    private double salary;
    private LocalDate startWorkingDate;
    private LocalDate dateOfBirth;
    private int age;
    private String city;
    private String street;
    private String building;
    private int flat; //
    private int index;
    private String email;          //display
    private String password;
    private String phoneNumber;

    private boolean hasFlat;

    public Worker() {}
    public Worker(String tabNumber, String surname, String firstName, String middleName, String occupation, double salary, LocalDate startWorkingDate, LocalDate dateOfBirth, int age, String city, String street, String building, int flat, int index, String email, String password, String phoneNumber) {
        this.tabNumber = tabNumber;
        this.surname = surname;
        this.firstName = firstName;
        this.middleName = middleName;
        this.occupation = occupation;
        this.salary = salary;
        this.startWorkingDate = startWorkingDate;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.city = city;
        this.street = street;
        this.building = building;
        this.flat = flat;
        this.index = index;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.hasFlat = true;

    }
    public Worker(String tabNumber, String surname, String firstName, String occupation, double salary, LocalDate startWorkingDate, LocalDate dateOfBirth, int age, String city, String street, String building, int flat, int index, String email, String password, String phoneNumber) {
        this.tabNumber = tabNumber;
        this.surname = surname;
        this.firstName = firstName;
        this.occupation = occupation;
        this.salary = salary;
        this.startWorkingDate = startWorkingDate;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.city = city;
        this.street = street;
        this.building = building;
        this.flat = flat;
        this.index = index;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.hasFlat = true;

    }
    public Worker(String tabNumber, String surname, String firstName, String middleName, String occupation, double salary, LocalDate startWorkingDate, LocalDate dateOfBirth, int age, String city, String street, String building, int index, String email, String password, String phoneNumber) {
        this.tabNumber = tabNumber;
        this.surname = surname;
        this.firstName = firstName;
        this.middleName = middleName;
        this.occupation = occupation;
        this.salary = salary;
        this.startWorkingDate = startWorkingDate;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.city = city;
        this.street = street;
        this.building = building;
        this.index = index;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.hasFlat = false;

    }
    public Worker(String tabNumber, String surname, String firstName, String occupation, double salary, LocalDate startWorkingDate, LocalDate dateOfBirth, int age, String city, String street, String building, int index, String email, String password, String phoneNumber) {
        this.tabNumber = tabNumber;
        this.surname = surname;
        this.firstName = firstName;
        this.occupation = occupation;
        this.salary = salary;
        this.startWorkingDate = startWorkingDate;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.city = city;
        this.street = street;
        this.building = building;
        this.index = index;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.hasFlat = false;

    }
    public Worker(String tabNumber, String surname, String firstName, String occupation, String email) {
        this.tabNumber = tabNumber;
        this.surname = surname;
        this.firstName = firstName;
        this.occupation = occupation;
        this.email = email;

    }


    public String getTabNumber() {
        return tabNumber;
    }

    public void setTabNumber(String tabNumber) {
        this.tabNumber = tabNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean hasMiddleName() {
        return middleName!=null;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDate getStartWorkingDate() {
        return startWorkingDate;
    }

    public void setStartWorkingDate(LocalDate startWorkingDate) {
        this.startWorkingDate = startWorkingDate;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int calculateAge() {
        if (dateOfBirth == null) return 0;
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public boolean hasFlat() {
        return hasFlat;
    }

    public int getFlat() {
        return flat;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Worker orElse(Object o) {
        if(this == null) {
            return null;
        }
        return this;
    }


}
