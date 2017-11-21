/*
 * Shipping Store Management Software v0.1
 * Developed for CS3354: Object Oriented Design and Programming.
 * Copyright: Junye Wen (j_w236@txstate.edu)
 */

/**
 * Drum is a subclass of Package
 * @author Jamal Rasool and Zach Sotak
 */
public class Drum extends Package {

    private String material;
    private float diameter;

    /**
     * Default constructor.
     */
    public Drum() {
        this.material = "";
        this.diameter = 0.0f;
    }

    /**
     * Constructor used to initialize the class fields of the class with the
     * provided values.
     * @param ptn
     * @param specification
     * @param mailingClass
     * @param material
     * @param diameter
     */
    public Drum(String ptn, String specification, String mailingClass, String material, float diameter) {
        super(ptn, specification, mailingClass);
        this.material = material;
        this.diameter = diameter;
    }

    /**
     * Get the drum material.
     * @return material
     */
    public String getMaterial() {
        return material;
    }

    /**
     * set the drum material.
     * @param material
     */
    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     * Get the drum diameter.
     * @return diameter
     */
    public float getDiameter() {
        return diameter;
    }

    /**
     * Set the drum diameter.
     * @param diameter
     */
    public void setDiameter(float diameter) {
        this.diameter = diameter;
    }

    /**
     * Get the attributes of the drum, in a formatted text fashion.
     * @return Formatted text
     */
    @Override
    public String getFormattedText() {
        return String.format("| %12s | %12s | %13s | %13s | Material: %10s, Diamether: %10f  | %n",
                "Drum", ptn, specification, mailingClass, material, diameter);
    }

    @Override
    public String toString() {
        return "ptn=" + ptn + ", specification=" + specification +
                ", Mailing Class=" + mailingClass + ",Info: Material=" + material +
                ", volume=" + diameter;
    }

    public void display() {
        System.out.printf("Drum", getPtn(), getSpecification(), getMailingClass(), getMaterial(), getDiameter());
    }

}
