package org.github.s01ix.organiser;

import java.sql.*;

public class Organiser {
    public static void main(String[] args) {
        DBInit.init();
        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT class_id, name, description FROM classes");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.printf("#%d %s – %s%n",
                        rs.getInt("class_id"),
                        rs.getString("name"),
                        rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}