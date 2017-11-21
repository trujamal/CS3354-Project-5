/*
 * Shipping Store Management Software v0.1
 * Developed for CS3354: Object Oriented Design and Programming.
 * Copyright: Junye Wen (j_w236@txstate.edu)
 */

import java.io.Serializable;

/**
 * Class Package is an abstract entity class that represents a Package in the
 * shipping store. This class is expected to be inherited by subclasses
 * that specify the package type. No instance of this class can be created.
 * 
 * @author Jamal Rasool and Zach Sotak
 */
public abstract class Package implements Serializable {

    /**
     * The Package Tracking Number (PTN) of the vehicle.
     */
    protected String ptn;

    /**
     * The specification  of the package.
     */
    protected String specification;

    /**
     * The mailing class of the package.
     */
    protected String mailingClass;
    

    /**
     * Default constructor used to initialize the class fields of the class.
     * Since this is an abstract class, the constructor cannot be used to
     * instantiate objects object of the class.
     */
    protected Package() {
        this.ptn = "";
        this.specification = "";
        this.mailingClass = "";
    }

    /**
     * Constructor used to initialize the class fields of the class. Since this
     * is an abstract class, the constructor cannot be used to instantiate
     * objects object of the class.
     * @param ptn The Package Tracking Number (PTN) of the package.
     * @param specification The specification of the package.
     * @param mailingClass The mailing class of the package.
     */
    protected Package(String ptn, String specification, String mailingClass) {
        this.ptn = ptn;
        this.specification = specification;
        this.mailingClass = mailingClass;
    }

    /**
     * Get the PTN of the package.
     * @return ptn The PTN of the package.
     */
    public String getPtn() {
        return ptn;
    }

    /**
     * Set the PTN of the package.
     * @param ptn
     */
    public void setPtn(String ptn) {
        this.ptn = ptn;
    }

    /**
     * Get the specification of the package.
     * @return specification The Specification of the package
     */
    public String getSpecification() {
        return specification;
    }

    /**
     * Set the specification of the package.
     * @param specification
     */
    public void setSpecification(String specification) {
        this.specification = specification;
    }

    /**
     * Get the mailing calss of the package.
     * @return mailingcalss The mailing class of the package
     */
    public String getMailingClass() {
        return mailingClass;
    }

    /**
     * Set the mailingClass of the package.
     * @param mailingClass
     */
    public void setMailingClass(String mailingClass) {
        this.mailingClass = mailingClass;
    }
    
    /**
     * Abstract method, to be implemented by subclasses of class Package.
     * @return the text describing the package in a formatted manner.
     */
    public abstract String getFormattedText();
}
