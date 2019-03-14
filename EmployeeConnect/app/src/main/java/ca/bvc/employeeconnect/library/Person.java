package ca.bvc.employeeconnect.library;

public abstract class Person {
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;
    protected int storeId;

    public Person(String firstName, String lastName, String email, String password, int storeId){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.storeId = storeId;
    }
}
