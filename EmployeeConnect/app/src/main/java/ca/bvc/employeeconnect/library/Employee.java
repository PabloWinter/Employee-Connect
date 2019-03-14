package ca.bvc.employeeconnect.library;

public class Employee extends Person {

    public Employee(String firstName, String lastName, String email, String password, int storeId){
        super(firstName, lastName, email, password, storeId);
    }

    public void displayEmployeeName(String firstName, String secondName){
        System.out.println("Welcome " + firstName + " " + secondName);
    }
}
