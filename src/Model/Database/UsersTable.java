package Model.Database;

import Model.Model;
import Model.Objects.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

public class UsersTable extends AVacationdatabaseTable {

    public enum UsersfieldNameEnum {
        Username, Password, Birthday, FirstName, LastName, City, State
    }

    public UsersTable(String databaseName) {
        // SQLite connection string
        Connection c = null;
        Statement stmt = null;
        String url = "jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName;

        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + Configuration.loadProperty("directoryPath") + databaseName);
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Users_Table (\n"
                    + "Username text PRIMARY KEY,\n"
                    + "	Password text NOT NULL,\n"
                    + "	Birthday text NOT NULL,\n"
                    + "	FirstName text NOT NULL,\n"
                    + "	LastName text NOT NULL,\n"
                    + "	City text NOT NULL,\n"
                    + "	State text NOT NULL\n"
                    + ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser(String Username_val) {
        List<String[]> result = selectQuery("Users_Table", UsersfieldNameEnum.Username + " = '" + Username_val + "'");
        if (result.size() != 1)
            return null;
        else {
            String[] ans = result.get(0);
            LocalDate bDate = LocalDate.parse(ans[2]);
            return new User(ans[0], ans[1], bDate, ans[3], ans[4], ans[5], ans[6]);
        }
    }

    public boolean deleteUser(String username) {
        try {
            deleteQuery(tableNameEnum.Users_table.toString(), UsersfieldNameEnum.Username + " = '" + username + "'");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean userExist(String username) {
        return getUser(username) != null;
    }


    public boolean createUser(User user) {
        try {
            String[] values = {user.getUsername(), user.getPassword(), user.getBirth_Date().toString(), user.getFirst_Name(), user.getLast_Name(), user.getCity(), user.getCountry()};
            insertQuery("Users_Table", UsersfieldNameEnum.class, values);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateUserInfo(String username, User user) {
        try {
            String Birthday_val = user.getBirth_Date().toString();
            String[] values = {user.getUsername(), user.getPassword(), Birthday_val, user.getFirst_Name(), user.getLast_Name(), user.getCity(), user.getCountry()};
            updateQuery(Model.tableNameEnum.Users_table.name(), UsersfieldNameEnum.class, values, Model.UsersfieldNameEnum.Username.toString() + " = '" + username + "'");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkPassword(String Username_val, String Password_val) {
        List<String[]> result = selectQuery("Users_Table", UsersfieldNameEnum.Username + " = '" + Username_val + "'");
        if (result.size() != 1)
            return false;
        else return result.get(0)[1].equals(Password_val);
    }
}
