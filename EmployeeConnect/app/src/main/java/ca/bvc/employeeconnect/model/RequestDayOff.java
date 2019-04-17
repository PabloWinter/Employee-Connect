package ca.bvc.employeeconnect.model;

public class RequestDayOff {

    public String nameUser;
    public String employeeCode;
    public String reasonDayOff;
    public String dateDayOff;

    /**
     * Constructor
     * @param name
     * @param codeEmployee
     * @param reason
     * @param dateDayOff
     */
    public RequestDayOff(String name, String codeEmployee, String reason, String dateDayOff ){
        this.nameUser = name;
        this.employeeCode = codeEmployee;
        this.reasonDayOff = reason;
        this.dateDayOff = dateDayOff;
    }

    /**
     * get for employee Name;
     * @return
     */
    public String getNameUser() {
        return nameUser;
    }

    /**
     * set for User Name;
     * @param nameUser
     */
    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    /**
     * get for employee code
     * @return
     */
    public String getEmployeeCode() {
        return employeeCode;
    }

    /**
     * Set for employee code
     * @param employeeCode
     */
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    /**
     * Get for day off request
     * @return
     */
    public String getReasonDayOff() {
        return reasonDayOff;
    }

    /**
     * set for day off request
     * @param reasonDayOff
     */
    public void setReasonDayOff(String reasonDayOff) {
        this.reasonDayOff = reasonDayOff;
    }

    /**
     * get for day off
     * @return
     */
    public String getDateDayOff() {
        return dateDayOff;
    }

    /**
     * set for date off
     * @param dateDayOff
     */
    public void setDateDayOff(String dateDayOff) {
        this.dateDayOff = dateDayOff;
    }
}
