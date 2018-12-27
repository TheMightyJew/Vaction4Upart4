package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AVacationdatabaseTable {

    protected enum tableNameEnum {Users_table, Vacations_Table, Purchases_Table, Flights_table, FlightsToVacations_Table, PurchaseRequests_Table, Payments_Table, VisaPayments_Table, Paypalpayments_Table;}

    protected void insertQuery(String table_name, Class<? extends Enum<?>> tableEnum, String[] insert_values) throws SQLException {
        String[] field_array = getNames(tableEnum);
        String sql = "INSERT INTO " + table_name + "(";
        for (int i = 0; i < field_array.length; i++) {
            sql += field_array[i] + ",";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += ") VALUES(";
        for (int i = 0; i < field_array.length; i++) {
            if (i != field_array.length - 1)
                sql += "?,";
            else
                sql += "?)";
        }

        Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        for (int i = 0; i < field_array.length; i++) {
            if (i >= insert_values.length) {
                pstmt.setString(i + 1, "");
            } else {
                pstmt.setString(i + 1, insert_values[i]);
            }

        }
        pstmt.executeUpdate();

    }//insert query pattern

    protected static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + "EveryVaction4U.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    protected static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
    }//converting enum to String array

    protected List<String[]> selectQuery(String table_name, String where_condition) {
        String sql = "SELECT *" + " FROM " + table_name + " WHERE " + where_condition;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            List<String[]> table;
            table = specificSelectQuery(sql);
            return table;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }//select query pattern

    protected void updateQuery(String table_name, Class<? extends Enum<?>> tableEnum, String[] update_values, String where_condition) throws SQLException {
        String[] fields_array = getNames(tableEnum);
        String sql = "UPDATE " + table_name + " SET ";
        for (int i = 0; i < fields_array.length; i++) {
            sql += fields_array[i] + " = ? ,";
        }
        sql = sql.substring(0, sql.length() - 1) + " ";
        sql += "WHERE " + where_condition;

        Connection conn = this.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // set the corresponding param
        for (int j = 0; j < fields_array.length; j++) {
            if (j >= update_values.length) {
                pstmt.setString(j + 1, "");
            } else {
                pstmt.setString(j + 1, update_values[j]);
            }

        }
        // update
        pstmt.executeUpdate();

    }//update query pattern

    protected void deleteQuery(String table_name, String where_condition) {
        String sql = "DELETE FROM " + table_name + " WHERE " + where_condition;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }//delete query pattern

    protected static List<String[]> specificSelectQuery(String sql)
    {
        List<String[]> table = new ArrayList<>();
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            // loop through the result set
            int nCol = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                String[] row = new String[nCol];
                for (int iCol = 1; iCol <= nCol; iCol++) {
                    Object obj = rs.getObject(iCol);
                    row[iCol - 1] = (obj == null) ? null : obj.toString();
                }
                table.add(row);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return table;
    }
}
