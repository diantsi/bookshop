package com.example.bookshop.entity;

//        `ID_number` varchar(32) NOT NULL,
//        `Surname` varchar(30) NOT NULL,
//        `First_name` varchar(30) NOT NULL,
//        `Middle_name` varchar(30) NULL,
//        `Phone_number` varchar(13) NOT NULL,
//        `Registration_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//        `Date_of_birth` date NOT NULL,
//        `Age` int NOT NULL,
//        `Email_addres` varchar(30) NOT NULL,
//        `Bonus_number` int NOT NULL,
//        PRIMARY KEY (`ID_number`),
//        UNIQUE KEY (`Phone_number`)

import com.example.bookshop.controller.ReceiptController;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

public class ClientCard {
    private String idNumber;          //display
    private String surname;           //display
    private String firstName;         //display
    private String middleName; //
    private String phoneNumber;       //display
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private Integer age;
    private String email;
    private Integer numberOfBonuses;  //display
    private Integer numberOfReceipts;  //display


    public ClientCard() {}
    public ClientCard(String idNumber, String surname, String firstName, String middleName, String phoneNumber, LocalDate registrationDate, LocalDate dateOfBirth, Integer age, String email, Integer numberOfBonuses) {
        this.idNumber = idNumber;
        this.surname = surname;
        this.firstName = firstName;
        this.middleName = middleName;
        this.phoneNumber = phoneNumber;
        this.registrationDate = registrationDate;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.email = email;
        this.numberOfBonuses = numberOfBonuses;
    }

    public ClientCard(String idNumber, String surname, String firstName, String phoneNumber, Integer numberOfBonuses) {
        this.idNumber = idNumber;
        this.surname = surname;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.numberOfBonuses = numberOfBonuses;

    }


    public ClientCard orElse(Object o) {
        if(this == null) {
            return null;
        }
        return this;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer calculateAge() {
        if (dateOfBirth == null) return 0;
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getNumberOfBonuses() {
        return numberOfBonuses;
    }

    public void setNumberOfBonuses(Integer numberOfBonuses) {
        this.numberOfBonuses = numberOfBonuses;
    }

    public Integer getNumberOfReceipts() {
        return numberOfReceipts;
    }

    public void setNumberOfReceipts(Integer number) {
        this.numberOfReceipts = number;
    }

}
