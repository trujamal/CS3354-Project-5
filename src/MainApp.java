import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.awt.Toolkit;
import com.apple.eawt.Application;


/**
* Main access point
*/
public class MainApp {
    
    ShippingStore ss;
    private final Scanner sc; // Used to read from System's standard input
    
    /**
     * Constructor
     */

    public MainApp() {
        ss = ShippingStore.readDatabase();
        this.sc = new Scanner(System.in);
    }

        public void addComponentsToPane(Container pane) {
            pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

            // Declaring Buttons
            JButton w1 = new JButton("Show All existing packages in the database");
            JButton w2 = new JButton("Add a new package to the database");
            JButton w3 = new JButton("Delete a package from a database (given its tracking number)");
            JButton w4 = new JButton("Search for a package (given its tracking number)");
            JButton w5 = new JButton("Show list of users");
            JButton w6 = new JButton("Add a new user to the database");
            JButton w7 = new JButton("Update user info (given their id)");
            JButton w8 = new JButton("Deliver a package");
            JButton w9 = new JButton("Show a list of transactions");

            // Setting Alignment
            w1.setAlignmentX(Component.CENTER_ALIGNMENT);
            w2.setAlignmentX(Component.CENTER_ALIGNMENT);
            w3.setAlignmentX(Component.CENTER_ALIGNMENT);
            w4.setAlignmentX(Component.CENTER_ALIGNMENT);
            w5.setAlignmentX(Component.CENTER_ALIGNMENT);
            w6.setAlignmentX(Component.CENTER_ALIGNMENT);
            w7.setAlignmentX(Component.CENTER_ALIGNMENT);
            w8.setAlignmentX(Component.CENTER_ALIGNMENT);
            w9.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Adding buttons
            pane.add(w1);
            pane.add(w2);
            pane.add(w3);
            pane.add(w4);
            pane.add(w5);
            pane.add(w6);
            pane.add(w7);
            pane.add(w8);
            pane.add(w9);

            // Handling Button Output


            w1.addActionListener((ActionEvent v) -> {
                // do something here
                System.out.println("Option 1 was selected");
                showAllPackages();
            });

            w2.addActionListener((ActionEvent v) -> {
                // do something here
                System.out.println("Option 2 was selected");
                try {
                    addNewPackage();
                }catch(Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.PLAIN_MESSAGE);
                    System.exit(0);
                }
            });

            w3.addActionListener((ActionEvent v) -> {
                // do something here
                System.out.println("Option 3 was selected");
                deletePackage();
            });

            w4.addActionListener((ActionEvent v) -> {
                // do something here
                System.out.println("Option 4 was selected");
                searchPackage();

            });

            w5.addActionListener((ActionEvent v) -> {
                // do something here
                System.out.println("Option 5 was selected");
                showAllUsers();
            });

            w6.addActionListener((ActionEvent v) -> {
                // do something here
                System.out.println("Option 6 was selected");
                addNewUser();
            });

            w7.addActionListener((ActionEvent v) -> {
                // do something here
                System.out.println("Option 7 was selected");
                try {
                    updateUser();
                }catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.PLAIN_MESSAGE);
                }
            });

            w8.addActionListener((ActionEvent v) -> {
                // do something here
                System.out.println("Option 8 was selected");
                try{
                    deliverPackage();
                }catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.PLAIN_MESSAGE);
                }
            });

            w9.addActionListener((ActionEvent v) -> {
                // do something here
                System.out.println("Option 9 was selected");
                showAllTransactions();
            });



        }

        /**
         * Create the GUI and show it.  For thread safety,
         * this method should be invoked from the
         * event-dispatching thread.
         */

        private void createAndShowGUI() {
            //Create and set up the window.
            JFrame frame = new JFrame("Shipping Store");
            frame.setDefaultCloseOperation(closeOP());

            JPanel titlePanel = new JPanel();
            titlePanel.setBorder(BorderFactory.createTitledBorder("Welcome to the shipping store"));
            frame.add(titlePanel);


            //Set up the content pane.
            addComponentsToPane(frame.getContentPane());

            // Colors
            frame.setBackground(Color.LIGHT_GRAY);

            //Display the window.
            frame.pack();
            frame.setLocationRelativeTo(null); // Centers Program
            frame.setVisible(true);
        }

    public int closeOP()
    {
        ss.writeDatabase();
        return JFrame.EXIT_ON_CLOSE;
    }

    /**
    * This method servers as the main interface between the program and the user.
    * The method interacts with the user by printing out a set of options, and
    * asking the user to select one.
    */
   public void runSoftware() {
        createAndShowGUI();
   }

   /**
    * Auxiliary method that prints out the operations menu.
    */
   private static void printMenu() {
       System.out.println(
               "\n 1. Show all existing packages in the database.\n" +
               " 2. Add a new package to the database. \n" +
               " 3. Delete a package from a database (given its tracking number).\n" +
               " 4. Search for a package (given its tracking number).\n" +
               " 5. Show list of users.\n" +
               " 6. Add a new user to the database.\n" +
               " 7. Update user info (given their id).\n" +
               " 8. Deliver a package.\n" +
               " 9. Show a list of transactions.\n" +
               "10. Exit program.\n");
   }
   
   /**
     * This method allows the user to enter a new package to the list
     * database.
     * @throws shippingstore.BadInputException bad input
     */
    public void addNewPackage() throws BadInputException {
        System.out.println("Select package type:\n"
                + "1. Envelope\n"
                + "2. Box\n"
                + "3. Crate\n"
                + "4. Drum");
        int packageType = sc.nextInt();
        if (packageType < 1 || packageType > 4) {
            throw new BadInputException("Legal package type values: 1-4.");
        }
        sc.nextLine();

        System.out.println("\nEnter tracking number (string): ");
        String ptn = sc.nextLine();
        if (ptn.length() > 5) {
            throw new BadInputException("Tracking number should not be more that 5 characters long.");
        }

        if (ss.packageExists(ptn)) {
            System.out.println("\nPackage with the same tracking number exists, try again");
            return;
        }

        System.out.println("\nEnter Specification: Fragile, Books, Catalogs, Do-not-bend, or N/A");
        String specification = sc.nextLine();
        boolean correct = false;

        correct = specification.equalsIgnoreCase("Fragile") || specification.equalsIgnoreCase("Books") || specification.equalsIgnoreCase("Catalogs");
        correct = correct || specification.equalsIgnoreCase("Do-not-bend") || specification.equalsIgnoreCase("N/A");

        if (!(correct)) {
            throw new BadInputException("Specifications can only be one of the following: Fragile, Books, Catalogs, Do-not-bend, or N/A");
        }

        System.out.println("\nEnter mailing class can be First-Class, Priority, Retail, Ground, or Metro.");
        String mailingClass = sc.nextLine();

        correct = mailingClass.equalsIgnoreCase("First-Class") || mailingClass.equalsIgnoreCase("Priority") || mailingClass.equalsIgnoreCase("Retail");
        correct = correct || mailingClass.equalsIgnoreCase("Ground") || mailingClass.equalsIgnoreCase("Metro");
        if (!(correct)) {
            throw new BadInputException("Specifications can only be one of the following: Fragile, Books, Catalogs, Do-not-bend, or N/A");
        }

        if (packageType == 1) {
            System.out.println("\nEnter height (inch), (int): ");
            int height = 0;
            if (sc.hasNextInt()) {
                height = sc.nextInt();
                sc.nextLine();
                if (height < 0) {
                    throw new BadInputException("Height of Envelope cannot be negative.");
                }
            } else {
                sc.nextLine();
                throw new BadInputException("Height of Envelope is integer.");
            }

            int width = 0;
            System.out.println("\nEnter width (inch), (int): ");
            if (sc.hasNextInt()) {
                width = sc.nextInt();
                sc.nextLine();
                if (width < 0) {
                    throw new BadInputException("Width of Envelope cannot be negative.");
                }
            } else {
                sc.nextLine();
                throw new BadInputException("Width of Envelope is integer.");
            }
            
            ss.addEnvelope(ptn, specification, mailingClass, height, width);

        } else if (packageType == 2) {
            System.out.println("\nEnter largest dimension (inch), (int): ");

            int dimension = 0;
            if (sc.hasNextInt()) {
                dimension = sc.nextInt();
                sc.nextLine();
                if (dimension < 0) {
                    throw new BadInputException("Largest dimension of Box cannot be negative.");
                }
            } else {
                sc.nextLine();
                throw new BadInputException("Dimension should be integer.");
            }

            System.out.println("\nEnter volume (inch^3), (int): ");

            int volume = 0;
            if (sc.hasNextInt()) {
                volume = sc.nextInt();
                sc.nextLine();
                if (volume < 0) {
                    throw new BadInputException("Volume of Box cannot be negative.");
                }
            } else {
                sc.nextLine();
                throw new BadInputException("Volume should be integer.");
            }

            ss.addBox(ptn, specification, mailingClass, dimension, volume);

        } else if (packageType == 3) {
            System.out.println("\nEnter maximum load weight (lb), (float): ");

            float weight = 0.0f;
            if (sc.hasNextFloat()) {
                weight = sc.nextFloat();
                sc.nextLine();
                if (weight < 0.0f) {
                    throw new BadInputException("Maximum load weight of Crate cannot be negative.");
                }
            } else {
                sc.nextLine();
                throw new BadInputException("Max load should be float");
            }

            System.out.println("\nEnter content (string): ");
            String content = sc.nextLine();

            ss.addCrate(ptn, specification, mailingClass, weight, content);
           
        } else if (packageType == 4) {

            System.out.println("\nEnter material (Plastic / Fiber): ");
            String material = sc.nextLine();
            if (!(material.equalsIgnoreCase("Plastic") || material.equalsIgnoreCase("Fiber"))) {
                throw new BadInputException("Material of Drum can only be plastic or fiber.");
            }

            float diameter = 0.0f;
            System.out.println("\nEnter diameter (float): ");
            if (sc.hasNextFloat()) {
                diameter = sc.nextFloat();
                sc.nextLine();
                if (diameter < 0.0f) {
                    throw new BadInputException("Diameter of Drum cannot be negative.");
                }
            } else {
                sc.nextLine();
                throw new BadInputException("Diameter should be float");
            }

            ss.addDrum(ptn, specification, mailingClass, material, diameter);
            
        } else {
            System.out.println("Unknown package type entered. Please try again.");
        }
    }
    
    /**
     * This method prints out all the package currently in the inventory, in a
     * formatted manner.
     */
    public void showAllPackages() {
        System.out.println(ss.getAllPackagesFormatted());
    }
    
    /**
     * This method allows the user to delete a package from the inventory
     * database.
     */
    public void deletePackage() {
        sc.nextLine();
        System.out.print("\nEnter tracking number of pacakge to delete (string): ");
        String ptn = sc.nextLine();

        if (ss.deletePackage(ptn)) 
            System.out.println("Package deleted.");
        else 
            System.out.println("Package with given tracking number not found in the database.");
    }
    
    /**
     * This method allows the users to search for a package given its tracking number
     * and then it prints details about the package.
     */
    public void searchPackage() {
        sc.nextLine();
        System.out.print("\nEnter tracking number of package to search for (string): ");
        String ptn = sc.nextLine();

        if (ss.packageExists(ptn))
            System.out.println(ss.getPackageFormatted(ptn));
        else
            System.out.println("Package with PTN " + ptn + " not found in the database");
    }
    
    /**
     * Prints out a list of all users in the database.
     */
    public void showAllUsers() {
        System.out.println(ss.getAllUsersFormatted());
    }
    
    /**
     * This method allows a new user to be added to the database.
     *
     */
    public void addNewUser() {
        boolean success;
        // Add fields for new user
        int userType = 0;
        boolean check = false;

        while (!check) {
            System.out.println("Select user type:\n"
                    + "1. Customer\n"
                    + "2. Employee");

            if (sc.hasNextInt()) {
                userType = sc.nextInt();

                if (userType < 1 || userType > 2) {
                    System.out.println("Wrong integer value: choose 1 or 2");
                } else {
                    check = true;
                }
            } else {
                System.out.println("Please select 1 or 2");
            }
        }

        sc.nextLine();
        System.out.println("\nEnter first name (string): ");
        String firstName = sc.nextLine();

        System.out.println("\nEnter last name (string): ");
        String lastName = sc.nextLine();

        if (userType == 1) {
            System.out.println("\nEnter phone number (string): ");
            String phoneNumber = sc.nextLine();

            System.out.println("\nEnter address (string): ");
            String address = sc.nextLine();

            ss.addCustomer(firstName, lastName, phoneNumber, address);

        } else if (userType == 2) {

            check = false;
            float monthlySalary = 0.0f;

            while (!check) {

                System.out.println("\nEnter monthly salary (float): ");

                if (sc.hasNextFloat()) {
                    monthlySalary = sc.nextFloat();
                    if (monthlySalary < 0.0f) {
                        System.out.println("Monthly salary cannot be negative.");
                    } else {
                        check = true;
                    }
                    sc.nextLine();

                } else {
                    System.out.println("Please enter monthly salary as a non-zero float value.");
                    sc.nextLine();
                }
            }

            int ssn = 0;
            check = false;
            while (!check) {

                System.out.println("\nEnter SSN (9-digital int): ");
                if (sc.hasNextInt()) {
                    ssn = sc.nextInt();
                    if (String.valueOf(ssn).length() != 9) {
                        System.out.println("\nThat is not a nine digit number");
                    } else if (ssn < 10000000 || ssn > 999999999) {
                        System.out.println("\nGive a correct 9 digit integer");
                    } else {
                        check = true;
                    }
                    sc.nextLine();
                } else {
                    System.out.println("\nNot a number!");
                    sc.nextLine();
                } //end if
            }// end while

            check = false;
            int bankAccNumber = 0;
            while (!check) {
                System.out.println("\nEnter bank account number (int): ");
                if (sc.hasNextInt()) {
                    bankAccNumber = sc.nextInt();
                    if (bankAccNumber < 0) {
                        System.out.println("\nBank account cannot be negative");
                    } else {
                        check = true;
                    }
                    sc.nextLine();
                } else {
                    System.out.println("Invalid bank Account format, please try again");
                    sc.nextLine();
                }

            }//end while

            ss.addEmployee(firstName, lastName, ssn, monthlySalary, bankAccNumber);
        } else {
            System.out.println("Unknown user type. Please try again.");
        }

    }
    
    /**
     * This method can be used to update a user's information, given their user
     * ID.
     *
     * @throws shippingstore.BadInputException
     */
    public void updateUser() throws BadInputException {
        boolean check = false;
        System.out.print("\nEnter user ID: ");
        int userID = sc.nextInt();

        if (!ss.userExists(userID)) {
            System.out.println("User not found.");
            return;
        }

        sc.nextLine();
        System.out.print("\nEnter first name (string): ");
        String firstName = sc.nextLine();

        System.out.print("\nEnter last name (string): ");
        String lastName = sc.nextLine();

        if (ss.isCustomer(userID)) {
            System.out.print("\nEnter phone number (string): ");
            String phoneNumber = sc.nextLine();
            System.out.print("\nEnter address (string): ");
            String address = sc.nextLine();
            
            ss.updateCustomer(userID, firstName, lastName, phoneNumber, address);

        } else { //User is an employee

            float monthlySalary = 0.0f;
            check = false;
            while (!check) {

                System.out.println("\nEnter monthly salary (float): ");

                if (sc.hasNextFloat()) {
                    monthlySalary = sc.nextFloat();
                    if (monthlySalary < 0.0f) {
                        new BadInputException("Monthly salary cannot be negative.");
                    } else {
                        check = true;
                    }
                    sc.nextLine();
                } else {
                    System.out.println("Please enter monthly salary as a non-zero float value.");
                    sc.nextLine();
                }
            }

            int ssn = 0;
            check = false;
            while (!check) {

                System.out.println("\nEnter SSN (9-digital int): ");
                if (sc.hasNextInt()) {
                    ssn = sc.nextInt();
                    if (String.valueOf(ssn).length() != 9) {
                        new BadInputException("\nThat is not a nine digit number");
                    } else if (ssn < 10000000 || ssn > 999999999) {
                        new BadInputException("\nGive a correct 9 digit integer");

                    } else {
                        check = true;
                    }
                } //end if
                sc.nextLine();

            }// end while

            int bankAccNumber = 0;
            check = false;
            while (!check) {
                System.out.println("\nEnter bank account number (int): ");

                if (sc.hasNextInt()) {
                    bankAccNumber = sc.nextInt();
                    if (bankAccNumber < 0) {
                        new BadInputException("Bank account cannot be negative");
                    } else {
                        check = true;
                    }
                    sc.nextLine();
                } else {
                    System.out.println("Invalid bank Account format, please try again");
                    sc.nextLine();
                }
            } //end while

            ss.updateEmployee(userID, firstName, lastName, ssn, monthlySalary, bankAccNumber);
        }
    }
    
    /**
     * This method is used to complete a package shipping/delivery transaction.
     *
     * @throws shippingstore.BadInputException
     */
    public void deliverPackage() throws BadInputException {

        Date currentDate = new Date(System.currentTimeMillis());

        sc.nextLine();
        System.out.println("\nEnter customer ID (int): ");
        int customerId = sc.nextInt();
        //Check that the customer exists in database
        boolean customerExists = ss.userExists(customerId);

        if (!customerExists) {
            System.out.println("\nThe customer ID you have entered does not exist in the database.\n"
                    + "Please add the customer to the database first and then try again.");
            return;
        }

        System.out.println("\nEnter employee ID (int): ");

        int employeeId = 0;
        if (sc.hasNextInt()) {
            employeeId = sc.nextInt();
        }
        //Check that the employee exists in database
        boolean employeeExists = ss.userExists(employeeId);

        if (!employeeExists) {
            System.out.println("\nThe employee ID you have entered does not exist in the database.\n"
                    + "Please add the employee to the database first and then try again.");
            return;
        }

        sc.nextLine();
        System.out.println("\nEnter tracking number (string): ");
        String ptn = sc.nextLine();

        //Check that the package exists in database
        if (!ss.packageExists(ptn)) {
            System.out.println("\nThe package with the tracking number you are trying to deliver "
                    + "does not exist in the database. Aborting transaction.");
            return;
        }

        System.out.println("\nEnter price (float): ");
        float price = sc.nextFloat();
        if (price < 0.0f) {
            throw new BadInputException("Price cannot be negative.");
        }

        ss.addShppingTransaction(customerId, employeeId, ptn, currentDate, currentDate, price);
        ss.deletePackage(ptn);

        System.out.println("\nTransaction Completed!");
    }
    
    
    /**
     * Prints out a list of all recorded transactions.
     */
    public void showAllTransactions() {
        System.out.println(ss.getAllTransactionsText());
    }


    /**
     * The main method of the program.
     *
     * @param args the command line arguments
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        MainApp app = new MainApp();
        app.runSoftware();
    }
}
