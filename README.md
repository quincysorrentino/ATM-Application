# ATM Application

The ATM Application is a Java-based application that simulates an ATM (Automated Teller Machine). It allows users to create an account, make withdrawals, deposit funds, and keep track of their transactions.

## Security
A primary focus of this project is the secure storage of user information, including passwords. One of the strategies employed is the addition of a “salt” to user passwords before hashing them. The “salt” is a randomly generated string added to the user’s password prior to hashing. This process helps protect against rainbow table attacks and can slow down brute force attacks. It also ensures that even if multiple users have identical passwords, their hashes will be unique.

All passwords are hashed using a method called PBKDF2, which stands for Password-Based Key Derivation Function 2. This method uses a technique known as HMAC (Keyed-Hash Message Authentication Code), in conjunction with the SHA-1 hashing algorithm. When a user attempts to log in to their account, their inputted password is salted with the stored salt for that user, then hashed and compared to the stored hash in the database. This means that even if the database is compromised, users’ plaintext passwords will remain hidden, thereby protecting users who reuse passwords across accounts.

## Setup and Running 

1. **Clone the Repository:**
   ```
   git clone https://github.com/quincysorrentino/ATM Application.git
   cd ATM Application
   ```
   
2. **Database Setup:**

   - Install MySQL on your local machine
   - Create a local database for the application
   - Create a table named "users" with seven columns:
     - user_id (bigint)
     - firstName (varchar(100))
     - lastName (varchar(100))
     - dob (date)
     - salt (varchar(100))
     - password (varchar(100))
     - balance (double)
   - Create a second table named "transactions" with five columns:
     - amount (double)
     - transaction_dateTime (datetime)
     - transaction_id (bigint)
     - transaction_type (varchar(45))
     - user_id (bigint)

3. **Update Hibernate configuration:**
   - Open the `hibernate.cfg.xml` file in the `src/main/resources` directory.
   - Update the following properties with your database connection details:


   ```
   <!-- url -->
   <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/YOUR DB NAME</property>
   <!-- username -->
   <property name="hibernate.connection.username">YOUR DB USERNAME</property>
   <!-- password -->
   <property name="hibernate.connection.password">YOUR DB PASSWORD</property>
   ```

    -   Ensure that the JDBC URL, username, and password match your local MySQL setup.

## Gallery

| ![Screenshot 2024-07-05 210439](https://github.com/quincysorrentino/ATM-Application/assets/140214071/0c1a4492-8f54-4667-b8af-f2fe84747345) | ![Screenshot 2024-07-05 210713](https://github.com/quincysorrentino/ATM-Application/assets/140214071/0199841c-809b-4ce2-b8b6-582d56ed9657) | ![Screenshot 2024-07-05 210908](https://github.com/quincysorrentino/ATM-Application/assets/140214071/3014ea8d-224b-47a3-86d0-812935f4adb9) |
|:---:|:---:|:---:|
| Register Page | Login Page | Home Page |

| ![Screenshot 2024-07-05 210938](https://github.com/quincysorrentino/ATM-Application/assets/140214071/d4ef8d3d-51c4-4649-8a7b-c754a0f74585) | ![Screenshot 2024-07-05 211113](https://github.com/quincysorrentino/ATM-Application/assets/140214071/ab9487f5-93e9-40e6-9e34-ae0aa74573db) | ![Screenshot 2024-07-05 211319](https://github.com/quincysorrentino/ATM-Application/assets/140214071/6ce4fb93-0d63-4027-8398-dd6a8a282650) |
|:---:|:---:|:---:|
| Transfer Popup | Transfer Error | Settings Page |


