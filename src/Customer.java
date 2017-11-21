/*
 * Shipping Store Management Software v0.1
 * Developed for CS3354: Object Oriented Design and Programming.
 * Copyright: Junye Wen (j_w236@txstate.edu)
 */


/**
 * Designed to hold the customer class of information
 * @author Jamal Rasool
 */
public class Customer extends User {
    private String phoneNumber;
    private String address;

    /**
     * Default constructor.
     */
    public Customer() {
        this.phoneNumber = "";
        this.address = "";
    }

    /**
     * Constructor initializes a customer object with the provided values.
     * @param id
     * @param phoneNumber
     * @param address
     * @param firstName
     * @param lastName
     */
    public Customer(int id, String firstName, String lastName, String phoneNumber, String address) {
        super(id, firstName, lastName);
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    /**
     * Get the phone number.
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Set the phone number.
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Get the address.
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the address.
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * Returns the attributes of the customer, in a formatted text fashion.
     * @return Formated Text.
     */
    public String getFormattedText() {
        return String.format("| %10s | %9d | %12s | %12s | Ph#: %12s, Add: %30s | %n",
                "Customer", id, firstName, lastName, phoneNumber, address);
    }

    @Override
    public String toString() {
        return "id=" + id + ", firstName=" + firstName
                + ", lastName=" + lastName + ",Info: phoneNumber=" + phoneNumber
                + ", address=" + address;
    }
}
