package org.example;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long user_id;

  @Column(name = "firstName")
  private String firstName;
  @Column(name = "lastName")
  private String lastName;
  @Column(name = "dob")
  private LocalDate dob;
  @Column(name = "password")
  private String password;
  @Column(name = "balance")
  private double balance;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<Transaction> transactions;

  //Constructors
  User(){}

  User(String firstName, String lastName, LocalDate dob, String password){

    if (!firstName.isEmpty() && !lastName.isEmpty()){
      this.firstName = firstName;
      this.lastName = lastName;
    }

    if (dob.isBefore(LocalDate.now())){
      this.dob = dob;
    }

    if (!password.isEmpty()){
      this.password = password;
    }

    this.balance = 0.00;

  }


  //Getter and Setters
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public LocalDate getDob() {
    return dob;
  }

  public void setDob(LocalDate dob) {
    this.dob = dob;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }

  @Override
  public String toString(){
    return "Firstname: " + this.firstName + "\nLastname: " + this.lastName + "\nDOB: " + this.dob + "\nPassword: " + this.password + "\nBalance: " + this.balance;
  }

}
