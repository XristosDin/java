package db;

import db.DBConnection;
import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println(" Επιτυχής σύνδεση στη βάση!");
        } catch (Exception e) {
            System.out.println(" Σφάλμα σύνδεσης: " + e.getMessage());
        }
    }
}
