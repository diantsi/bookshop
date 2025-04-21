package com.example.bookshop.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//`ordered_number` int(11) NOT NULL AUTO_INCREMENT,
//                                        `user_name` varchar(30) NOT NULL,
//                                        `user_email` varchar(30) NOT NULL,
//                                        `number_of_chars` int(11) NOT NULL,
//                                        `grade` int(11) NOT NULL,
//                                        `review_date` date NOT NULL,
//        `review_status` varchar(20) NOT NULL,
//                                        `ISBN_book` varchar(30) NOT NULL,
//                                        `answered_number` int(11) NULL,
//                                        `tab_number_of_worker` varchar(30) NULL,
public class Review {
    private Integer id;
    private String userName;
    private String userEmail;
    private Integer numberOfChars;           //TODO: обраховується
    private String text;
    private Integer grade;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String status;

    private String bookISBN;                 //TODO: випадаючий список з реальної бд з пошуком
    private Integer numberOfAnswer;
    private String tabNumber;                //TODO: підтягується системою


    public Review() {
    }

    public Review(Integer id, String userName, String userEmail, Integer numberOfChars, String text, Integer grade, LocalDate date, String status, String bookISBN, Integer numberOfAnswer, String tabNumber) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.numberOfChars = numberOfChars;
        this.grade = grade;
        this.date = date;
        this.text = text;
        this.status = status;
        this.bookISBN = bookISBN;
        this.numberOfAnswer = numberOfAnswer;
        this.tabNumber = tabNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getNumberOfChars() {
        return numberOfChars;
    }

    public void setNumberOfChars(Integer numberOfChars) {
        this.numberOfChars = numberOfChars;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public Integer getNumberOfAnswer() {
        return numberOfAnswer;
    }

    public void setNumberOfAnswer(Integer numberOfAnswers) {
        this.numberOfAnswer = numberOfAnswer;
    }

    public String getTabNumber() {
        return tabNumber;
    }

    public void setTabNumber(String tabNumber) {
        this.tabNumber = tabNumber;
    }
}
