package sample.Database;



import sample.Controller.AddItemController;
import sample.Controller.AddItemFormController;
import sample.model.Task;
import sample.model.User;



import java.sql.*;

//handle the database connection.
public class DatabaseHandler {

    // database information
    private final String dbHost = "jdbc:mysql://localhost:";
    private final String dbName = "root";
    private final String dbUser = "todoapp?useTimezone=true&serverTimezone=UTC";
    private final String dbPassword = "123";
    private final String dbPort = "3306/";

    // the connection
    private Connection dbConnection;




    /**
     * connect to "myconnection" - todoapp.
     *  contractor that will get the connection automatic
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public DatabaseHandler() throws ClassNotFoundException, SQLException {
        //get the driver.
        Class.forName("com.mysql.cj.jdbc.Driver");

        //get the connection into dbConnection.
       dbConnection = DriverManager.getConnection
               (dbHost +dbPort +dbUser,dbName,dbPassword);
    }


    /**
     *
     * @param user Object Contain into all of the values for Sign up the user.
     */
    public void signUpUser(User user ){

        // the insert query
        String query = "insert into "+Const.USERS_TABLE+"("+Const.USERS_USERNAME+","
                +Const.USERS_PASSWORD+"," + Const.USERS_FIRSTNAME +"," + Const.USERS_LASTNAME
                +"," +Const.USERS_GENDER+")" + "VALUES(?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);

            // add the values into the database via Prepared Statement
            preparedStatement.setString(1,user.getUserName());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getFirstName());
            preparedStatement.setString(4,user.getLastName());
            preparedStatement.setString(5,user.getGender());

            //execute the query
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();

        }


    }

    /**
     * Checks if the user exists and if the Username & password are matching to Database
     * @param user getting a user object
     * @return resultSet - who contains if the Username & password matching to Database
     * @throws Exception
     */
    public ResultSet loginUser(User user) throws Exception {
        String query = "SELECT * FROM users WHERE BINARY username = '" + user.getUserName()
                + "' AND BINARY password = '" + user.getPassword() + "'";


        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);

        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();


        // if resultSet.next() == true  ->  Username & Password is correct
        return resultSet;
    }


    /**
     *
     * @param task getting a Task object and insert it into the Data-base
     * @throws SQLException may throw SQLException
     */
    public void insertTask(Task task) throws SQLException {

        //the Query - INSERT INTO tasks (task , description , dateCreated) VALUES (?,?,?,?).
        // add it only by the order
        String Insert = "INSERT INTO "+Const.TASKS_TABLE +"("+Const.TASKS_USERID+","+Const.TASKS_TASK+","+Const.TASKS_DESCRIPTION+","+
               Const.TASKS_DATE+")"+ "VALUES(?,?,?,?)";


       PreparedStatement preparedStatement =dbConnection.prepareStatement(Insert);
        //add the  values by the order

        preparedStatement.setInt(1,task.getUserID());
       preparedStatement.setString(2,task.getTask());
       preparedStatement.setString(3,task.getDescription());
       preparedStatement.setTimestamp(4,task.getDateCreated());

       //execute the query
        preparedStatement.execute();
        preparedStatement.close();
    }

    /**
     *
     * @param userId the current id of the user
     * @return the tasks number of the user
     * @throws SQLException
     */
    public int getTasksNumber(int userId) throws SQLException {
        String query ="SELECT COUNT(*) FROM "+Const.TASKS_TABLE +" WHERE userid = ?";

        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setInt(1,userId);

         ResultSet resultSet =  preparedStatement.executeQuery();



         while (resultSet.next()){
             return resultSet.getInt(1);
         }

return resultSet.getInt(1);

    }


    /**
     *
     * @param userId getting the tasks from database by the userid
     * @return return ResultSet that contain the Tasks tables
     * @throws SQLException may Throw
     */
    public ResultSet getUserTasks(int userId) throws SQLException {
        ResultSet resultSet = null;
        String Query = "SELECT * FROM " + Const.TASKS_TABLE +" WHERE "
                +Const.TASKS_USERID +" =?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(Query);
        preparedStatement.setInt(1,userId);


        resultSet = preparedStatement.executeQuery();


return resultSet;

    }

    public void deleteTask(int userId, int taskId) throws SQLException {
        String query = "DELETE FROM "+ Const.TASKS_TABLE +" WHERE "+ Const.TASKS_USERID
                +"=? AND "+Const.TASKS_ID +"=?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setInt(1,userId);
        preparedStatement.setInt(2,taskId);

        preparedStatement.execute();
        preparedStatement.close();
    }

    public ResultSet getUserDeatils(int userid) throws SQLException{

        String Query = "SELECT * FROM "+Const.USERS_TABLE + " WHERE "+ Const.USERS_ID +
                " = ?";
        PreparedStatement preparedstatement = dbConnection.prepareStatement(Query);
        preparedstatement.setInt(1,userid);

        ResultSet resultSet = preparedstatement.executeQuery();



        return resultSet;
    }

    /**
     *
     * @param userName
     * @return true or false
     * @throws SQLException
     */
    public boolean userExists(String userName) throws SQLException {
       boolean userExists = false;

        String Query = "SELECT * FROM "+Const.USERS_TABLE +" WHERE "+Const.USERS_USERNAME +" = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(Query);
        preparedStatement.setString(1,userName);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            userExists =true;
        }
        return userExists;
    }


    /**
     * Updating a Task
     * @param task Object --> getting the Task , Date , Description.
     * @throws SQLException may be thrown
     */
    public void updateTask(Task task) throws SQLException {
        String Query = "UPDATE "+Const.TASKS_TABLE +" SET "+Const.TASKS_TASK+"=?, " +Const.TASKS_DESCRIPTION+"=?, "+Const.TASKS_DATE+"=? WHERE "+Const.USERS_ID+"=? AND "+Const.TASKS_ID+"=?";

        PreparedStatement preparedStatement = dbConnection.prepareStatement(Query);
        preparedStatement.setString(1,task.getTask());
        preparedStatement.setString(2,task.getDescription());
        preparedStatement.setTimestamp(3,task.getDateCreated());
        preparedStatement.setInt(4,AddItemController.getUserId());
        preparedStatement.setInt(5,task.getTaskId());

        preparedStatement.execute();
        preparedStatement.close();


    }

}
