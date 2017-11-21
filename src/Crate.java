/*
 * Shipping Store Management Software v0.1
 * Developed for CS3354: Object Oriented Design and Programming.
 * Copyright: Junye Wen (j_w236@txstate.edu)
 */

/**
 * Crate is a subclass of Package
 * @author Jamal Rasool and Zach Sotak
 */
public class Crate extends Package {

    private float loadWeight;
    private String content;

    /**
     * Default constructor.
     */
    public Crate() {
        this.loadWeight = 0;
        this.content = "";
    }

    /**
     * Constructor used to initialize the class fields of the class with the
     * provided values.
     * @param ptn
     * @param specification
     * @param mailingClass
     * @param loadWeight
     * @param content
     */
    public Crate(String ptn, String specification, String mailingClass, 
            float loadWeight, String content) {
        super(ptn, specification, mailingClass);
        this.loadWeight = loadWeight;
        this.content = content;
    }

    /**
     * Get the crate loadWeight.
     * @return loadWeight
     */
    public float getLoadWeight() {
        return loadWeight;
    }

    /**
     * set the crate loadWeight.
     * @param loadWeight
     */
    public void setLoadWeight(float loadWeight) {
        this.loadWeight = loadWeight;
    }

    /**
     * Get the crate content.
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * Set the crate content.
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get the attributes of the crate, in a formatted text fashion.
     * @return Formatted text
     */
    @Override
    public String getFormattedText() {
        return String.format("| %12s | %12s | %13s | %13s | Load Weight: %10f, Content: %10s | %n",
                "Crate", ptn, specification, mailingClass, loadWeight, content);
    }

    @Override
    public String toString() {
        return "ptn=" + ptn + ", specification=" + specification +
                ", Mailing Class=" + mailingClass + ", Info: loadWeight=" + loadWeight +
                ", width=" + content;
    }

    public void display() {
        System.out.printf("Crate ", getPtn(), getSpecification(), getMailingClass(), getLoadWeight(), getContent());
    }
}
