package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UI {

  private static JFrame frame;
  private static User user;
  private static DefaultListModel<String> listModel = new DefaultListModel<>();
  private static JList<String> transactionList = new JList<>(listModel);
  private static JScrollPane scrollPane;

  public static JPanel buildLoginPage(){
    if (frame == null){
      frame = new JFrame("ATM Application");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(600, 900);
    }

    JPanel loginScreenPanel = new JPanel();
    loginScreenPanel.setLayout(null);

    // Elements
    JLabel titleLabel = new JLabel("ATM Application");
    titleLabel.setBounds(100, 80, 400, 70);
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    titleLabel.setFont(new Font("Serif", Font.BOLD, 30));

    JLabel firstNameLabel = new JLabel("First Name: ");
    firstNameLabel.setBounds(65, 300, 135, 40);
    firstNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JTextField firstNameField = new JTextField();
    firstNameField.setBounds(240, 305, 295, 30);

    JLabel lastNameLabel = new JLabel("Last Name: ");
    lastNameLabel.setBounds(65, 355, 135, 40);
    lastNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JTextField lastNameField = new JTextField();
    lastNameField.setBounds(240, 360, 295, 30);

    JLabel passwordLabel = new JLabel("Password: ");
    passwordLabel.setBounds(65, 410, 135, 40);
    passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JPasswordField passwordField = new JPasswordField();
    passwordField.setBounds(240, 415, 295, 30);

    JButton loginButton = new JButton("Login");
    loginButton.setBounds(195, 490, 212, 39);
    loginButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        String firstNameValue = firstNameField.getText();
        String lastNameValue = lastNameField.getText();
        String passwordValue = passwordField.getText();

        if (firstNameValue.isEmpty() || lastNameValue.isEmpty() || passwordValue.isEmpty()){
          JOptionPane.showMessageDialog(loginScreenPanel, "Please fill all fields");
          return;
        }

        try {
          user = Service.login(firstNameValue, lastNameValue, passwordValue);
          frame.getContentPane().removeAll();
          frame.setContentPane(buildHomePage());
          frame.revalidate();
          frame.repaint();
        } catch (Exception e) {
          JOptionPane.showMessageDialog(frame, e.getMessage());
        }
      }
    });

    JButton registerButton = new JButton("Register");
    registerButton.setBounds(195, 539, 212, 39);
    registerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        frame.getContentPane().removeAll();
        frame.setContentPane(buildRegisterPage());
        frame.revalidate();
        frame.repaint();

      }
    });

    loginScreenPanel.add(titleLabel);
    loginScreenPanel.add(firstNameLabel);
    loginScreenPanel.add(firstNameField);
    loginScreenPanel.add(lastNameLabel);
    loginScreenPanel.add(lastNameField);
    loginScreenPanel.add(passwordLabel);
    loginScreenPanel.add(passwordField);
    loginScreenPanel.add(loginButton);
    loginScreenPanel.add(registerButton);

    frame.setContentPane(loginScreenPanel);
    frame.revalidate();
    frame.repaint();
    frame.setVisible(true);

    return loginScreenPanel;
  }

  private static JPanel buildRegisterPage(){
    JPanel panel = new JPanel();
    panel.setLayout(null);

    JLabel titleLabel = new JLabel("Register");
    titleLabel.setBounds(100, 80, 400, 70);
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    titleLabel.setFont(new Font("Serif", Font.BOLD, 30));

    JLabel firstNameLabel = new JLabel("First Name: ");
    firstNameLabel.setBounds(65, 300, 135, 40);
    firstNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JTextField firstNameField = new JTextField();
    firstNameField.setBounds(240, 305, 295, 30);

    JLabel lastNameLabel = new JLabel("Last Name: ");
    lastNameLabel.setBounds(65, 355, 135, 40);
    lastNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JTextField lastNameField = new JTextField();
    lastNameField.setBounds(240, 360, 295, 30);

    JLabel dobLabel = new JLabel("DOB (YYYY-MM-DD): ");
    dobLabel.setBounds(47, 410, 135, 40);
    JSpinner dobSpinner = new JSpinner(createSpinnerDateModel());
    dobSpinner.setBounds(240, 415, 295, 30);
    dobSpinner.setEditor(new JSpinner.DateEditor(dobSpinner, "yyyy-MM-dd"));

    JLabel passwordLabel = new JLabel("Password: ");
    passwordLabel.setBounds(65, 465, 135, 40);
    passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JPasswordField passwordField = new JPasswordField();
    passwordField.setBounds(240, 470, 295, 30);

    JButton registerButton = new JButton("Register");
    registerButton.setBounds(195, 545, 212, 39);
    registerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        String firstNameValue = firstNameField.getText();
        String lastNameValue = lastNameField.getText();
        Date dobValue = (Date) dobSpinner.getValue();
        LocalDate dobLocalDate = dobValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String passwordValue = passwordField.getText();

        if (firstNameValue.isEmpty() || lastNameValue.isEmpty() || passwordValue.isEmpty()){
          JOptionPane.showMessageDialog(frame, "Please fill all fields");
          return;
        }

        try {
          Service.registerUser(firstNameValue, lastNameValue, dobLocalDate, passwordValue);
        } catch (Exception e) {
          JOptionPane.showMessageDialog(frame, "User already exists");
        }

        frame.getContentPane().removeAll();
        frame.setContentPane(buildLoginPage());
        frame.revalidate();
        frame.repaint();
      }
    });

    JButton backButton = new JButton("Back");
    backButton.setBounds(195, 594, 212, 39);
    backButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        frame.getContentPane().removeAll();
        frame.setContentPane(buildLoginPage());
        frame.revalidate();
        frame.repaint();
      }
    });

    panel.add(titleLabel);
    panel.add(firstNameLabel);
    panel.add(firstNameField);
    panel.add(lastNameLabel);
    panel.add(lastNameField);
    panel.add(dobLabel);
    panel.add(dobSpinner);
    panel.add(passwordLabel);
    panel.add(passwordField);
    panel.add(registerButton);
    panel.add(backButton);

    return panel;
  }

  private static JPanel buildHomePage(){
    JPanel panel = new JPanel();
    panel.setLayout(null);

    JLabel namePlate = new JLabel(user.getFirstName() + " " + user.getLastName());
    namePlate.setBounds(17, 22, 283, 51);
    namePlate.setFont(new Font("Serif", Font.BOLD, 30));

    JButton settingsButton = new JButton("Settings");
    settingsButton.setBounds(470, 23, 125, 50);
    settingsButton.setBorder(null);
    settingsButton.setMargin(new Insets(0, 0, 0, 0));
    settingsButton.setContentAreaFilled(false);
    settingsButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        frame.getContentPane().removeAll();
        frame.setContentPane(buildSettingsPage());
        frame.revalidate();
        frame.repaint();
      }
    });

    JLabel balanceDisplay = new JLabel("Balance: $" + String.format("%.2f", user.getBalance()));
    balanceDisplay.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 3, true), new EmptyBorder(5, 10, 5, 5)));
    balanceDisplay.setBounds(25, 150, 295, 40);

    JButton transferButton = new JButton("Transfer");
    transferButton.setBounds(25, 200, 295, 40);
    transferButton.setBackground(Color.gray);
    transferButton.setForeground(Color.black);
    transferButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JDialog transferPanel = createTransferPanel(balanceDisplay);
        transferPanel.setLocationRelativeTo(frame);
        transferPanel.setVisible(true);
      }
    });

    JLabel filterLabel = new JLabel("Filter");
    filterLabel.setFont(new Font("Ariel", Font.BOLD, 25));
    filterLabel.setBounds(490, 285, 75, 20);

    JPanel filterPanel = buildFilterPanel();
    filterPanel.setBounds(25, 315, 535, 65);

    Service.updateTransactionList(user, listModel, transactionList, scrollPane);

    scrollPane = new JScrollPane(transactionList);
    scrollPane.setBounds(25, 390, 535, 200);

    panel.add(namePlate);
    panel.add(settingsButton);
    panel.add(balanceDisplay);
    panel.add(transferButton);
    panel.add(filterLabel);
    panel.add(filterPanel);
    panel.add(scrollPane);

    return panel;
  }

  private static SpinnerDateModel createSpinnerDateModel(){
    // Create the SpinnerDateModel
    Calendar calendar = Calendar.getInstance();
    Date initDate = calendar.getTime(); // today's date
    calendar.add(Calendar.YEAR, -100); // 100 years before today
    Date earliestDate = calendar.getTime();
    calendar.add(Calendar.YEAR, 200); // 100 years after today
    Date latestDate = calendar.getTime();

    return new SpinnerDateModel(initDate, earliestDate, latestDate, Calendar.DAY_OF_MONTH);
  }

  private static JDialog createTransferPanel(JLabel homeBalanceLabel){
    JDialog dialog = new JDialog(frame);
    dialog.setLayout(null);
    dialog.setSize(300, 200);

    JLabel balanceLabel = new JLabel("Balance: " + String.format("%.2f", user.getBalance()));
    balanceLabel.setBounds(15, 10, 260, 30);

    JTextField amountField = new JTextField("Set Amount");
    amountField.setBounds(15, 50, 260, 30);

    JButton withdrawButton = new JButton("Withdraw");
    withdrawButton.setBounds(50, 90, 200, 20);
    withdrawButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (amountField.getText().isEmpty()){
          JOptionPane.showMessageDialog(dialog, "Please input a value to withdraw or deposit");
          return;
        }

        double amount;
        try {
          amount = Double.parseDouble(amountField.getText());
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(dialog, "Invalid amount format");
          return;
        }

        if (amount > user.getBalance()) {
          JOptionPane.showMessageDialog(dialog, "Withdrawal exceeds balance");
          return;
        }

        Service.withdraw(user, amount);

        Service.updateTransactionList(user, listModel, transactionList, scrollPane);

        balanceLabel.setText("Balance: " + String.format("%.2f", user.getBalance()));
        homeBalanceLabel.setText("Balance: $" + String.format("%.2f", user.getBalance()));

      }
    });

    JButton depositButton = new JButton("Deposit");
    depositButton.setBounds(50, 120, 200, 20);
    depositButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (amountField.getText().isEmpty()){
          JOptionPane.showMessageDialog(dialog, "Please input a value to withdraw or deposit");
          return;
        }

        double amount;
        try {
          amount = Double.parseDouble(amountField.getText());
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(dialog, "Invalid amount format");
          return;
        }

        Service.deposit(user, amount);

        Service.updateTransactionList(user, listModel, transactionList, scrollPane);

        balanceLabel.setText("Balance: " + String.format("%.2f", user.getBalance()));
        homeBalanceLabel.setText("Balance: $" + String.format("%.2f", user.getBalance()));

      }
    });

    dialog.add(balanceLabel);
    dialog.add(amountField);
    dialog.add(withdrawButton);
    dialog.add(depositButton);

    return dialog;
  }

  private static JPanel buildFilterPanel(){
    JPanel panel = new JPanel();
    panel.setLayout(null);
    panel.setBackground(Color.LIGHT_GRAY);

    JLabel orderLabel = new JLabel("Order:");
    orderLabel.setBounds(5, 5, 40, 20);

    String[] options = {"Ascending", "Descending"};
    JComboBox<String> orderDropdown = new JComboBox<>(options);
    orderDropdown.setBounds(45, 5, 95, 20);

    JLabel amountCheckBoxLabel = new JLabel("Amount");
    amountCheckBoxLabel.setBounds(250, 5, 50, 20);

    JCheckBox amountCheckBox = new JCheckBox();
    amountCheckBox.setBackground(Color.LIGHT_GRAY);
    amountCheckBox.setBounds(305, 5, 20, 20);

    JLabel dateTimeLabel = new JLabel("Date/Time");
    dateTimeLabel.setBounds(435, 5, 60, 20);

    JCheckBox dateTimeCheckBox = new JCheckBox();
    dateTimeCheckBox.setBackground(Color.LIGHT_GRAY);
    dateTimeCheckBox.setBounds(500, 5, 20, 20);

    amountCheckBox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (amountCheckBox.isSelected()) {
          dateTimeCheckBox.setSelected(false);
        }
      }
    });

    dateTimeCheckBox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (dateTimeCheckBox.isSelected()) {
          amountCheckBox.setSelected(false);
        }
      }
    });

    JButton applyButton = new JButton("Apply");
    applyButton.setBounds(437, 35, 80, 20);
    applyButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        boolean orderAscending = orderDropdown.getSelectedItem().equals("Ascending");
        boolean sortByAmount = amountCheckBox.isSelected();
        boolean sortByDateTime = dateTimeCheckBox.isSelected();

        List<org.example.Transaction> filteredTransactions = Service.getFilteredTransactions(user, orderAscending, sortByAmount, sortByDateTime);
        listModel.clear();
        for (org.example.Transaction transaction : filteredTransactions) {
          listModel.addElement(transaction.toString());
        }
        transactionList.setModel(listModel);
      }
    });

    panel.add(orderLabel);
    panel.add(orderDropdown);
    panel.add(amountCheckBoxLabel);
    panel.add(amountCheckBox);
    panel.add(dateTimeLabel);
    panel.add(dateTimeCheckBox);
    panel.add(applyButton);

    return panel;
  }

  private static JPanel buildSettingsPage(){
    JPanel panel = new JPanel();
    panel.setLayout(null);

    JLabel settingsHeader = new JLabel("Settings");
    settingsHeader.setBounds(17, 22, 283, 51);
    settingsHeader.setFont(new Font("Serif", Font.BOLD, 35));

    JButton backButton = new JButton("Back");
    backButton.setBounds(480, 40, 80, 20);
    backButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        frame.getContentPane().removeAll();
        frame.setContentPane(buildHomePage());
        frame.revalidate();
        frame.repaint();
      }
    });

    JLabel personalInfoLabel = new JLabel("Personal Information");
    personalInfoLabel.setBounds(35, 110, 300, 25);
    personalInfoLabel.setFont(new Font("Serif", Font.BOLD, 25));

    JLabel firstNameLabel = new JLabel("First Name");
    firstNameLabel.setBounds(35, 150, 200, 20);
    firstNameLabel.setFont(new Font("Serif", Font.BOLD, 15));

    JLabel firstName = new JLabel(user.getFirstName());
    firstName.setBounds(35, 180, 200, 20);
    firstName.setOpaque(true);
    firstName.setBackground(Color.LIGHT_GRAY);

    JLabel lastNameLabel = new JLabel("Last Name");
    lastNameLabel.setBounds(35, 210, 200, 20);
    lastNameLabel.setFont(new Font("Serif", Font.BOLD, 15));

    JLabel lastName = new JLabel(user.getLastName());
    lastName.setBounds(35, 240, 200, 20);
    lastName.setOpaque(true);
    lastName.setBackground(Color.LIGHT_GRAY);

    JLabel dobLabel = new JLabel("Date of Birth");
    dobLabel.setBounds(35, 270, 200, 20);
    dobLabel.setFont(new Font("Serif", Font.BOLD, 15));

    JLabel dob = new JLabel(user.getDob().toString());
    dob.setBounds(35, 300, 200, 20);
    dob.setOpaque(true);
    dob.setBackground(Color.LIGHT_GRAY);

    JLabel changePasswordLabel = new JLabel("Change Password");
    changePasswordLabel.setBounds(35, 350, 200, 30);
    changePasswordLabel.setFont(new Font("Serif", Font.BOLD, 25));

    JLabel newPasswordLabel = new JLabel("New Password");
    newPasswordLabel.setBounds(35, 390, 200, 20);

    JPasswordField newPasswordTextField = new JPasswordField();
    newPasswordTextField.setBounds(35, 420, 200, 20);

    JLabel confirmPasswordLabel = new JLabel("Confirm Password");
    confirmPasswordLabel.setBounds(35, 450, 200, 20);

    JPasswordField confirmPasswordTextField = new JPasswordField();
    confirmPasswordTextField.setBounds(35, 480, 200, 20);

    JButton confirmButton = new JButton("Confirm");
    confirmButton.setBounds(50, 520, 170, 20);
    confirmButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String newPassword = newPasswordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();

        if (newPassword.isEmpty() || confirmPassword.isEmpty()){
          JOptionPane.showMessageDialog(panel, "Please fill both fields");
          return;
        }

        if (!newPassword.equals(confirmPassword)){
          JOptionPane.showMessageDialog(panel, "New passwords don't match");
          return;
        }

        Service.changePassword(user, newPassword);

        frame.getContentPane().removeAll();
        frame.setContentPane(buildHomePage());
        frame.revalidate();
        frame.repaint();
      }
    });

    JLabel signOutLabel = new JLabel("Sign Out");
    signOutLabel.setBounds(35, 570, 200, 25);
    signOutLabel.setFont(new Font("Serif", Font.BOLD, 25));

    JButton signOutButton = new JButton("Sign out");
    signOutButton.setBounds(35, 610, 200, 20);
    signOutButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        user = null;

        frame.getContentPane().removeAll();
        frame.setContentPane(buildLoginPage());
        frame.revalidate();
        frame.repaint();
      }
    });

//    JLabel deleteAccountLabel = new JLabel("Delete Account");
//    deleteAccountLabel.setBounds(35, 640, 200, 25);
//    deleteAccountLabel.setFont(new Font("Serif", Font.BOLD, 25));
//
//    JButton deleteAccountButton = new JButton("Delete");
//    deleteAccountButton.setBounds(35, 680, 200, 20);
//    deleteAccountButton.addActionListener(new ActionListener() {
//      @Override
//      public void actionPerformed(ActionEvent e) {
//        Service.removeUser(user);
//
//        user = null;
//
//        frame.getContentPane().removeAll();
//        frame.setContentPane(buildLoginPage());
//        frame.revalidate();
//        frame.repaint();
//      }
//    });

    panel.add(personalInfoLabel);
    panel.add(settingsHeader);
    panel.add(backButton);
    panel.add(firstNameLabel);
    panel.add(firstName);
    panel.add(lastNameLabel);
    panel.add(lastName);
    panel.add(dobLabel);
    panel.add(dob);
    panel.add(changePasswordLabel);
    panel.add(newPasswordLabel);
    panel.add(newPasswordTextField);
    panel.add(confirmPasswordLabel);
    panel.add(confirmPasswordTextField);
    panel.add(confirmButton);
    panel.add(signOutLabel);
    panel.add(signOutButton);
//    panel.add(deleteAccountLabel);
//    panel.add(deleteAccountButton);

    return panel;
  }


}
