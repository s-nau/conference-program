package Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class EmployeeManager extends PersonManager {

    // This account is created by organizer

    private ArrayList<String> requestIds;


    // dictionary of all employees; this is under the theme of creating a restricted chat for employees with employees only
    protected Map<String, Employee> usernameToEmployee = new HashMap<String, Employee>();

    protected ArrayList<String> employeeList = new ArrayList<>();


    public EmployeeManager(Map<String, Person> usernameToPerson, Map<String, Person> idToPerson) {

        super(usernameToPerson, idToPerson);
        this.requestIds = new ArrayList<String>();

    }

    /**
     *
     * @param userID
     * @return a list of all employees working the convention
     */
    public ArrayList getEmployeeList(String userID){
        return employeeList;

    }

    @Override
    public boolean createAccount(String name, String username, String password, String email) {
        if (!usernameToPerson.containsKey(username)) {
            Employee newEmployee = new Employee(name, username, password, email);
            usernameToPerson.put(username, newEmployee);
            idToPerson.put(newEmployee.getID(), newEmployee);
            usernameToEmployee.put(username, newEmployee);
            employeeList.add(newEmployee.getID());
            return true;
        }
        return false;
    }

    /**
     *
     * @param userID of the employee, should Organizer wish to cancel employee account wth Id
     * @return true if employee account was cancelled
     */
    public boolean cancelEmployeeAccount(String userID){
        if(idToPerson.containsKey((userID))){
            String username = getPerson(userID).getUsername();
            int typeUser = getPerson(userID).typePerson;
            if(typeUser == 4) {
                usernameToPerson.remove(username);
                idToPerson.remove(userID);
                usernameToEmployee.remove(username);
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param username of the employee, should Organizer wish to cancel employee account with username
     * @return true if employee account was cancelled
     */
    public boolean cancelEmployeeAccountByUsername(String username){
        if(usernameToPerson.containsKey(getPerson(username))){
            String userID = getPerson(username).getID();
            int typeUser = getPerson(userID).typePerson;
            if(typeUser == 4) {
                usernameToPerson.remove(username);
                idToPerson.remove(userID);
                usernameToEmployee.remove(username);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a chat
     * @param currentID of employee
     * @param chatID of chat associated with announcement for which event
     */
    public void addAnChat(String currentID, String chatID) {
        Employee employee = (Employee)getPerson(currentID);
        employee.addAnChat(chatID);
    }

    /**
     * Removes a chat
     * @param currentID of employee
     * @param chatID of chat associated with announcement for which event
     */
    public void removeAnChat(String currentID, String chatID) {
        Employee employee = (Employee)getPerson(currentID);
        employee.addAnChat(chatID);
    }

    /**
     *
     * @return map of employee's username to employee entity, which has all employee information
     */
    public Map<String, Employee> getUsernameToEmployee(){
        return usernameToEmployee;
    }

    /**
     *
     * @param username of employee
     * @return the full name of the employee
     */
    public String getFullName(String username) {
        Employee employee = (Employee) getPerson(username);
        return employee.getFullName();

    }

    /**
     *
     * @param userID of employee
     * @return the full name of the employee
     */
    public String getFullNameWithID(String userID){
        Employee employee = (Employee) getPerson(userID);
        return employee.getFullName();
    }

    /**
     *
     * @param userID of the employee
     * @return the username of the employee
     */
    public String getUserName(String userID) {
        Employee employee = (Employee) getPerson(userID);
        return employee.getUsername();

    }

    /**
     *
     * @param userName of the employee
     * @return the employee ID
     */
    public String getEmployeeID(String userName) {
        Employee employee = (Employee) getPerson(userName);
        return employee.getID();
    }

    /**
     *
     * @return a list of string in brackets, each of which contains employee info:
     *              this is in order to see a more visual representation of list of all employees
     */
    public ArrayList<String> getAllEmployees() {
        ArrayList<String> nameUsernamePassword = new ArrayList<>();
        Map<String, Employee> map = getUsernameToEmployee();

        for (Employee employee : map.values()) {

                nameUsernamePassword.add("(" + employee.getFullName() + "; " + employee.username + "; " + employee.getID() + ")");
        }
        return nameUsernamePassword;
    }

    /**
     *
     * @param userID of employee
     * @return list of all request entity ids on the request board
     */
    public ArrayList<String> getRequestsIDs(String userID){
        return requestIds;
    }


    /**
     *
     * @param userID of employee
     * @return array list for the employee with announcement chats ids
     */
    public ArrayList getAnChats(String userID){
        Employee employee = (Employee) getPerson(userID);
        return employee.getAnChatList();
    }

    // Getting a list of all talks at the conference is available from the EmployeeEventController

     }


