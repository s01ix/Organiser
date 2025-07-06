package org.github.s01ix.organiser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnectionCheck {

    public static void main(String[] args) {
        System.out.println("Test connection with db…");

        try (Connection con = DBManager.getConnection()) {

            boolean ok = con.isValid(2);
            System.out.println(ok ? "Succesfully connected" : "Connection error");

            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery("SELECT 1")) {

                if (rs.next()) {
                    System.out.println("SELECT 1 => " + rs.getInt(1));
                }
            }

        } catch (Exception e) {
            System.err.println("Connection error");
            e.printStackTrace();
        }
    }
}