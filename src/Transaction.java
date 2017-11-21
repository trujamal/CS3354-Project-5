/*
 * Shipping Store Management Software v0.1
 * Developed for CS3354: Object Oriented Design and Programming.
 * Copyright: Junye Wen (j_w236@txstate.edu)
 */

import java.io.Serializable;
import java.util.Date;

/**
 * This class represents a transaction in the Package Shipping Store Software.
 * @author Jamal Rasool and Zach Sotak
 */
public class Transaction implements Serializable {
    private int customerId;
    private int employeeId;
    private String ptn;
    private Date shippingDate;
    private Date deliveryDate;
    private float price;

    /**
     * Constructor initializes a SaleTransaction object with the provided values.
     * @param customerId
     * @param employeeId
     * @param ptn
     * @param shippingDate
     * @param deliveryDate
     * @param price
     */
    public Transaction(int customerId, int employeeId, String ptn,
                       Date shippingDate, Date deliveryDate, float price) {
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.ptn = ptn;
        this.shippingDate = shippingDate;
        this.deliveryDate = deliveryDate;
        this.price = price;
    }

    /**
     * Get the customer ID of this transaction.
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Set the customer ID of this transaction.
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Get the employee ID of this transaction.
     * @return
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * Set the employee ID of this transaction.
     * @param employeeId
     */
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Get the package PTN of this transaction.
     * @return ptn
     */
    public String getPtn() {
        return ptn;
    }

    /**
     * Set the package PTN of this transaction.
     * @param ptn
     */
    public void setPtn(String ptn) {
        this.ptn = ptn;
    }

    /**
     * Get the shipping date of this transaction.
     * @return shippingDate
     */
    public Date getShippingDate() {
        return shippingDate;
    }

    /**
     * Set the shipping date of this transaction.
     * @param shippingDate
     */
    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    /**
     * Get the delivery date of this transaction.
     * @return deliveryDate
     */
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * Set the delivery date of this transaction.
     * @param deliveryDate
     */
    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    /**
     * Get the price of this transaction.
     * @return
     */
    public float getPrice() {
        return price;
    }

    /**
     * Set the price of this transaction.
     * @param price
     */
    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Transaction{" + "customerId=" + customerId + ", employeeId="
                + employeeId + ", ptn=" + ptn + ", shippingDate=" + shippingDate
                + ", deliveryDate=" + deliveryDate + ", price=" + price + '}';
    }
}
