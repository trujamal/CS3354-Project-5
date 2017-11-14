import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

/**
 * @author Jamal Rasool (j_r771)
 * @author Zach Sotak (zs1046)
 * @version 1.0
 *
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

        public String getHead (Handler h) {
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
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "FileHandler threw IOException", e);
        }
    }

    /**
     * MainAppGUI is designed to set the information in each frame of the program, such as the title and the buttons
     * that are within the program.
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

        // Show list of transactions JButton
        JButton showListTransaction = new JButton("Show a list of transactions");
        showListTransaction.setHorizontalAlignment(SwingConstants.LEFT);

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
                field.setText("STATUS: in add user to database ...");
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
                field.setText("STATUS: adding user ...");
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
                logger.log(Level.INFO, "User pressed 'Update user info'");
                field.setText("STATUS: updating user ...");
                Thread qThread = new Thread() {
                    public void run() {
                        deliverPackageUI();
                    }
                };
                qThread.start();
            }
        });

        showListTransaction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                logger.log(Level.INFO, "User pressed 'Show a list of transactions'");
                field.setText("STATUS: deleting package ...");
                Thread qThread = new Thread() {
                    public void run() {
                        showListOfTransactionsUI();
                    }
                };
                qThread.start();
            }
        });

        showAllCompletedTransactions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                logger.log(Level.INFO, "User pressed 'Show a list of completed shipping transactions'");
                field.setText("STATUS: showing transactions ...");
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
                        closeOP();
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
        buttonPanel.add(deliverPackage);
        buttonPanel.add(addUser);
        buttonPanel.add(updateUserInfo);
        buttonPanel.add(showListTransaction);
        buttonPanel.add(showAllCompletedTransactions);
        buttonPanel.add(exit);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null); // Centers Program
        this.setVisible(true);

        logger.log(Level.INFO, "User has loaded main menu of GUI");
    }

    public void displayInventoryUI() {}

    public void addPackageUI() {}

    public void deletePackageUI() {}

    public void searchPackUI() {}

    public void listUsersUI() {}

    public void addUserUI() {}

    public void updateUserInfoUI() {}

    public void deliverPackageUI() {}

    public void showListOfTransactionsUI() {}

    public void showAllCompletedTransactionsUI() {}

    /**
     * main() is the initializer and executes the GUI on an EDT as opposed to the main thread
     * @param args
     */
    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainAppGUI execute = new MainAppGUI("Shipping Store Management Software");
            execute.setVisible(true);
        });
    }

    public int closeOP() {
        ss.writeDatabase();
        return JFrame.EXIT_ON_CLOSE;
    }
}
