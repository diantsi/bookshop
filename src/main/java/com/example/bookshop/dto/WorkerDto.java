package com.example.bookshop.dto;

import com.example.bookshop.entity.Worker;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

public class WorkerDto {
    private String tabNumber;
    private String surname;
    private String firstName;
    private String middleName;
    private String occupation;
    private BigDecimal salary;

    private LocalDate startWorkingDate;
    private LocalDate dateOfBirth;

    private Integer age;
    private String city;
    private String street;
    private String building;
    private Integer flat;
    private Integer index;
    private String email;
    private String password;
    private String phoneNumber;

    public WorkerDto() {
    }

    public WorkerDto(Worker worker) {
        this.tabNumber = worker.getTabNumber();
        this.surname = worker.getSurname();
        this.firstName = worker.getFirstName();
        this.middleName = worker.getMiddleName();
        this.occupation = worker.getOccupation();
        this.salary = worker.getSalary();

        /*LocalDate start = worker.getStartWorkingDate();
        this.startWorkingDateString = start != null ? start.toString() : "";

        LocalDate birth = worker.getDateOfBirth();
        this.dateOfBirthString = birth != null ? birth.toString() : "";*/

        this.age = worker.getAge();
        this.city = worker.getCity();
        this.street = worker.getStreet();
        this.building = worker.getBuilding();
        this.flat = worker.getFlat();
        this.index = worker.getIndex();
        this.email = worker.getEmail();
        this.password = worker.getPassword();
        this.phoneNumber = worker.getPhoneNumber();
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

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getStartWorkingDate() {
        return startWorkingDate;
    }

    public void setStartWorkingDate(LocalDate startWorkingDateString) {
        this.startWorkingDate = startWorkingDateString;
    }


    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirthString) {
        this.dateOfBirth = dateOfBirthString;
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
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

    public Integer getFlat() {
        return flat;
    }

    public void setFlat(Integer flat) {
        this.flat = flat;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
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


    public Worker toEntity() {
        /*LocalDate startDate = null;
        LocalDate birthDate = null;

        try {
            if (startWorkingDateString != null && !startWorkingDateString.isEmpty()) {
                startDate = LocalDate.parse(startWorkingDateString);
            }
            if (dateOfBirthString != null && !dateOfBirthString.isEmpty()) {
                birthDate = LocalDate.parse(dateOfBirthString);
            }
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Invalid date format in WorkerDto", e);
        }*/

        Integer age = Period.between(dateOfBirth, LocalDate.now()).getYears();

        return new Worker(
                tabNumber,
                surname,
                firstName,
                middleName,
                occupation,
                salary,
                startWorkingDate,
                dateOfBirth,
                age,
                city,
                street,
                building,
                flat,
                index,
                email,
                password,
                phoneNumber
        );
    }
}
