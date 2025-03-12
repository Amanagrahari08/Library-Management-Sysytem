package dao;

import model.Transaction;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private Connection conn;

    // ✅ Constructor with no arguments (gets connection from DatabaseConnection)
    public TransactionDAO() {
        this.conn = DatabaseConnection.getConnection();
    }

    // ✅ Issue a Book
    public boolean issueBook(Transaction transaction) {
        String sql = "INSERT INTO transactions (user_id, book_id, issue_date, due_date, status) VALUES (?, ?, ?, ?, 'ISSUED')";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getUserId());
            stmt.setInt(2, transaction.getBookId());
            stmt.setDate(3, transaction.getIssueDate());
            stmt.setDate(4, transaction.getDueDate());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Return a Book
    public boolean returnBook(int transactionId) {
        String sql = "UPDATE transactions SET return_date = CURDATE(), status = 'RETURNED' WHERE transaction_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
