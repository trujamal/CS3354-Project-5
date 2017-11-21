/*
 * Shipping Store Management Software v0.1
 * Developed for CS3354: Object Oriented Design and Programming.
 * Copyright: Junye Wen (j_w236@txstate.edu)
 */

/**
 * Envelope is a subclass of Package
 * @author Jamal Rasool and Zach Sotak
 */
public class Envelope extends Package {

    private int height;
    private int width;

    /**
     * Default constructor.
     */
    public Envelope() {
        this.height = 0;
        this.width = 0;
    }

    /**
     * Constructor used to initialize the class fields of the class with the
     * provided values.
     * @param ptn
     * @param specification
     * @param mailingClass
     * @param height
     * @param width
     */
    public Envelope(String ptn, String specification, String mailingClass, int height, int width) {
        super(ptn, specification, mailingClass);
        this.height = height;
        this.width = width;
    }

    /**
     * Get the envelope height.
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * set the envelope height.
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Get the envelope width.
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Set the envelope width.
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Get the attributes of the envelope, in a formatted text fashion.
     * @return Formatted text
     */
    @Override
    public String getFormattedText() {
        return String.format("| %12s | %12s | %13s | %13s | Height: %10d, Width: %10d        | %n",
                "Envelope", ptn, specification, mailingClass, height, width);
    }

    @Override
    public String toString() {
        return "ptn=" + ptn + ", specification=" + specification +
                ", Mailing Class=" + mailingClass + ",Info: height=" + height +
                ", width=" + width;
    }

    public void display() {
        System.out.printf("Envelope", getPtn(), getSpecification(), getMailingClass(), getHeight(), getWidth());
    }
}
