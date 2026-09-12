package domain;

import java.util.Objects;

public class Staff {

    private String staffId;
    private String employeeNumber;
    private String name;
    private String role;
    private String department;
    private String phone;
    private ServiceStatus status;

    public Staff() {
        this.status = ServiceStatus.ACTIVE;
    }

    public Staff(String staffId, String employeeNumber,
                 String name, String role,
                 String department, String phone) {

        setStaffId(staffId);
        setEmployeeNumber(employeeNumber);
        setName(name);
        setRole(role);
        setDepartment(department);
        setPhone(phone);

        this.status = ServiceStatus.ACTIVE;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        if (staffId == null || staffId.trim().isEmpty()) {
            throw new IllegalArgumentException("Staff ID cannot be empty.");
        }
        this.staffId = staffId.trim();
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Staff name cannot be empty.");
        }
        this.name = name.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? "" : role.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? "" : department.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? "" : phone.trim();
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = Objects.requireNonNull(status);
    }

    @Override
    public String toString() {
        return employeeNumber + " - " + name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Staff)) return false;

        Staff other = (Staff) obj;
        return Objects.equals(staffId, other.staffId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(staffId);
    }
}