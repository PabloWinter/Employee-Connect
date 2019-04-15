package ca.bvc.employeeconnect.model;

public class RequestDayOff {

    public String nameUser;
    public String employeeCode;
    public String reasonDayOff;
    public String dateDayOff;

    public RequestDayOff(String name, String codeEmployee, String reason, String dateDayOff ){
        this.nameUser = name;
        this.employeeCode = codeEmployee;
        this.reasonDayOff = reason;
        this.dateDayOff = dateDayOff;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getReasonDayOff() {
        return reasonDayOff;
    }

    public void setReasonDayOff(String reasonDayOff) {
        this.reasonDayOff = reasonDayOff;
    }

    public String getDateDayOff() {
        return dateDayOff;
    }

    public void setDateDayOff(String dateDayOff) {
        this.dateDayOff = dateDayOff;
    }
}
