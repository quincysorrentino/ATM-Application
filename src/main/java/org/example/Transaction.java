package org.example;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

@Entity
@Table(name = "transactions")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long transaction_id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "transaction_type")
  private String transactionType;
  @Column(name = "amount")
  private double amount;
  @Column(name = "transaction_dateTime")
  private LocalDateTime transactionDateTime;

  Transaction(){

  }

  Transaction(String transactionType, double amount, User user){
    this.transactionType = transactionType;
    this.amount = amount;
    this.transactionDateTime = LocalDateTime.now();
    this.user = user;
  }

  public long getTransaction_id() {
    return transaction_id;
  }

  public void setTransaction_id(long transaction_id) {
    this.transaction_id = transaction_id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public LocalDateTime getTransactionDateTime() {
    return transactionDateTime;
  }

  public void setTransactionDateTime(LocalDateTime transactionDateTime) {
    this.transactionDateTime = transactionDateTime;
  }

  @Override
  public String toString(){
    return "Date and Time: " + transactionDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "      Amount: " + "$" + amount + "      Transaction Type: " + transactionType;
  }

}
