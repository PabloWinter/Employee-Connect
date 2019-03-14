package ca.bvc.employeeconnect.library;

public class Manager extends Person{
    public int managerId;

    public Manager(String firstName, String lastName, String email, String password, int storeId, int managerId) {
        super(firstName, lastName, email, password, storeId);
        this.managerId = managerId;
    }
}
