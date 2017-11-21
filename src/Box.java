/*
 * Shipping Store Management Software v0.1
 * Developed for CS3354: Object Oriented Design and Programming.
 * Copyright: Junye Wen (j_w236@txstate.edu)
 */

/**
 * Box class is a child class of Package
 * @author Jamal Rasool and Zack Sotak
 */
public class Box extends Package {

    private int dimension;
    private int volume;

    /**
     * Default constructor.
     */
    public Box() {
        this.dimension = 0;
        this.volume = 0;
    }

    /**
     * Constructor used to initialize the class fields of the class with the
     * provided values.
     * @param ptn package tracking number
     * @param specification detailed specification
     * @param mailingClass mailing class
     * @param dimension largest dimension
     * @param volume Volume
     */
    public Box(String ptn, String specification, String mailingClass, int dimension, int volume) {
        super(ptn, specification, mailingClass);
        this.dimension = dimension;
        this.volume = volume;
    }

    /**
     * Get the box dimension.
     * @return dimension
     */
    public int getDimension() {
        return dimension;
    }

    /**
     * set the box dimension.
     * @param dimension box dimension
     */
    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    /**
     * Get the box volume.
     * @return volume
     */
    public int getVolume() {
        return volume;
    }

    /**
     * Set the box volume.
     * @param volume box volume
     */
    public void setVolume(int volume) {
        this.volume = volume;
    }

    /**
     * Get the attributes of the box, in a formatted text fashion.
     * @return Formatted text
     */
    @Override
    public String getFormattedText() {
        return String.format("| %12s | %12s | %13s | %13s | Dimension: %10d, Volume: %10d    | %n",
                "Box", ptn, specification, mailingClass, dimension, volume);
    }

    @Override
    public String toString() {
        return "ptn=" + ptn + ", specification=" + specification +
                ", Mailing Class=" + mailingClass + ",Info: dimension =" + dimension +
                ", volume=" + volume;
    }

    public void display() {
        System.out.printf("Box", getPtn(), getSpecification(), getMailingClass(), getDimension(), getVolume());
    }
}
