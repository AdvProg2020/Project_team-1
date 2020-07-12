package bank;

import com.gilecode.yagson.YaGson;

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
        YaGson mapper = new YaGson();
        String addReceiptSql = "INSERT INTO " +
                "receipts (json, receiptType, description, money, sourceAccountID, destAccountID) " +
                "VALUES ('" + mapper.toJson(receipt) + "', '" + receipt.getReceiptType() + "', '" +
                receipt.getDescription() + "', " + receipt.getMoney() + ", " + receipt.getSourceAccountID() +
                ", " + receipt.getDestAccountID() + ")";
        executeUpdate(addReceiptSql);
        ResultSet resultSet = statement.executeQuery("SELECT Max(id) FROM receipts");
        int id = resultSet.getInt(1);
        closeStatementAndConnection();
        return id;
    }

    public synchronized AuthenticationToken getAuthTokenByUuid(String uuid) throws SQLException {
        createConnectionAndStatement();
        String selectByUuidSql = "SELECT * FROM authenticationTokens WHERE uuid = '" + uuid + "'";
        ResultSet resultSet = statement.executeQuery(selectByUuidSql);
        AuthenticationToken authenticationToken = null;
        if (resultSet.next()) {
            authenticationToken = new AuthenticationToken(resultSet.getInt("id"),
                    resultSet.getString("uuid"), resultSet.getInt("accountId"),
                    resultSet.getInt("expired"), resultSet.getLong("create_time"));
        }
        closeStatementAndConnection();
        return authenticationToken;
    }

    public synchronized void setAuthTokenExpire(String uuid) throws SQLException {
        createConnectionAndStatement();
        String expireAuthTokenSql = "UPDATE authenticationTokens SET expired = 1 " +
                "WHERE uuid = '" + uuid + "'";
        executeUpdate(expireAuthTokenSql);
        closeStatementAndConnection();
    }

    public synchronized BankAccount getAccountById(int accountId) throws SQLException {
        createConnectionAndStatement();
        String selectByUsernameSql = "SELECT * FROM accounts WHERE id=" + accountId;
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

    public synchronized Receipt getReceiptById(int id) throws SQLException {
        createConnectionAndStatement();
        String selectByIdSql = "SELECT * FROM receipts WHERE id = " + id;
        ResultSet resultSet = statement.executeQuery(selectByIdSql);
        Receipt receipt = null;
        if (resultSet.next()) {
            receipt = new Receipt(resultSet.getString("receiptType"), resultSet.getInt("money"),
                    resultSet.getInt("sourceAccountID"), resultSet.getInt("destAccountID"),
                    resultSet.getString("description"), resultSet.getInt("id"),
                    resultSet.getInt("paid"), resultSet.getString("json"));
        }
        closeStatementAndConnection();
        return receipt;
    }

    public synchronized StringBuilder getIdAllTransactionsJson(int accountId) throws SQLException {
        StringBuilder result = new StringBuilder("");
        String getTransactionsSql = "";
        createConnectionAndStatement();

        closeStatementAndConnection();
        return result;
    }

    public synchronized StringBuilder getTransactionsFromIdJson(int accountId) throws SQLException {
        StringBuilder result = new StringBuilder("");
        String getTransactionsSql = "";
        createConnectionAndStatement();

        closeStatementAndConnection();
        return result;
    }

    public synchronized StringBuilder getTransactionsToIdJson(int accountId) throws SQLException {
        StringBuilder result = new StringBuilder("");
        String getTransactionsSql = "";
        createConnectionAndStatement();

        closeStatementAndConnection();
        return result;
    }
}
