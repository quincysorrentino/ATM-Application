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

