package bank;

import java.io.File;
import java.sql.*;

public class BankDataBase {
    private Connection connection;
    private Statement statement;

    public BankDataBase() {
        if (!new File("database.db").exists()) {
            try {
                createConnectionAndStatement();
                createTables();
                closeStatementAndConnection();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error: Server couldn't create database.db file. Server exiting now ...");
                System.exit(1);
            }
        }
    }

    private synchronized void createTables() throws SQLException {
        createConnectionAndStatement();
        String accountsTableSql = "CREATE TABLE accounts (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "    username TEXT NOT NULL UNIQUE, \n" +
                "    password TEXT NOT NULL, \n" +
                "    firstName TEXT NOT NULL, \n" +
                "    lastName TEXT NOT NULL, \n" +
                "    balance INTEGER DEFAULT 0 NOT NULL\n" +
                ")";
        executeUpdate(accountsTableSql);
        String authenticationTokensTableSql = "CREATE TABLE authenticationTokens (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "    uuid TEXT NOT NULL UNIQUE, \n" +
                "    accountId INTEGER NOT NULL, \n" +
                "    expired INTEGER DEFAULT 0 NOT NULL, \n" +
                "    create_time INTEGER NOT NULL, \n" +
                "    FOREIGN KEY(accountId) REFERENCES accounts(id) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ")";
        executeUpdate(authenticationTokensTableSql);
        String receiptsTableSql = "CREATE TABLE receipts (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "    json TEXT NOT NULL, \n" +
                "    receiptType TEXT NOT NULL, \n" +
                "    description TEXT, \n" +
                "    money INTEGER NOT NULL, \n" +
                "    sourceAccountID INTEGER NOT NULL, \n" +
                "    destAccountID INTEGER NOT NULL, \n" +
                "    paid INTEGER DEFAULT 0 NOT NULL, \n" +
                "    FOREIGN KEY(sourceAccountID) REFERENCES accounts(id) ON DELETE NO ACTION ON UPDATE CASCADE\n" +
                "    FOREIGN KEY(destAccountID) REFERENCES accounts(id) ON DELETE NO ACTION ON UPDATE CASCADE\n" +
                ")";
        executeUpdate(receiptsTableSql);
        closeStatementAndConnection();
    }

    private synchronized void createConnectionAndStatement() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        statement = connection.createStatement();
    }

    private synchronized void closeStatementAndConnection() throws SQLException {
        statement.close();
        connection.close();
    }

    private synchronized void executeUpdate(String command) throws SQLException {
        createConnectionAndStatement();
        statement.executeUpdate(command);
        closeStatementAndConnection();
    }

    public synchronized void getAccounts() throws SQLException {
        createConnectionAndStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("firstname");
            System.out.println(id + "    " + firstName);
        }
        closeStatementAndConnection();
    }

    public synchronized int addAccount(BankAccount bankAccount) throws SQLException {
        String createAccountSql = "INSERT INTO accounts (username, password, firstName, lastName)\n" +
                "VALUES ('" + bankAccount.getUsername() + "', '" + bankAccount.getPassword() + "', '" +
                bankAccount.getFirstName() + "', '" +  bankAccount.getLastName() + "')";
        executeUpdate(createAccountSql);
        return getAccount(bankAccount.getUsername()).getId();
    }

    public synchronized BankAccount getAccount(String username) throws SQLException{
        createConnectionAndStatement();
        String selectByUsernameSql = "SELECT * FROM accounts WHERE username='" + username + "'";
        ResultSet resultSet = statement.executeQuery(selectByUsernameSql);
        BankAccount bankAccount = null;
        if (resultSet.next()) {
            bankAccount = new BankAccount(
                    resultSet.getInt("id"), resultSet.getString("username"),
                    resultSet.getString("password"), resultSet.getString("firstname"),
                    resultSet.getString("lastname"), resultSet.getInt("balance"));
        }
        closeStatementAndConnection();
        return bankAccount;
    }

    public synchronized void addAuthenticationToken(AuthenticationToken authToken) throws SQLException {
        createConnectionAndStatement();
        String addAuthTokenSql = "INSERT INTO authenticationTokens (uuid, accountId, create_time)" +
                "VALUES ('" + authToken.getUuid() + "', " + authToken.getAccountId() + ", "
                + authToken.getCreateTime() + ")";
        executeUpdate(addAuthTokenSql);
        closeStatementAndConnection();
    }

    public synchronized int addReceipt(Receipt receipt) throws SQLException {
        createConnectionAndStatement();
        ResultSet resultSet = statement.executeQuery("SELECT Max(id) FROM receipts");
        int id = resultSet.getInt(1);
        closeStatementAndConnection();
        return id;
    }

    public synchronized AuthenticationToken getAuthTokenByUuid(String uuid) throws SQLException {
        createConnectionAndStatement();
        String selectByUuidSql = "SELECT * FROM";
        ResultSet resultSet = statement.executeQuery(selectByUuidSql);
        AuthenticationToken authenticationToken = null;
        if (resultSet.next()) {
            //authenticationToken = new AuthenticationToken()
        }
        closeStatementAndConnection();
        return authenticationToken;
    }
}
