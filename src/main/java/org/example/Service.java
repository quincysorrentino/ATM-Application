package org.example;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Service {

  private static final SessionFactory sessionFactory;

  static {
    Configuration configuration = new Configuration();
    configuration.configure("hibernate.cfg.xml");
    sessionFactory = configuration.buildSessionFactory();

    // Registering a shutdown hook to close the SessionFactory when the JVM shuts down
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      if (sessionFactory != null) {
        sessionFactory.close();
      }
    }));
  }


  public static void registerUser(String firstName, String lastName, LocalDate dob, String password) throws Exception{
    try (Session session = sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();

      // Check that the username doesn't already exist
      CriteriaBuilder builder = session.getCriteriaBuilder();
      CriteriaQuery<User> query = builder.createQuery(User.class);
      Root<User> root = query.from(User.class);
      query.select(root).where(builder.and(builder.equal(root.get("firstName"), firstName), builder.equal(root.get("lastName"), lastName)));
      Query<User> q = session.createQuery(query);
      ArrayList<User> users = (ArrayList<User>) q.getResultList();

      if (users.size() > 0) {
        throw new Exception("User already exists");
      }

      // salt and hash password
      byte[] saltByte = generateSalt();
      String hashedPassword = hashPassword(password, saltByte);

      // convert saltByte to a String
      Base64.Encoder enc = Base64.getEncoder();
      String salt = enc.encodeToString(saltByte);

      User user = new User(firstName, lastName, dob, salt, hashedPassword);
      session.persist(user);
      transaction.commit();
    }
  }

  public static void removeUser(User user){
    try (Session session = sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();

      session.remove(user);
      transaction.commit();
    }
  }

  public static User login(String firstName, String lastName, String password) throws Exception{
    try (Session session = sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();

      CriteriaBuilder builder = session.getCriteriaBuilder();
      CriteriaQuery<User> query = builder.createQuery(User.class);
      Root<User> root = query.from(User.class);
      query.select(root).where(builder.and(builder.equal(root.get("firstName"), firstName), builder.equal(root.get("lastName"), lastName)));
      Query<User> q = session.createQuery(query);

      User user;

      try {
        user = q.getSingleResult();
      } catch (Exception e){
        throw new Exception("User doesn't exist");
      }

      String saltString = user.getSalt();
      byte[] saltBytes = Base64.getDecoder().decode(saltString);

      String hashedPassword = hashPassword(password, saltBytes);

      if (hashedPassword.equals(user.getPassword())) {
        return user;
      } else {
        throw new Exception("Incorrect password");
      }
    }
  }

  public static void withdraw(User user, double amount){
    try (Session session = sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();

      if (amount > user.getBalance()) {
        return;
      }

      org.example.Transaction newTransaction = new org.example.Transaction("Withdrawal", amount, user);
      user.setBalance(user.getBalance() - amount);

      session.merge(user);
      session.persist(newTransaction);
      session.flush();

      transaction.commit();
    }
  }

  public static void deposit(User user, double amount) {
    try (Session session = sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();

      org.example.Transaction newTransaction = new org.example.Transaction("Deposit", amount, user);
      user.setBalance(user.getBalance() + amount);

      session.merge(user);
      session.persist(newTransaction);
      session.flush();

      transaction.commit();
    }
  }


  public static List<org.example.Transaction> getTransactions(User user){
    try (Session session = sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();

      return user.getTransactions();
    }

  }

  public static void updateTransactionList(User user, DefaultListModel<String> listModel, JList<String> transactionList, JScrollPane scrollPane){
    try (Session session = sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();

      session.refresh(user);

      List<org.example.Transaction> updatedTransactions = Service.getFilteredTransactions(user, false, false, false);
      listModel.clear();
      for (org.example.Transaction trans : updatedTransactions) {
        listModel.addElement(trans.toString());
      }
      transactionList.setModel(listModel);

      transaction.commit();
    }
  }

  public static List<org.example.Transaction> getFilteredTransactions(User user, boolean orderAscending, boolean sortByAmount, boolean sortByDateTime) {
    try (Session session = sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();

      CriteriaBuilder builder = session.getCriteriaBuilder();
      CriteriaQuery<org.example.Transaction> query = builder.createQuery(org.example.Transaction.class);
      Root<org.example.Transaction> root = query.from(org.example.Transaction.class);

      query.select(root).where(builder.equal(root.get("user"), user));

      if (sortByAmount) {
        if (orderAscending) {
          query.orderBy(builder.asc(root.get("amount")));
        } else {
          query.orderBy(builder.desc(root.get("amount")));
        }
      } else if (sortByDateTime) {
        if (orderAscending) {
          query.orderBy(builder.asc(root.get("transactionDateTime")));
        } else {
          query.orderBy(builder.desc(root.get("transactionDateTime")));
        }
      }

      Query<org.example.Transaction> q = session.createQuery(query);
      List<org.example.Transaction> transactions = q.getResultList();

      transaction.commit();
      return transactions;
    }
  }

  public static void changePassword(User user, String password){
    try (Session session = sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();

      byte[] saltByte = generateSalt();

      // convert saltByte to a String
      Base64.Encoder enc = Base64.getEncoder();
      String salt = enc.encodeToString(saltByte);

      // hash password
      String hashedPassword = null;
      try {
        hashedPassword = hashPassword(password, saltByte);
      } catch (Exception e) {
        e.getMessage();
      }

      user.setSalt(salt);
      user.setPassword(hashedPassword);
      session.merge(user);

      transaction.commit();
    }
  }

  public static byte[] generateSalt(){
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);

    return salt;
  }
  public static String hashPassword(String password, byte[] salt) throws Exception {
    KeySpec spec = new PBEKeySpec("password".toCharArray(), salt, 10000, 128);
    SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    byte[] hash = f.generateSecret(spec).getEncoded();

    Base64.Encoder enc = Base64.getEncoder();

    return enc.encodeToString(hash);
  }

}
