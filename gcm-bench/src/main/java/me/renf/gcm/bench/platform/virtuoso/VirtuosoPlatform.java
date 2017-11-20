package me.renf.gcm.bench.platform.virtuoso;

import me.renf.gcm.bench.Platform;
import me.renf.gcm.bench.conf.BenchConf;

import java.sql.*;

public class VirtuosoPlatform extends Platform{
    private final String DRIVER = "virtuoso.jdbc4.Driver";
    private Connection conn;
    private Statement stmt;

    public VirtuosoPlatform(BenchConf conf) {
        super(conf);
    }

    @Override
    public void init() {
        String url = "jdbc:virtuoso://localhost:1111/UID=dba/PWD=dba";
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadData() {

    }

    @Override
    public String query(String query) {
        String sql = "sparql " + query;
        StringBuilder result = new StringBuilder();
        try {
            ResultSet rs = stmt.executeQuery(sql);
            int columnCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                result.append(rs.getMetaData().getColumnName(i));
                result.append(" ");
            }
            result.append("\n");
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    result.append(rs.getString(i));
                    result.append(" ");
                }
                result.append("\n");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    @Override
    public void unload() {

    }

    @Override
    public void exit() {
        try {
            if (conn != null) conn.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
