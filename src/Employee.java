/*
 * Shipping Store Management Software v0.1
 * Developed for CS3354: Object Oriented Design and Programming.
 * Copyright: Junye Wen (j_w236@txstate.edu)
 */

import java.util.List;

/**
 * Employee is a subclass of User
 * @author Jamal Rasool and Zach Sotak
 */
public class Employee extends User {
    private int socialSecurityNumber;
    private float monthlySalary;
    private int bankAccountNumber;

    /**
     * Default constructor.
     */
    public Employee() {
        this.socialSecurityNumber = 0;
        this.monthlySalary = 0.0f;
        this.bankAccountNumber = 0;
    }

    /**
     * Constructor initializes an employee object with the provided values.
     * @param id
     * @param firstName
     * @param lastName
     * @param socialSecurityNumber
     * @param monthlySalary
     * @param bankAccountNumber
     */
    public Employee(int id, String firstName, String lastName, int socialSecurityNumber, 
            float monthlySalary, int bankAccountNumber) {
        super(id, firstName, lastName);
        this.socialSecurityNumber = socialSecurityNumber;
        this.monthlySalary = monthlySalary;
        this.bankAccountNumber = bankAccountNumber;
    }

    /**
     * Get the SSN.
     * @return socialSecurityNumber
     */
    public int getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    /**
     * Set the SSN.
     * @param socialSecurityNumber
     */
    public void setSocialSecurityNumber(int socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    /**
     * Get the monthly salary.
     * @return monthlySalary
     */
    public float getMonthlySalary() {
        return monthlySalary;
    }

    /**
     * Set the monthly salary.
     * @param monthlySalary
     */
    public void setMonthlySalary(float monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    /**
     * Get the bank account number.
     * @return
     */
    public int getBankAccountNumber() {
        return bankAccountNumber;
    }

    /**
     * Set the bank account number.
     * @param bankAccountNumber
     */
    public void setBankAccountNumber(int bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    /**
     * Returns the attributes of the employee, in a formatted text fashion.
     * @return Formated Text.
     */
    @Override
    public String getFormattedText() {
        return String.format("| %10s | %9d | %12s | %12s | SSN: %9d, Salary: %10s, Bank#: %11d | %n",
                "Employee", id, firstName, lastName, socialSecurityNumber, monthlySalary, bankAccountNumber);
    }

    @Override
    public String toString() {
        return "id=" + id + ", firstName=" + firstName
                + ", lastName=" + lastName + ",Info: socialSecurityNumber=" + socialSecurityNumber
                + ", monthlySalary=" + monthlySalary
                + ", bankAccountNumber=" + bankAccountNumber;
    }
}
