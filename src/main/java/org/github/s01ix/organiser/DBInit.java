package org.github.s01ix.organiser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public final class DBInit {
    private static final String SCHEMAFILEPATH = "/schema.sql";
    public static void init(){
        try (Connection con = DBManager.getConnection()) {
            if(isAlreadyInitialize(con)) {return;}
            runSqlScript(con, SCHEMAFILEPATH);

        } catch (Exception  e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isAlreadyInitialize(Connection con) throws SQLException {
        try(var rs = con.getMetaData().getTables(null, null, "users", null)){
            return rs.next();
        }
    }
    private static void runSqlScript(Connection con, String resourcePath) throws Exception {
        var in = DBInit.class.getResourceAsStream(resourcePath);
        if (in == null) {
            throw new IllegalStateException("Wrong filepath" + resourcePath);
        }
        try (
                var br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                var st = con.createStatement()) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("--") || line.isEmpty()) continue;
                sb.append(line).append(' ');
                while (sb.indexOf(";") != -1) {
                    int index = sb.indexOf(";");
                    String sql = sb.substring(0, index).trim();
                    sb.delete(0, index + 1);
                    if (!sql.isEmpty()) {
                        System.out.println("Sql Statemant" + sql); //debug
                        st.execute(sql);
                    }
                }
            }
            String sql = sb.toString().trim();
            if (!sql.isEmpty()) {
                System.out.println("Sql Statemant" + sql); //debug
                st.execute(sql);
            }
        }
    }
    private DBInit(){}
}
