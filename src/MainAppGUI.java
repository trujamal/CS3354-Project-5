import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.logging.Formatter;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

/**
 * @author Jamal Rasool (j_r771)
 * @author Zach Sotak (zs1046)
 * @version 1.0
 * MainAppGUI
 */

public class MainAppGUI extends JFrame {

    private static ShippingStore ss = ShippingStore.readDatabase(),
            db = (ss == null) ? new ShippingStore() : ss;

    private static final Logger logger = Logger.getLogger(MainAppGUI.class.getName());
    private static FileHandler fh;


    private final static String ENVELOPEPANEL = "Envelope",
            BOXPANEL = "Box",
            CRATEPANEL = "Crate",
            DRUMPANEL = "Drum",
            CUSTOMERPANEL = "CUSTOMER",
            EMPLOYEEPANEL = "EMPLOYEE";

    private final static int extraWindowWidth = 100;

    private final Date today = new Date();


    private JButton exitFrom,
            exitFromPackage,
            exitFromUser;

    /**
     * Default Constructor for class MainAppGUI
     */

    MainAppGUI() {

    }

    /**
     * Class CustomFormatter overrides the format method of the superclass Formatter in order to format the
     * Logger output when writing to a file to a readable format (as opposed to the default XML format)
     */

    class CustomFormatter extends Formatter {
        private final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

        /**
         * format() method overrides the superclass's method in order to obtain a much simpler output
         *
         * @param record refers to the LogRecord to change
         * @return builder.toString(), the String which will be the new format for each log event
         */
        @Override
        public String format(LogRecord record) {
            StringBuilder builder = new StringBuilder(1000);
            builder.append(df.format(new Date(record.getMillis()))).append(" - ");
            builder.append("[").append(record.getSourceClassName()).append(".");
            builder.append(record.getSourceMethodName()).append("] :");
            builder.append("\n");
            builder.append("\t[").append(record.getLevel()).append("] - ");
            builder.append(formatMessage(record));
            builder.append("\n");
            return builder.toString();
        }

        public String getHead(Handler h) {
            return super.getHead(h);
        }

        public String getTail(Handler h) {
            return super.getTail(h);
        }
    }

    /**
     * initLogger() method initializes the Logger environment for the class upon call
     */
    private void initLogger() {
        logger.setUseParentHandlers(false);
        CustomFormatter formatter = new CustomFormatter();
        try {
            fh = new FileHandler("log.txt");
            fh.setFormatter(formatter);
            logger.addHandler(fh);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "FileHandler threw IOException", e);
        }
    }

    /**
     * MainAppGUI is designed to set the information in each frame of the program, such as the title and the buttons
     * that are within the program.
     *
     * @param title receives the name to set the window as.
     */

    private MainAppGUI(String title) {
        // Setting title information
        super(title);
        initLogger();

        // Creating a new panel to house the buttons within
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(10, 0));
        JLabel introText = new JLabel("\n\nPlease select an option below:");
        JTextField field = new JTextField(35);
        introText.setHorizontalAlignment(SwingConstants.CENTER);
        field.setText("STATUS: Waiting for user...");
        field.setEditable(false);

        // show all existing packages JButton
        JButton showExisting = new JButton("Show all existing packages in the database");
        showExisting.setHorizontalAlignment(SwingConstants.LEFT);

        // Add a new package to data base JButton
        JButton addPackage = new JButton("Add a new package to the database");
        addPackage.setHorizontalAlignment(SwingConstants.LEFT);

        // Delete package JButton
        JButton delPack = new JButton("Delete a package from a database (given its tracking number)");
        delPack.setHorizontalAlignment(SwingConstants.LEFT);

        // Search Package JButton
        JButton searchPack = new JButton("Search for a package (given its tracking number)");
        searchPack.setHorizontalAlignment(SwingConstants.LEFT);

        // Show a list of Users JButton
        JButton listUsers = new JButton("Show list of users");
        listUsers.setHorizontalAlignment(SwingConstants.LEFT);

        // Add a new User JButton
        JButton addUser = new JButton("Add a new user to the database");
        addUser.setHorizontalAlignment(SwingConstants.LEFT);

        // Update user info JButton
        JButton updateUserInfo = new JButton("Update user info (given their id)");
        updateUserInfo.setHorizontalAlignment(SwingConstants.LEFT);

        // Deliver package JButton
        JButton deliverPackage = new JButton("Deliver a package");
        deliverPackage.setHorizontalAlignment(SwingConstants.LEFT);

        JButton showAllCompletedTransactions = new JButton("Show a list of completed shipping transactions");
        showAllCompletedTransactions.setHorizontalAlignment(SwingConstants.LEFT);

        JButton exit = new JButton("Exit program");
        exit.setHorizontalAlignment(SwingConstants.LEFT);


        showExisting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                logger.log(Level.INFO, "User pressed 'Show all existing packages in database'");
                field.setText("STATUS: in display inventory ...");
                Thread qThread = new Thread() {
                    public void run() {
                        displayInventoryUI();
                    }

                };
                qThread.start();
            }
        });

        addPackage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                logger.log(Level.INFO, "Add a new package to the database'");
                field.setText("STATUS: in adding package ...");
                Thread qThread = new Thread() {
                    public void run() {
                        addPackageUI();
                    }
                };
                qThread.start();
            }
        });

        delPack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                logger.log(Level.INFO, "User pressed 'Delete a package from the database'");
                field.setText("STATUS: in deleting package ...");
                Thread qThread = new Thread() {
                    public void run() {
                        deletePackageUI();
                    }
                };
                qThread.start();
            }
        });

        searchPack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                logger.log(Level.INFO, "User pressed 'Search for a package'");
                field.setText("STATUS: in searching inventory ...");
                Thread qThread = new Thread() {
                    public void run() {
                        searchPackUI();
                    }
                };
                qThread.start();
            }
        });

        listUsers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                logger.log(Level.INFO, "User pressed 'Show list of users'");
                field.setText("STATUS: in listing users...");
                Thread qThread = new Thread() {
                    public void run() {
                        listUsersUI();
                    }
                };
                qThread.start();
            }
        });

        addUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                logger.log(Level.INFO, "User pressed 'Add a new user to the database'");
                field.setText("STATUS: adding user to database ...");
                Thread qThread = new Thread() {
                    public void run() {
                        addUserUI();
                    }
                };
                qThread.start();
            }
        });

        updateUserInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                logger.log(Level.INFO, "User pressed 'Update user info (given their id)'");
                field.setText("STATUS: Updating user ...");
                Thread qThread = new Thread() {
                    public void run() {
                        updateUserInfoUI();
                    }
                };
                qThread.start();
            }
        });

        deliverPackage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                logger.log(Level.INFO, "User pressed 'Update deliver package'");
                field.setText("STATUS: User in Deliver a package ...");
                Thread qThread = new Thread() {
                    public void run() {
                        deliverPackageUI();
                    }
                };
                qThread.start();
            }
        });

        showAllCompletedTransactions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                logger.log(Level.INFO, "User pressed 'Show a list of completed shipping transactions'");
                field.setText("STATUS: Showing completed transactions ...");
                Thread qThread = new Thread() {
                    public void run() {
                        showAllCompletedTransactionsUI();
                    }
                };
                qThread.start();
            }
        });

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                logger.log(Level.INFO, "User pressed 'Exit program'");
                Thread qThread = new Thread() {
                    public void run() {
                        try {
                            closeOP();
                        } catch (Exception e) {
                            logger.log(Level.SEVERE, e.toString());
                            System.exit(0);
                        }
                    }
                };
                qThread.start();
                field.setText("STATUS: bye! Saving session ...");
            }
        });

        this.setContentPane(mainPanel);
        mainPanel.add(introText, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(field, BorderLayout.SOUTH);
        buttonPanel.add(showExisting);
        buttonPanel.add(addPackage);
        buttonPanel.add(delPack);
        buttonPanel.add(searchPack);
        buttonPanel.add(listUsers);
        buttonPanel.add(deliverPackage);
        buttonPanel.add(addUser);
        buttonPanel.add(updateUserInfo);
        buttonPanel.add(showAllCompletedTransactions);
        buttonPanel.add(exit);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null); // Centers Program
        this.setVisible(true);

        logger.log(Level.INFO, "User has loaded main menu of GUI");
    }

    /**
     * Display Inventory UI, is designed to go through and show all of the list of packages within the database, by displaying
     * informaiton such as tracking number, mailing class, and many other feautres.
     */

    public void displayInventoryUI() {


        if (db.getPackageList().size() == 0) {
            JOptionPane.showMessageDialog(null, "There is nothing to view as the database\n" +
                            " is currently empty! Now exiting..", "Failure!",
                    ERROR_MESSAGE);
            logger.log(Level.WARNING, "User attempted to view an empty database");
            return;
        }

        // Implement right header
        String[] header = {"PACKAGE TYPE", "TRACKING #", "SPECIFICATION", "MAILING CLASS", "OTHER DETAILS"};
        Object[][] data = new Object[db.getPackageList().size()][header.length];
        try {
            // Do something
            for (int i = 0; i < db.getPackageList().size(); ++i) {

                data[i][0] = db.getPackageList().get(i).getClass().getName();
                data[i][1] = db.getPackageList().get(i).getPtn();
                data[i][2] = db.getPackageList().get(i).getSpecification();
                data[i][3] = db.getPackageList().get(i).getMailingClass();
                String info = db.getPackageList().get(i).toString();
                data[i][4] = info.substring(info.lastIndexOf("Info:")+6);
            }
        } catch (ClassCastException e) {
            logger.log(Level.SEVERE, "ClassCastException thrown, possible database corruption");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unknown exception thrown! See stack trace..", e);
            e.printStackTrace();
        }

        JFrame display = new JFrame("Inventory List");
        final JTable table = new JTable(data, header);
        table.setPreferredScrollableViewportSize(new Dimension(800, 100));
        table.setFillsViewportHeight(true);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        display.add(scrollPane);


        display.pack();
        this.setLocationRelativeTo(null); // Centers Program
        display.setVisible(true);
        display.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        logger.log(Level.INFO, "User in 'Package List' window");
    }

    /**
     * Add package UI is designed for bringing up the control pannel to add a package.
     */
    public void addPackageUI() {

        JFrame frame = new JFrame("Adding package");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        MainAppGUI n = new MainAppGUI();
        n.newPanelComponentPackage(frame.getContentPane());

        //Display the window.
        frame.pack();
        this.setLocationRelativeTo(null); // Centers Program
        frame.setVisible(true);
        // Setting up to print into the log
        logger.log(Level.INFO, "User in 'Adding Package' window");
    }

    /**
     * DeletePackageUI is designed to go through and take a users input
     */

    public void deletePackageUI() {
        JFrame frame = new JFrame("Deleting Packages");

        if (db.getPackageList().size() == 0) {
            JOptionPane.showMessageDialog(frame, "There is nothing to delete as the database\n" +
                            " is currently empty! Now exiting removal process..", "Failure!",
                    ERROR_MESSAGE);
            logger.log(Level.WARNING, "User attempted to view an empty list (package list)");
            return;
        }

        JPanel panel = new JPanel();
        JLabel instr = new JLabel("Enter the package number (to be deleted): ");
        JTextField TI = new JTextField(12);
        JButton submit = new JButton("Submit");
        JButton exit = new JButton("Exit");

        frame.setContentPane(panel);
        panel.add(instr);
        panel.add(TI);
        panel.add(submit);
        panel.add(exit);

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                logger.log(Level.INFO, "User submitted a Tracking number (string)");
                Thread qThread = new Thread() {
                    public void run() {
                        if (db.deletePackage(TI.getText())) {
                            JOptionPane.showMessageDialog(frame, "Removal was successful!", "Success!",
                                    JOptionPane.INFORMATION_MESSAGE);
                            logger.log(Level.INFO, "User was able to remove a package via string value");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Removal was unsuccessful! Please check your input" +
                                            " and try again!", "Failure!",
                                    ERROR_MESSAGE);
                            logger.log(Level.INFO, "User's search term was not found, nothing removed from package" +
                                    "database");
                        }
                    }
                };
                qThread.start();
            }
        });

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                frame.dispose();
                logger.log(Level.INFO, "User presses 'Exit' button");
            }
        });


        //Display the window.
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        this.setLocationRelativeTo(null); // Centers Program
        frame.setVisible(true);
        logger.log(Level.INFO, "User opened up GUI option to delete a package");

    }

    /**
     * Search Pack UI, is desinged to go through and search for the package that the user wants to look for and display
     * the information that goes along with it.
     */
    private void searchPackUI() {
        JFrame frame1 = new JFrame("Searching Package");

        if (db.getPackageList().size() == 0) {
            JOptionPane.showMessageDialog(null, "There is nothing to view as the database\n" +
                            " is currently empty! Now exiting..", "Failure!",
                    ERROR_MESSAGE);
            logger.log(Level.WARNING, "User attempted to view an empty list (Packages)");
            return;
        }

        JPanel searchpanel = new JPanel();
        JLabel searchfield = new JLabel("Enter the Package's tracking number:  ");
        JTextField trackingno = new JTextField(12);
        JButton submit = new JButton("Submit");
        JButton exit = new JButton("Exit");

        frame1.setContentPane(searchpanel);
        searchpanel.add(searchfield);
        searchpanel.add(trackingno);
        searchpanel.add(submit);
        searchpanel.add(exit);
        searchpanel.setVisible(true);


        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                logger.log(Level.INFO, "User submitted a tracking number for package");
                Thread qThread = new Thread() {
                    public void run() {
                        if (db.packageExists(trackingno.getText())) {
                            JFrame display = new JFrame("Inventory List");


                            // Implement right header
                            String[] header = {"PACKAGE TYPE", "TRACKING #", "SPECIFICATION", "MAILING CLASS", "OTHER DETAILS"};
                            Object[][] data = new Object[db.getPackageList().size()][header.length];
                            try {
                                // Do something
                                for (int i = 0; i < db.getPackageList().size(); ++i) {

                                    System.out.println(trackingno.getText());
                                    System.out.println(db.getPackageList().get(i).ptn);
                                    if (db.getPackageList().get(i).ptn.equals(trackingno.getText())) {
                                        data[i][0] = db.getPackageList().get(i).getClass().getName();
                                        data[i][1] = db.getPackageList().get(i).ptn;
                                        data[i][2] = db.getPackageList().get(i).specification;
                                        data[i][3] = db.getPackageList().get(i).mailingClass.toString();

                                        String info = db.getPackageList().get(i).toString();
                                        data[i][4] = info.substring(info.lastIndexOf("Info:")+6);

                                        break;
                                    }
                                }
                            } catch (ClassCastException e) {
                                logger.log(Level.SEVERE, "ClassCastException thrown, possible database corruption");
                            } catch (Exception e) {
                                logger.log(Level.SEVERE, "Unknown exception thrown! See stack trace..", e);
                                e.printStackTrace();
                            }
                            final JTable table = new JTable(data, header);
                            table.setPreferredScrollableViewportSize(new Dimension(800, 100));
                            table.setFillsViewportHeight(true);
                            table.setEnabled(false);

                            JScrollPane scrollPane = new JScrollPane(table);
                            display.add(scrollPane);

                            display.pack();
                            display.setVisible(true);
                            display.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            logger.log(Level.INFO, "User in 'Package List' window");


                        } else {
                            JOptionPane.showMessageDialog(null, "Package not found in database");
                            logger.log(Level.INFO, "User's search term was not found in the" +
                                    "database");
                        }
                    }
                };
                qThread.start();
            }
        });

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                frame1.dispose();
                logger.log(Level.INFO, "User presses 'Exit' button");
            }
        });


        //Display the window.
        frame1.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame1.pack();
        this.setLocationRelativeTo(null); // Centers Program
        frame1.setVisible(true);
        searchpanel.setVisible(true);
        logger.log(Level.INFO, "User opened up GUI option to search a package");

    }

    /**
     * List user UI is designed to list all of the users within the database of the system, and show all of their provided
     * information
     */
    public void listUsersUI() {
        JFrame frame2 = new JFrame("User List");

        String[] header = {"USER TYPE", "USER ID", "FIRST NAME", "LAST NAME", "OTHER DETAILS"};
        Object[][] data = new Object[db.getUserList().size()][header.length];
        try {
            // Do something
            for (int i = 0; i < db.getUserList().size(); ++i) {

                data[i][0] = db.getUserList().get(i).getClass().getName();
                data[i][1] = db.getUserList().get(i).getId();
                data[i][2] = db.getUserList().get(i).getFirstName();
                data[i][3] = db.getUserList().get(i).getLastName();
                String info = db.getUserList().get(i).toString();
                data[i][4] = info.substring(info.lastIndexOf("Info:")+6);
            }
        } catch (ClassCastException e) {
            logger.log(Level.SEVERE, "ClassCastException thrown, possible database corruption");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unknown exception thrown! See stack trace..", e);
            e.printStackTrace();
        }
        final JTable table = new JTable(data, header);
        table.setPreferredScrollableViewportSize(new Dimension(800, 100));
        table.setFillsViewportHeight(true);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        frame2.add(scrollPane);

        frame2.pack();
        this.setLocationRelativeTo(null); // Centers Program
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        logger.log(Level.INFO, "User in 'User List' window");

    }

    /**
     * Add user UI is designed to bring up the add new user UI, and allows the User to add a new user.
     */
    public void addUserUI() {
        JFrame frame = new JFrame("Adding new user");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        MainAppGUI x = new MainAppGUI();
        x.newPanelComponentUser(frame.getContentPane());

        //Display the window.
        frame.pack();
        this.setLocationRelativeTo(null); // Centers Program
        frame.setVisible(true);
        logger.log(Level.INFO, "User has entered 'Adding new user'");
    }

    /**
     * Update User Info is designed to allow the user to update the info of a person by entering the ID of said person,
     * by doing this they are able to edit infomration such as first name, last name, address, and phone number.
     */

    public void updateUserInfoUI() {
        JFrame frame = new JFrame("Update a user");
        JPanel panel = new JPanel();
        JTextField entry = new JTextField(12);
        JLabel id_name = new JLabel("Enter the ID# of the user to be updated: ");
        JButton submit = new JButton("Submit");

        panel.add(id_name);
        panel.add(entry);
        panel.add(submit);

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                logger.log(Level.INFO, "User pressed 'Submit'");
                int q = 0;
                for (int i = 0; i < db.getUserDatabaseSize(); ++i, q++) {
                    try {
                        if (db.getUserAtPosition(i).getId() == Integer.parseInt(entry.getText())) {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        Container frame = panel.getParent();
                        do {
                            frame = frame.getParent();
                        } while (!(frame instanceof JFrame));
                        JOptionPane.showMessageDialog(frame, "Error you have enter an invalid character"
                                , "Failure!",
                                JOptionPane.ERROR_MESSAGE);
                        logger.log(Level.SEVERE, "User submitted non-integer values", e);

                    } catch (Exception ex) {
                        Container frame = panel.getParent();
                        do {
                            frame = frame.getParent();
                        } while (!(frame instanceof JFrame));
                        JOptionPane.showMessageDialog(frame, "You literally broke the program! Congratulations you won " +
                                        "An unknown error has occurred!", "Critical Failure!",
                                JOptionPane.ERROR_MESSAGE);
                        logger.log(Level.SEVERE, "Unknown exception occurred", ex);

                    }
                }
                final User temp = db.getUserAtPosition(q);
                if (temp == null) {
                    JOptionPane.showMessageDialog(frame, "User ID not found!", "Failure!",
                            JOptionPane.ERROR_MESSAGE);
                    logger.log(Level.INFO, "User did not enter a valid ID#");
                    return;
                } else {
                    JPanel subpanel = new JPanel(new GridLayout(5, 1, 2, 5));
                    JLabel first = new JLabel("First Name: ");
                    JTextField firstname = new JTextField(temp.getFirstName(), 12);
                    JLabel last = new JLabel("Last Name: ");
                    JTextField lastname = new JTextField(temp.getLastName(), 12);
                    JButton sub_submit = new JButton("Submit Changes");
                    JLabel phoneNum = new JLabel("Phone Number: ");
                    JTextField phoneNumb = new JTextField(12);
                    JLabel uAdd = new JLabel("Customers Address: ");
                    JTextField uAddress = new JTextField(12);
                    JLabel salary = new JLabel("Monthly Salary: ");
                    JTextField salaryTF = new JTextField(12);
                    JLabel bank = new JLabel("Bank Account #: ");
                    JTextField bankTF = new JTextField(12);
                    JLabel type = new JLabel();

                    subpanel.add(first);
                    subpanel.add(firstname);
                    subpanel.add(last);
                    subpanel.add(lastname);

                    if (temp instanceof Customer) {
                        phoneNumb.setText(((Customer) temp).getPhoneNumber());
                        uAddress.setText((((Customer) temp).getAddress()));
                        type.setText("Type: Customer");

                        subpanel.add(phoneNum);
                        subpanel.add(phoneNumb);
                        subpanel.add(uAdd);
                        subpanel.add(uAddress);
                    } else {
                        salaryTF.setText(Float.toString(((Employee) temp).getMonthlySalary()));
                        bankTF.setText(Integer.toString(((Employee) temp).getBankAccountNumber()));
                        type.setText("Type: Employee");

                        subpanel.add(salary);
                        subpanel.add(salaryTF);
                        subpanel.add(bank);
                        subpanel.add(bankTF);
                    }

                    subpanel.add(sub_submit);
                    subpanel.add(type);

                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(subpanel);
                    frame.validate();
                    frame.pack();

                    sub_submit.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent x) {
                            temp.setFirstName(firstname.getText());
                            temp.setLastName(lastname.getText());
                            if (temp instanceof Customer) {
                                ((Customer) temp).setPhoneNumber(phoneNumb.getText());
                                ((Customer) temp).setAddress((uAddress.getText()));
                            } else {
                                ((Employee) temp).setMonthlySalary(Float.parseFloat(salaryTF.getText()));
                                ((Employee) temp).setBankAccountNumber(Integer.parseInt(bankTF.getText()));
                            }
                            JOptionPane.showMessageDialog(frame, "User has been successfully updated!", "Success!",
                                    JOptionPane.INFORMATION_MESSAGE);
                            logger.log(Level.INFO, "User able to update a user");
                            frame.dispose();
                        }
                    });
                }
            }
        });

        frame.setContentPane(panel);
        frame.pack();
        this.setLocationRelativeTo(null); // Centers Program
        frame.setVisible(true);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        logger.log(Level.INFO, "User entered 'Update User' operation");
    }


    /**
     * Deliver Package UI is designed to allow the user to process a delivery, by entering information such as the
     * customer id, employee id, transaction number, and the cost of the actual transaction.
     */

    public void deliverPackageUI() {
        JFrame frame = new JFrame("Package Delivery");

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        MainAppGUI x = new MainAppGUI();
        x.newPanelComponentDeliver(frame.getContentPane());

        //Display the window.
        frame.pack();
        this.setLocationRelativeTo(null); // Centers Program
        frame.setVisible(true);
        logger.log(Level.INFO, "User has entered 'Deliver Package'.");

    }

    /**
     * Show All Completed Transactions is designed to show all completed shipping transactions that have been processed,
     * and delivered by the systems standards.
     */
    public void showAllCompletedTransactionsUI() {
        //Completed
        JFrame frame2 = new JFrame("Transaction List");

        String[] header = {"CUSTOMER ID", "EMPLOYEE ID", "PTN", "SHIPPING DATE", "DELIVERY DATE", "PRICE"};
        Object[][] data = new Object[db.getTransactionList().size()][header.length];
        try {
            // Do something
            for (int i = 0; i < db.getTransactionList().size(); ++i) {

                data[i][0] = db.getTransactionList().get(i).getCustomerId();
                data[i][1] = db.getTransactionList().get(i).getEmployeeId();
                data[i][2] = db.getTransactionList().get(i).getPtn();
                data[i][3] = db.getTransactionList().get(i).getShippingDate();
                data[i][4] = db.getTransactionList().get(i).getDeliveryDate();
                data[i][5] = db.getTransactionList().get(i).getPrice();

            }
        } catch (ClassCastException e) {
            logger.log(Level.SEVERE, "ClassCastException thrown, possible database corruption");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unknown exception thrown! See stack trace..", e);
            e.printStackTrace();
        }
        final JTable table = new JTable(data, header);
        table.setPreferredScrollableViewportSize(new Dimension(800, 100));
        table.setFillsViewportHeight(true);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        frame2.add(scrollPane);

        frame2.pack();
        this.setLocationRelativeTo(null); // Centers Program
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        logger.log(Level.INFO, "User in 'All Transaction List' window");

    }

    /**
     * newPanelComponentUser is designed to do the backend work of adding a user to the database, and also handling the
     * creation of the actual interface in terms of how many rows, buttons, and fields that there is. Also this function
     * is designed to handle the error checking.
     * @param pane Transfers over the UI elements from the main JFRAME
     */
    public void newPanelComponentUser(Container pane) {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Panel for adding a new Customer
        JPanel card1 = new JPanel(new GridLayout(6, 1, 1, 1)) {
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += extraWindowWidth;
                return size;
            }
        };
        JLabel cFName = new JLabel("First Name (String)");
        JTextField cFNameTF = new JTextField("", 12);
        card1.add(cFName);
        card1.add(cFNameTF);
        JLabel cLName = new JLabel("Last Name (String)");
        JTextField cLNameTF = new JTextField("", 12);
        card1.add(cLName);
        card1.add(cLNameTF);
        JLabel cPhone = new JLabel("Phone Number (String)");
        JTextField cPhoneTF = new JTextField("", 12);
        card1.add(cPhone);
        card1.add(cPhoneTF);
        JLabel cDLN = new JLabel("Customer's Address (String)");
        JTextField cDLNTF = new JTextField("", 12);
        card1.add(cDLN);
        card1.add(cDLNTF);

        JButton cSUBMIT = new JButton("Submit");
        card1.add(cSUBMIT);
        JButton cCLEAR = new JButton("Clear");
        card1.add(cCLEAR);
        exitFrom = new JButton("Exit");
        card1.add(exitFrom);

        exitFrom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Container frame = exitFrom.getParent();
                do {
                    frame = frame.getParent();
                } while (!(frame instanceof JFrame));
                ((JFrame) frame).dispose();
            }
        });

        cSUBMIT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Thread qThread = new Thread() {
                    public void run() {
                        String first, last, phone;
                        String dl;
                        ArrayList<String> err = new ArrayList<>();

                        if (cFName.getText().length() == 0) {
                            err.add("Field 'First Name' is empty");
                            logger.log(Level.WARNING, "User submitted an empty field text (First Name)");
                        }

                        first = cFNameTF.getText();


                        if (cLNameTF.getText().length() == 0) {
                            err.add("Field 'Last Name' is empty");
                            logger.log(Level.WARNING, "User submitted an empty field (Last Name)");

                        }

                        last = cLNameTF.getText();

                        if (cPhoneTF.getText().length() == 0) {
                            err.add("Field 'Phone Number' is empty");
                            logger.log(Level.WARNING, "User submitted an empty field (Phone Number)");
                        }

                        phone = cPhoneTF.getText();

                        if (cDLNTF.getText().length() == 0) {
                            err.add("'Year' value is invalid");
                            logger.log(Level.WARNING, "User submitted empty field (Year)");
                        }

                        try {
                            dl = cDLNTF.getText();


                            if (err.isEmpty()) {
                                Customer newObj = new Customer(db.idGen(), first, last, phone, dl);
                                if (db.addUserDirectly(newObj)) {
                                    Container frame = card1.getParent();
                                    do {
                                        frame = frame.getParent();
                                    } while (!(frame instanceof JFrame));
                                    JOptionPane.showMessageDialog(frame, "User has been successfully added!\n",
                                            "Success!", JOptionPane.INFORMATION_MESSAGE);
                                    logger.log(Level.INFO, "New user added successfully");
                                } else {
                                    Container frame = card1.getParent();
                                    do {
                                        frame = frame.getParent();
                                    } while (!(frame instanceof JFrame));
                                    JOptionPane.showMessageDialog(frame, "addUserDirectly(User) method failed, " +
                                                    "despite criteria being met. An unknown error has occurred!", "Failure!",
                                            JOptionPane.ERROR_MESSAGE);
                                    logger.log(Level.SEVERE, "addUserDirectly(User) failed");
                                }
                            } else {
                                Container frame = card1.getParent();
                                do {
                                    frame = frame.getParent();
                                } while (!(frame instanceof JFrame));
                                for (String i : err) {
                                    JOptionPane.showMessageDialog(frame, i, "Failure!", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } catch (NumberFormatException e) {
                            logger.log(Level.SEVERE, "User submitted non-integer values", e);
                            e.printStackTrace();
                        } catch (Exception e) {
                            logger.log(Level.SEVERE, "Unknown exception thrown, refer to stack trace");
                            e.printStackTrace();
                        }
                    }
                };
                qThread.start();
            }
        });

        cCLEAR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.log(Level.INFO, "User cleared the form");
                cFNameTF.setText("");
                cLNameTF.setText("");
                cPhoneTF.setText("");
                cDLNTF.setText("");
            }
        });

        // Panel for adding a new Employee
        JPanel card2 = new JPanel(new GridLayout(7, 1, 1, 1)) {
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += extraWindowWidth;
                return size;
            }
        };
        JLabel eFName = new JLabel("First Name (String)");
        JTextField eFNameTF = new JTextField("", 12);
        card2.add(eFName);
        card2.add(eFNameTF);
        JLabel eLName = new JLabel("Last Name (String)");
        JTextField eLNameTF = new JTextField("", 12);
        card2.add(eLName);
        card2.add(eLNameTF);
        JLabel eSSN = new JLabel("Social Security Number (int)");
        JTextField eSSNTF = new JTextField("", 12);
        card2.add(eSSN);
        card2.add(eSSNTF);
        JLabel eSalary = new JLabel("Monthly Salary (float)");
        JTextField eSalaryTF = new JTextField("", 12);
        card2.add(eSalary);
        card2.add(eSalaryTF);
        JLabel eBAN = new JLabel("Bank Account # (int)");
        JTextField eBANTF = new JTextField("", 12);
        card2.add(eBAN);
        card2.add(eBANTF);
        JButton eSUBMIT = new JButton("Submit");
        card2.add(eSUBMIT);
        JButton eCLEAR = new JButton("Clear");
        card2.add(eCLEAR);
        exitFromUser = new JButton("Exit");
        card2.add(exitFromUser);

        exitFromUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.log(Level.INFO, "User is exiting the add user window");
                Container frame = exitFrom.getParent();
                do {
                    frame = frame.getParent();
                } while (!(frame instanceof JFrame));
                ((JFrame) frame).dispose();
            }
        });

        eSUBMIT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Thread qThread = new Thread() {
                    public void run() {
                        String first, last;
                        int bank, SSN;
                        float salary;
                        ArrayList<String> err = new ArrayList<>();

                        if (eFNameTF.getText().length() == 0) {
                            err.add("Field 'First Name' is empty");
                        }

                        first = eFNameTF.getText();


                        if (eLNameTF.getText().length() == 0) {
                            err.add("Field 'Last Name' is empty");
                        }

                        last = eLNameTF.getText();

                        if (eBANTF.getText().length() == 0) {
                            err.add("Field 'Bank Account #' is empty");
                        } else if (Integer.parseInt(eBANTF.getText()) < 0) {
                            err.add("Bank account number cannot be a negative value");
                        }

                        bank = Integer.parseInt(eBANTF.getText());

                        if (eSalaryTF.getText().length() == 0) {
                            err.add("Field 'Monthly Salary' is empty");
                        } else if (Float.parseFloat(eSalaryTF.getText()) < 0) {
                            err.add("Monthly Salary cannot be a negative value");
                        }

                        salary = Float.parseFloat(eSalaryTF.getText());
                        SSN = Integer.parseInt(eSSNTF.getText());

                        if (err.isEmpty()) {
                            Employee newObj = new Employee(db.idGen(), first, last, SSN, salary, bank);
                            if (db.addUserDirectly(newObj)) {
                                Container frame = card2.getParent();
                                do {
                                    frame = frame.getParent();
                                } while (!(frame instanceof JFrame));
                                JOptionPane.showMessageDialog(frame, "User has been successfully added!\n",
                                        "Success!", JOptionPane.INFORMATION_MESSAGE);
                                logger.log(Level.INFO, "User successfully adds a new Employee");
                            } else {
                                Container frame = card2.getParent();
                                do {
                                    frame = frame.getParent();
                                } while (!(frame instanceof JFrame));
                                JOptionPane.showMessageDialog(frame, "addUserDirectly(User) method failed, " +
                                                "despite criteria being met. An unknown error has occurred!", "Failure!",
                                        JOptionPane.ERROR_MESSAGE);
                                logger.log(Level.SEVERE, "Unable to add a new user despite criteria being met in addUserDirectly()");
                            }
                        } else {
                            Container frame = card2.getParent();
                            do {
                                frame = frame.getParent();
                            } while (!(frame instanceof JFrame));
                            for (String i : err) {
                                JOptionPane.showMessageDialog(frame, i, "Failure!", JOptionPane.ERROR_MESSAGE);
                                logger.log(Level.WARNING, "Program displaying error message logs to user");
                            }
                        }
                    }
                };
                qThread.start();
            }
        });

        eCLEAR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.log(Level.INFO, "User has cleared the form");
                eFNameTF.setText("");
                eLNameTF.setText("");
                eBANTF.setText("");
                eSSNTF.setText("");
                eSalaryTF.setText("");
            }
        });

        tabbedPane.addTab(CUSTOMERPANEL, card1);
        tabbedPane.addTab(EMPLOYEEPANEL, card2);

        pane.add(tabbedPane, BorderLayout.WEST);
    }

    @SuppressWarnings("Duplicates")

    /**
     * newPanelComponentDeliver is designed to handle the backend of all the package delivery system by deleting it out
     * of the shippingStore database, and moving it over to the completed transactions.
     * @param pane Transfers over the UI elements form the main JFRAME
     */
    public void newPanelComponentDeliver(Container pane) {
        // Panel for adding a new Customer
        JPanel card1 = new JPanel(new GridLayout(6, 1, 1, 1)) {
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += extraWindowWidth;
                return size;
            }
        };

        JLabel cFName = new JLabel("CustomerID (Int)");
        JTextField cFNameTF = new JTextField("", 12);
        card1.add(cFName);
        card1.add(cFNameTF);

        JLabel cLName = new JLabel("EmployeeID (Int)");
        JTextField cLNameTF = new JTextField("", 12);
        card1.add(cLName);
        card1.add(cLNameTF);

        JLabel cTrackingNumber = new JLabel("Tracking # (Int)");
        JTextField cTrackingNumberTF = new JTextField("", 12);
        card1.add(cTrackingNumber);
        card1.add(cTrackingNumberTF);

        JLabel cPrice = new JLabel("Price (float)");
        JTextField cPriceTF = new JTextField("", 12);
        card1.add(cPrice);
        card1.add(cPriceTF);

        JButton cSUBMIT = new JButton("Submit");
        card1.add(cSUBMIT);

        JButton cCLEAR = new JButton("Clear");
        card1.add(cCLEAR);

        exitFrom = new JButton("Exit");
        card1.add(exitFrom);

        cSUBMIT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Thread qThread = new Thread() {
                    public void run() {{
                        int CustomerID, EmployeeID;
                        String trackingNumber;
                        float price;


                        ArrayList<String> err = new ArrayList<>();

                        if (cFName.getText().length() == 0) {
                            err.add("Field 'CustomerID' is empty");
                            logger.log(Level.WARNING, "User submitted an empty field text (CustomerID)");
                        }

                        CustomerID = Integer.parseInt(cFNameTF.getText());


                        if (cLNameTF.getText().length() == 0) {
                            err.add("Field 'EmployeeID' is empty");
                            logger.log(Level.WARNING, "User submitted an empty field (EmployeeID)");

                        }

                        EmployeeID = Integer.parseInt(cLNameTF.getText());

                        if (cTrackingNumberTF.getText().length() == 0) {
                            err.add("Field 'Tracking #' is empty");
                            logger.log(Level.WARNING, "User submitted an empty field (Tracking #)");
                        }

                        trackingNumber = cTrackingNumberTF.getText();

                        if (cPriceTF.getText().length() == 0) {
                            err.add("'Price' value is invalid");
                            logger.log(Level.WARNING, "User submitted empty field (Price)");
                        }

                        if(cPriceTF.getText().length() == 0 && cTrackingNumberTF.getText().length() == 0
                                && cLNameTF.getText().length() == 0 && cFNameTF.getText().length() == 0) {
                            Container frame = card1.getParent();
                            do {
                                frame = frame.getParent();
                            } while (!(frame instanceof JFrame));
                            JOptionPane.showMessageDialog(frame, "Error you have not entered anything"
                                    , "Failure!",
                                    JOptionPane.ERROR_MESSAGE);
                            logger.log(Level.SEVERE, "User left all fields blank", e);
                        }

                        price = Float.parseFloat(cPriceTF.getText());

                        if(err.isEmpty()){
                            try {
                                // Adding package to the transaction store
                                db.addShppingTransaction(CustomerID,EmployeeID,trackingNumber,today,today,price);
                                db.deletePackage(trackingNumber);

                                Container frame = pane.getParent();

                                do {
                                    frame = frame.getParent();
                                } while (!(frame instanceof JFrame));
                                JOptionPane.showMessageDialog(frame, "Package has been successfully added!\n",
                                        "Success!", JOptionPane.INFORMATION_MESSAGE);
                                logger.log(Level.INFO, "User able to successfully add an Envelope object");
                            } catch (Exception e) {
                                Container frame = pane.getParent();
                                do {
                                    frame = frame.getParent();
                                } while (!(frame instanceof JFrame));
                                JOptionPane.showMessageDialog(frame, "Adding transaction  method failed, " +
                                                "despite criteria being met. An unknown error has occurred!", "Failure!",
                                        JOptionPane.ERROR_MESSAGE);

                                logger.log(Level.SEVERE, "db.addEnvelope(tracking, specification, mailing, " +
                                        "height, width);\n() failed unexpectedly");

                                do {
                                    frame = frame.getParent();
                                } while (!(frame instanceof JFrame));
                                for (String i : err) {
                                    JOptionPane.showMessageDialog(frame, i, "Failure!", JOptionPane.ERROR_MESSAGE);
                                    logger.log(Level.INFO, "Message dialog shown to user indicating bad input" + e.toString());
                                }
                            }
                        }

                    }}
                };
                qThread.start();
            }
        });

        exitFrom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.log(Level.INFO, "User exited from creating a package via exit JButton");
                Container frame = exitFrom.getParent();
                do {
                    frame = frame.getParent();
                } while (!(frame instanceof JFrame));
                ((JFrame) frame).dispose();
            }
        });

        cCLEAR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cFNameTF.setText("");
                cPriceTF.setText("");
                cLNameTF.setText("");
                cTrackingNumberTF.setText("");
            }
        });



        pane.add(card1, BorderLayout.WEST);
    }

    /**
     * main() is the initializer and executes the GUI on an EDT as opposed to the main thread
     *
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainAppGUI execute = new MainAppGUI("Shipping Store Management Software");
            execute.setVisible(true);
        });
    }

    /**
     * The function ClosesOP is designed to close out of the program, and save the information that was changed or
     * modified within the program into a serializable fil.
     *
     * @return returns value of 3 to close the program, this should never return a value, if it does a SEVERE error has
     * occurred.
     * @throws Exception The exception in this case is to handle if the closing of the program fails and, allows the
     *                   program to still run.
     */
    public int closeOP() throws Exception {
        try {
            db.writeDatabase();
            logger.log(Level.INFO, "User has closed the program via 'Exit' in main menu, exit successful!");

            System.exit(0);
            return JFrame.EXIT_ON_CLOSE;
        } catch (Exception e) {
            System.out.println("PROGRAM SHOULD NEVER REACH THIS POINT UNDERNEATH ANY CIRCUMSTANCE");
            logger.log(Level.SEVERE, e.toString());
        }
        return JFrame.EXIT_ON_CLOSE;
    }

    /**
     * newPanelComponentPackage is designed to display all of the options to handle adding a new package to the system.
     * Implementing adding a new package interface,
     * @param pane
     */

    @SuppressWarnings("Duplicates")
    public void newPanelComponentPackage(Container pane) {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Panel for adding a new package
        JPanel envelopeTab = new JPanel(new GridLayout(7, 1, 1, 1)) {
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += extraWindowWidth;
                return size;
            }
        };

        // Tracking number input
        JLabel track = new JLabel("Tracking Number (string)");
        JTextField trackINPUT = new JTextField("", 12);
        envelopeTab.add(track);
        envelopeTab.add(trackINPUT);

        // Specification field
        JLabel spec = new JLabel("Specification [Fragile, Books, Catalogs, Do-not-bend, or N/A] (string)");
        JTextField specINPUT = new JTextField("", 12);
        envelopeTab.add(spec);
        envelopeTab.add(specINPUT);

        // Mailing Class field
        JLabel mail = new JLabel("Mailing Class [First-Class, Priority, Retail, Ground, or Metro] (string)");
        JTextField mailINPUT = new JTextField("", 12);
        envelopeTab.add(mail);
        envelopeTab.add(mailINPUT);

        // Height Class Field
        JLabel height = new JLabel("Enter largest dimension (int)");
        JTextField heightINPUT = new JTextField("", 12);
        envelopeTab.add(height);
        envelopeTab.add(heightINPUT);

        // Width Input Field
        JLabel width = new JLabel("Width (int)");
        JTextField widthINPUT = new JTextField("", 12);
        envelopeTab.add(width);
        envelopeTab.add(widthINPUT);

        // Submit User Action field
        JButton envelopeSubmit = new JButton("Submit");
        envelopeTab.add(envelopeSubmit);

        // Clear all Fields Button
        JButton dbClear = new JButton("Clear");
        envelopeTab.add(dbClear);

        // Exit button
        exitFrom = new JButton("Exit");
        envelopeTab.add(exitFrom);

        // Button for exiting out of that tab
        exitFrom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.log(Level.INFO, "User exited from the adding package window.");
                Container frame = exitFrom.getParent();
                do {
                    frame = frame.getParent();
                } while (!(frame instanceof JFrame));
                ((JFrame) frame).dispose();
            }
        });

        // Handling the button to add to the shippingStore
        envelopeSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                {
                    Thread qThread = new Thread() {
                        public void run() {
                            String tracking, specification, mailing;
                            int height, width;

                            ArrayList<String> errorArray = new ArrayList<>();

                            if (trackINPUT.getText().length() == 0) {
                                errorArray.add("Field 'tracking number' is empty");
                                logger.log(Level.WARNING, "User submitted empty field, tracking number");
                            } else if (trackINPUT.getText().length() != 5) {
                                errorArray.add("'VIN' length is too long!");
                                logger.log(Level.WARNING, "User submitted String that is too long");
                            } else if (db.packageExists(trackINPUT.getText())) {
                                errorArray.add("Package already exists in the database!");
                                logger.log(Level.WARNING, "User submitted package that already exists");
                            }

                            // Setting the tracking number
                            tracking = trackINPUT.getText();


                            if (specINPUT.getText().length() == 0) {
                                errorArray.add("Field 'Specification' is empty");
                                logger.log(Level.WARNING, "User submitted an empty field, specification");
                            }

                            specification = specINPUT.getText();

                            if (mailINPUT.getText().length() == 0) {
                                errorArray.add("Field 'Mailing Class' is empty");
                                logger.log(Level.WARNING, "User submitted an empty field, Mailing Class");
                            }

                            mailing = mailINPUT.getText();


                            if (heightINPUT.getText().length() == 0) {
                                errorArray.add("Field 'Height' is empty");
                                logger.log(Level.WARNING, "User submitted an empty field, height");
                            } else if (Integer.parseInt(heightINPUT.getText()) <= 0) {
                                errorArray.add("Field 'Height' is negative or is zero");
                                logger.log(Level.WARNING, "User submitted an empty field, height");
                            }

                            height = Integer.parseInt(heightINPUT.getText());

                            if (widthINPUT.getText().length() == 0) {
                                errorArray.add("Field 'Width' is empty");
                                logger.log(Level.WARNING, "User submitted an empty field, height");
                            } else if (Integer.parseInt(widthINPUT.getText()) <= 0) {
                                errorArray.add("Field 'Height' is negative or is zero");
                                logger.log(Level.WARNING, "User submitted an empty field, height");
                            }

                            width = Integer.parseInt(widthINPUT.getText());

                            if (errorArray.isEmpty()) {
                                try {
                                    // Adding package to the shipping store
                                    db.addEnvelope(tracking, specification, mailing, height, width);

                                    Container frame = envelopeTab.getParent();

                                    do {
                                        frame = frame.getParent();
                                    } while (!(frame instanceof JFrame));
                                    JOptionPane.showMessageDialog(frame, "Package has been successfully added!\n",
                                            "Success!", JOptionPane.INFORMATION_MESSAGE);
                                    logger.log(Level.INFO, "User able to successfully add an Envelope object");
                                } catch (Exception e) {
                                    Container frame = envelopeTab.getParent();
                                    do {
                                        frame = frame.getParent();
                                    } while (!(frame instanceof JFrame));
                                    JOptionPane.showMessageDialog(frame, "Adding envelope  method failed, " +
                                                    "despite criteria being met. An unknown error has occurred!", "Failure!",
                                            JOptionPane.ERROR_MESSAGE);

                                    logger.log(Level.SEVERE, "db.addEnvelope(tracking, specification, mailing, " +
                                            "height, width);\n() failed unexpectedly");

                                    do {
                                        frame = frame.getParent();
                                    } while (!(frame instanceof JFrame));
                                    for (String i : errorArray) {
                                        JOptionPane.showMessageDialog(frame, i, "Failure!", JOptionPane.ERROR_MESSAGE);
                                        logger.log(Level.INFO, "Message dialog shown to user indicating bad input" + e.toString());
                                    }
                                }
                            }
                        }
                    };
                    qThread.start();
                }
            }
        });

        dbClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.log(Level.INFO, "User interacted with clear form button");
                trackINPUT.setText("");
                specINPUT.setText("");
                mailINPUT.setText("");
                widthINPUT.setText("");
                heightINPUT.setText("");
            }
        });

        // Panel for adding a new package
        JPanel boxTab = new JPanel(new GridLayout(7, 1, 1, 1)) {
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += extraWindowWidth;
                return size;
            }
        };


        JLabel bTrack = new JLabel("Tracking Number (string)");
        JTextField bTrackInput = new JTextField("", 12);
        boxTab.add(bTrack);
        boxTab.add(bTrackInput);

        JLabel bSpec = new JLabel("Specification [Fragile, Books, Catalogs, Do-not-bend, or N/A] (string)");
        JTextField bSpecT = new JTextField("", 12);
        boxTab.add(bSpec);
        boxTab.add(bSpecT);

        JLabel bMail = new JLabel("Mailing class can be First-Class, Priority, Retail, Ground, or Metro");
        JTextField bMailT = new JTextField("", 12);
        boxTab.add(bMail);
        boxTab.add(bMailT);

        JLabel bDimension = new JLabel("Largest dimension (int)");
        JTextField bDimensionT = new JTextField("", 12);
        boxTab.add(bDimension);
        boxTab.add(bDimensionT);

        JLabel bVolume = new JLabel("Enter volume (int)");
        JTextField bVolumeT = new JTextField("", 12);
        boxTab.add(bVolume);
        boxTab.add(bVolumeT);

        JButton bSubmit = new JButton("Submit");
        boxTab.add(bSubmit);
        JButton bClear = new JButton("Clear");
        boxTab.add(bClear);
        exitFromPackage = new JButton("Exit");
        boxTab.add(exitFromPackage);


        exitFromPackage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.log(Level.INFO, "User exited from creating a package via exit JButton");
                Container frame = exitFrom.getParent();
                do {
                    frame = frame.getParent();
                } while (!(frame instanceof JFrame));
                ((JFrame) frame).dispose();
            }
        });

        // Adding a box to the package store
        bSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Thread qThread = new Thread() {
                    public void run() {

                        String tracking, specification, mailing;
                        int dimension, volume;

                        ArrayList<String> err = new ArrayList<>();

                        if (bTrackInput.getText().length() == 0) {
                            err.add("Field 'Tracking number' is empty");
                            logger.log(Level.WARNING, "User submitted empty field, tracking number");
                        } else if (bTrackInput.getText().length() != 5) {
                            err.add("'tracking number' length is not 5 characters!");
                            logger.log(Level.WARNING, "User submitted tracking number that is wrong length");
                        } else if (db.packageExists(bTrackInput.getText())) {
                            err.add("'VIN' already exists in the database!");
                            logger.log(Level.WARNING, "Package already exists, invalid tracking number to add");
                        }

                        tracking = bTrackInput.getText();

                        if (bSpecT.getText().length() == 0) {
                            err.add("Field 'Specification' is empty");
                            logger.log(Level.WARNING, "User submitted field that is empty, Make");
                        }

                        specification = bSpecT.getText();

                        if (bMailT.getText().length() == 0) {
                            err.add("Field 'Model' is empty");
                            logger.log(Level.WARNING, "JTextField is empty, Model");
                        }

                        mailing = bMailT.getText();

                        if (bDimensionT.getText().length() == 0) {
                            err.add("'Dimension' value is empty");
                            logger.log(Level.WARNING, "Dimension field is empty");
                        } else if (Integer.parseInt(bDimensionT.getText()) < 0) {
                            err.add("'Year' cannot be a negative value");
                            logger.log(Level.WARNING, "User submitted negative value");
                        }

                        dimension = Integer.parseInt(bDimensionT.getText());

                        if (bVolumeT.getText().length() == 0) {
                            err.add("Field 'Volume' is empty");
                            logger.log(Level.WARNING, "User submitted field that is empty, volume");
                        } else if (Integer.parseInt(bVolumeT.getText()) < 0) {
                            err.add("'Volume' cannot be a negative value");
                            logger.log(Level.WARNING, "User submitted a negative value for volume");
                        }

                        volume = Integer.parseInt(bVolumeT.getText());

                        if (err.isEmpty()) {
                            db.addBox(tracking, specification, mailing, dimension, volume);

                            if (db.findPackage(tracking).ptn == tracking) {
                                Container frame = boxTab.getParent();
                                do {
                                    frame = frame.getParent();
                                } while (!(frame instanceof JFrame));
                                JOptionPane.showMessageDialog(frame, "Box has been successfully added!\n",
                                        "Success!", JOptionPane.INFORMATION_MESSAGE);
                                logger.log(Level.INFO, "Box object added successfully!");
                            } else {
                                Container frame = boxTab.getParent();
                                do {
                                    frame = frame.getParent();
                                } while (!(frame instanceof JFrame));
                                JOptionPane.showMessageDialog(frame, "box adding method failed, " +
                                                "despite criteria being met. An unknown error has occurred!", "Failure!",
                                        JOptionPane.ERROR_MESSAGE);
                                logger.log(Level.INFO, "box failed unexpectedly");
                            }
                        } else {
                            Container frame = boxTab.getParent();
                            do {
                                frame = frame.getParent();
                            } while (!(frame instanceof JFrame));
                            for (String i : err) {
                                JOptionPane.showMessageDialog(frame, i, "Failure!", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                };
                qThread.start();
            }
        });


        bClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.log(Level.INFO, "User cleared the form for adding a package");
                bTrackInput.setText("");
                bMailT.setText("");
                bSpecT.setText("");
                bVolumeT.setText("");
                bDimensionT.setText("");
            }
        });

        // Panel for adding a new package
        JPanel cratePanel = new JPanel(new GridLayout(7, 1, 1, 1)) {
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += extraWindowWidth;
                return size;
            }
        };

        JLabel cTrack = new JLabel("Enter tracking number (string)");
        JTextField cTrackINPUT = new JTextField("", 12);
        cratePanel.add(cTrack);
        cratePanel.add(cTrackINPUT);

        JLabel cSpec = new JLabel("Specification [Fragile, Books, Catalogs, Do-not-bend, or N/A] (string)");
        JTextField cSpecINPUT = new JTextField("", 12);
        cratePanel.add(cSpec);
        cratePanel.add(cSpecINPUT);

        JLabel cMailing = new JLabel("Mailing class be First-Class, Priority, Retail, Ground, or Metro (string)");
        JTextField cMailingINPUT = new JTextField("", 12);
        cratePanel.add(cMailing);
        cratePanel.add(cMailingINPUT);

        JLabel cWeight = new JLabel("Maximum load weight (float)");
        JTextField cWeightINPUT = new JTextField("", 12);
        cratePanel.add(cWeight);
        cratePanel.add(cWeightINPUT);

        JLabel cContent = new JLabel("Content (string)");
        JTextField cContentINPUT = new JTextField("", 12);
        cratePanel.add(cContent);
        cratePanel.add(cContentINPUT);


        JButton cSUBMIT = new JButton("Submit");
        cratePanel.add(cSUBMIT);
        JButton cCLEAR = new JButton("Clear");
        cratePanel.add(cCLEAR);
        exitFromPackage = new JButton("Exit");
        cratePanel.add(exitFromPackage);

        cSUBMIT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Thread qThread = new Thread() {
                    public void run() {

                        String tracking, spec, mailing, content;
                        float weight;

                        ArrayList<String> err = new ArrayList<>();

                        if (cTrackINPUT.getText().length() == 0) {
                            err.add("Field 'tracking number' is empty");
                            logger.log(Level.WARNING, "Submission of empty field attempted");
                        } else if (cTrackINPUT.getText().length() != 5) {
                            err.add("'tracking number' length is needs to be 5!");
                            logger.log(Level.WARNING, "Number is not 5 bad");
                        } else if (db.packageExists(cTrackINPUT.getText())) {
                            err.add("'tracking number' already exists in the database!");
                            logger.log(Level.WARNING, "tracking# already exists");
                        }

                        tracking = cTrackINPUT.getText();

                        if (cSpecINPUT.getText().length() == 0) {
                            err.add("Field 'Specification' is empty");
                            logger.log(Level.WARNING, "Submission of empty field attempted");
                        }

                        spec = cSpecINPUT.getText();

                        if (cMailingINPUT.getText().length() == 0) {
                            err.add("Field 'Mailing' is empty");
                            logger.log(Level.WARNING, "Submission of empty field attempted");
                        }

                        mailing = cMailingINPUT.getText();

                        if (Float.parseFloat(cWeightINPUT.getText()) < 0) {
                            err.add("'Weight' cannot be a negative value");
                            logger.log(Level.WARNING, "Year cannot be negative");
                        } else if (cWeightINPUT.getText().length() < 1) {
                            err.add("'Weight' is not within the acceptable range");
                            logger.log(Level.WARNING, "Not within acceptable range for weight");
                        }

                        weight = Float.parseFloat(cWeightINPUT.getText());

                        if (cContentINPUT.getText().length() == 0) {
                            err.add("Field 'Content' is empty");
                            logger.log(Level.WARNING, "Submission of empty field attempted");
                        }

                        content = cContentINPUT.getText();

                        if (err.isEmpty()) {
                            db.addCrate(tracking, spec, mailing, weight, content);
                            if (db.findPackage(tracking).ptn.matches(tracking)) {
                                Container frame = cratePanel.getParent();
                                do {
                                    frame = frame.getParent();
                                } while (!(frame instanceof JFrame));
                                JOptionPane.showMessageDialog(frame, "Crate has been successfully added!\n",
                                        "Success!", JOptionPane.INFORMATION_MESSAGE);
                                logger.log(Level.INFO, "User added a crate object successfully");
                            } else {
                                Container frame = cratePanel.getParent();
                                do {
                                    frame = frame.getParent();
                                } while (!(frame instanceof JFrame));
                                JOptionPane.showMessageDialog(frame, "addCrate method failed, " +
                                                "despite criteria being met. An unknown error has occurred!", "Failure!",
                                        JOptionPane.ERROR_MESSAGE);
                                logger.log(Level.SEVERE, "crate failed");
                            }
                        } else {
                            Container frame = cratePanel.getParent();
                            do {
                                frame = frame.getParent();
                            } while (!(frame instanceof JFrame));
                            for (String i : err) {
                                JOptionPane.showMessageDialog(frame, i, "Failure!", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                };
                qThread.start();
            }
        });

        exitFromPackage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.log(Level.INFO, "User exited from creating a package via exit JButton");
                Container frame = exitFrom.getParent();
                do {
                    frame = frame.getParent();
                } while (!(frame instanceof JFrame));
                ((JFrame) frame).dispose();
            }
        });

        cCLEAR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cTrackINPUT.setText("");
                cSpecINPUT.setText("");
                cMailingINPUT.setText("");
                cWeightINPUT.setText("");
                cContentINPUT.setText("");
            }
        });

        JPanel drumPanel = new JPanel(new GridLayout(7, 1, 1, 1)) {
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += extraWindowWidth;
                return size;
            }
        };

        JLabel drumTrackingNumber = new JLabel("Tracking number (string)");
        JTextField dTrackingINPUT = new JTextField("", 12);
        drumPanel.add(drumTrackingNumber);
        drumPanel.add(dTrackingINPUT);

        JLabel drumSpecification = new JLabel("Specification: Fragile, Books, Catalogs, Do-not-bend, or N/A");
        JTextField drumSpecINPUT = new JTextField("", 12);
        drumPanel.add(drumSpecification);
        drumPanel.add(drumSpecINPUT);

        JLabel drumMailing = new JLabel("Mailing: First-Class, Priority, Retail, Ground, or Metro (string)");
        JTextField drumMailingINPUT = new JTextField("", 12);
        drumPanel.add(drumMailing);
        drumPanel.add(drumMailingINPUT);

        JLabel drumMaterial = new JLabel("Enter material: (Plastic / Fiber) ");
        JTextField drumMaterialINPUT = new JTextField("", 12);
        drumPanel.add(drumMaterial);
        drumPanel.add(drumMaterialINPUT);

        JLabel drumDiameter = new JLabel("Enter diameter (float):");
        JTextField drumDiameterINPUT = new JTextField("", 12);
        drumPanel.add(drumDiameter);
        drumPanel.add(drumDiameterINPUT);

        JButton drumSubmit = new JButton("Submit");
        drumPanel.add(drumSubmit);

        JButton drumClear = new JButton("Clear");
        drumPanel.add(drumClear);

        exitFromPackage = new JButton("Exit");
        drumPanel.add(exitFromPackage);

        drumSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Thread qThread = new Thread() {
                    public void run() {

                        String tracking, spec, mailing, material;
                        float diameter;

                        ArrayList<String> err = new ArrayList<>();

                        if (dTrackingINPUT.getText().length() == 0) {
                            err.add("Field 'Tracking number' is empty");
                            logger.log(Level.WARNING, "Submission of empty field attempted");
                        } else if (dTrackingINPUT.getText().length() != 5) {
                            err.add("'Tracking number' length is needs to be 5!");
                            logger.log(Level.WARNING, "Number is not 5 bad!!!!!!!!!!!");
                        } else if (db.packageExists(dTrackingINPUT.getText())) {
                            err.add("'tracking number' already exists in the database!");
                            logger.log(Level.WARNING, "tracking# already exists");
                        }

                        tracking = dTrackingINPUT.getText();

                        if (drumSpecINPUT.getText().length() == 0) {
                            err.add("Field 'Specification' is empty");
                            logger.log(Level.WARNING, "Submission of empty field attempted");
                        }

                        spec = drumSpecINPUT.getText();

                        if (drumMailingINPUT.getText().length() == 0) {
                            err.add("Field 'Mailing' is empty");
                            logger.log(Level.WARNING, "Submission of empty field attempted");
                        }

                        mailing = drumMailingINPUT.getText();


                        if (drumMaterialINPUT.getText().length() == 0) {
                            err.add("Field is empty");
                            logger.log(Level.WARNING, "Empty field detected");
                        }

                        material = drumMaterialINPUT.getText();

                        if (drumDiameterINPUT.getText().length() == 0) {
                            err.add("Field is empty");
                            logger.log(Level.WARNING, "Empty field detected");
                        } else if (Float.parseFloat(drumDiameterINPUT.getText()) <= 0) {
                            err.add("Number for drum diameter is less than 0");
                            logger.log(Level.WARNING, "Number is lower than 0");
                        }

                        diameter = Float.parseFloat(drumDiameterINPUT.getText());

                        if (err.isEmpty()) {
                            db.addDrum(tracking, spec, mailing, material, diameter);
                            if (db.findPackage(tracking).ptn.matches(tracking)) {
                                Container frame = drumPanel.getParent();
                                do {
                                    frame = frame.getParent();
                                } while (!(frame instanceof JFrame));
                                JOptionPane.showMessageDialog(frame, "Drum has been successfully added!\n",
                                        "Success!", JOptionPane.INFORMATION_MESSAGE);
                                logger.log(Level.INFO, "User added a drum object successfully");
                            } else {
                                Container frame = drumPanel.getParent();
                                do {
                                    frame = frame.getParent();
                                } while (!(frame instanceof JFrame));
                                JOptionPane.showMessageDialog(frame, "addDrum method failed, " +
                                                "despite criteria being met. An unknown error has occurred!", "Failure!",
                                        JOptionPane.ERROR_MESSAGE);
                                logger.log(Level.SEVERE, "drum critically failed");
                            }
                        } else {
                            Container frame = drumPanel.getParent();
                            do {
                                frame = frame.getParent();
                            } while (!(frame instanceof JFrame));
                            for (String i : err) {
                                JOptionPane.showMessageDialog(frame, i, "Failure!", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                };
                qThread.start();
            }
        });

        exitFromPackage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.log(Level.INFO, "User exited from creating a package via exit JButton");
                Container frame = exitFrom.getParent();
                do {
                    frame = frame.getParent();
                } while (!(frame instanceof JFrame));
                ((JFrame) frame).dispose();
            }
        });

        drumClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dTrackingINPUT.setText("");
                drumSpecINPUT.setText("");
                drumMailingINPUT.setText("");
                drumDiameterINPUT.setText("");
                drumMaterialINPUT.setText("");
            }
        });




        tabbedPane.addTab(ENVELOPEPANEL, envelopeTab);
        tabbedPane.addTab(BOXPANEL, boxTab);
        tabbedPane.addTab(CRATEPANEL, cratePanel);
        tabbedPane.addTab(DRUMPANEL, drumPanel);

        pane.add(tabbedPane, BorderLayout.WEST);
    }
}

