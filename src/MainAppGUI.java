import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.*;
import java.util.Scanner;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

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
                        mainPanel.setVisible(false);
                        displayInventoryUI();
                        mainPanel.setVisible(true);
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
                        try {
                            closeOP();
                        }catch (Exception e) {
                            logger.log(Level.SEVERE,e.toString());
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

    // Jamal
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
              for (int  j = 0; i < db.getPackageList().size(); i++)
                  data[i][j] = db.getPackageList().get(i).display();

          }
      }
      catch (ClassCastException e) {
          logger.log(Level.SEVERE, "ClassCastException thrown, possible database corruption");
      }
      catch (Exception e) {
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
      display.setVisible(true);
      display.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      logger.log(Level.INFO, "User in 'Package List' window");
    }

    // Havent implemented yet
    //TODO: 11/14/17 IMPLEMENT EVERYTHING BELOW
    // Jamal
    public void addPackageUI() {
        JFrame frame = new JFrame("Adding package");
              frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

              //Display the window.
              frame.pack();
              frame.setVisible(true);

              logger.log(Level.INFO, "User in 'Adding Package' window");
    }

// Jamal
    public void deletePackageUI() {
      JFrame frame = new JFrame("Deleting Package");

      if (db.getPackageList().size() == 0) {
          JOptionPane.showMessageDialog(frame, "There is nothing to delete as the database\n" +
                          " is currently empty! Now exiting removal process..", "Failure!",
                  ERROR_MESSAGE);
          logger.log(Level.WARNING, "User attempted to view an empty list (vehicles)");
          return;
      }

      JPanel panel = new JPanel();
      JLabel instr = new JLabel("Enter the vehicle's VIN (to be deleted): ");
      JTextField VIN = new JTextField(12);
      JButton submit = new JButton("Submit");
      JButton exit = new JButton("Exit");

      frame.setContentPane(panel);
      panel.add(instr);
      panel.add(VIN);
      panel.add(submit);
      panel.add(exit);

      submit.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent ev) {
              logger.log(Level.INFO, "User submitted a VIN (string)");
              Thread qThread = new Thread() {
                  public void run() {
                      if (db.deletePackage(VIN.getText())) {
                          JOptionPane.showMessageDialog(frame, "Removal was successful!", "Success!",
                                  JOptionPane.INFORMATION_MESSAGE);
                          logger.log(Level.INFO, "User was able to remove a vehicle via string value");
                      }
                      else {
                          JOptionPane.showMessageDialog(frame, "Removal was unsuccessful! Please check your input" +
                                          " and try again!", "Failure!",
                                  ERROR_MESSAGE);
                          logger.log(Level.INFO, "User's search term was not found, nothing removed from vehicle" +
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
      frame.setVisible(true);
      logger.log(Level.INFO, "User opened up GUI option to delete a vehicle");

    }

    // Maria
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

                                    String intialText = db.getPackageList().get(i).toString();
                                    String finalText = "";
                                    System.out.println(trackingno.getText());
                                    System.out.println(db.getPackageList().get(i).ptn);
                                    if(db.getPackageList().get(i).ptn.equals(trackingno.getText())){
                                        System.out.println("GO");
                                        data[i][0] = db.getPackageList().get(i).getClass().getName();
                                        data[i][1] = db.getPackageList().get(i).ptn;
                                        data[i][2] = db.getPackageList().get(i).specification;
                                        data[i][3] = db.getPackageList().get(i).mailingClass.toString();
                                        data[i][4] = db.getPackageList().get(i).toString();

                                        break;
                                    }
                                }
                            }
                            catch (ClassCastException e) {
                                logger.log(Level.SEVERE, "ClassCastException thrown, possible database corruption");
                            }
                            catch (Exception e) {
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



                        }
                        else {
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
        frame1.setVisible(true);
        searchpanel.setVisible(true);
        logger.log(Level.INFO, "User opened up GUI option to search a package");

    }

    // Maria
    // Completed
    public void listUsersUI() {
        JFrame frame2 = new JFrame("User List");

        String[] header = {"USER TYPE", "USER ID", "FIRST NAME", "LAST NAME", "OTHER DETAILS"};
        Object[][] data = new Object[db.getUserList().size()][header.length];
        try {
            // Do something
            for (int i = 0; i < db.getUserList().size(); ++i) {

                String intialText = db.getUserList().get(i).toString();
                String finalText = "";
                System.out.println("GO");
                data[i][0] = db.getUserList().get(i).getClass().getName();
                data[i][1] = db.getUserList().get(i).getId();
                data[i][2] = db.getUserList().get(i).getFirstName();
                data[i][3] = db.getUserList().get(i).getLastName();
                data[i][4] = db.getUserList().get(i).toString();

            }
        }
        catch (ClassCastException e) {
            logger.log(Level.SEVERE, "ClassCastException thrown, possible database corruption");
        }
        catch (Exception e) {
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
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        logger.log(Level.INFO, "User in 'User List' window");

    }
    // Zach
    public void addUserUI() {}

    // zach
    public void updateUserInfoUI() {}

    // Krisof
    public void deliverPackageUI() {

    }

    // Kristof
    public void showListOfTransactionsUI() {}

    //
    public void showAllCompletedTransactionsUI() {
        //Completed
        JFrame frame2 = new JFrame("Transaction List");

        String[] header = {"CUSTOMER ID", "EMPLOYEE ID", "PTN", "SHIPPING DATE", "DELIVERY DATE", "PRICE"};
        Object[][] data = new Object[db.getTransactionList().size()][header.length];
        try {
            // Do something
            for (int i = 0; i < db.getTransactionList().size(); ++i) {

                String intialText = db.getTransactionList().get(i).toString();
                String finalText = "";
                System.out.println("GO");
                data[i][0] = db.getTransactionList().get(i).getCustomerId();
                data[i][1] = db.getTransactionList().get(i).getEmployeeId();
                data[i][2] = db.getTransactionList().get(i).getPtn();
                data[i][3] = db.getTransactionList().get(i).getShippingDate();
                data[i][4] = db.getTransactionList().get(i).getDeliveryDate();
                data[i][5] = db.getTransactionList().get(i).getPrice();

            }
        }
        catch (ClassCastException e) {
            logger.log(Level.SEVERE, "ClassCastException thrown, possible database corruption");
        }
        catch (Exception e) {
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
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        logger.log(Level.INFO, "User in 'All Transaction List' window");

    }

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

    /**
     * The function ClosesOP is designed to close out of the program, and save the information that was changed or
     * modified within the program into a serializable fil.
     * @return returns value of 3 to close the program, this should never return a value, if it does a SEVERE error has
     * occurred.
     * @throws Exception The exception in this case is to handle if the closing of the program fails and, allows the
     * program to still run.
     */
    public int closeOP() throws Exception {
        try {
            db.writeDatabase();
            logger.log(Level.INFO, "User has closed the program via 'Exit' in main menu, exit successful!");

            System.exit(0);
            return JFrame.EXIT_ON_CLOSE;
        } catch (Exception e){
            System.out.println("PROGRAM SHOULD NEVER REACH THIS POINT UNDERNEATH ANY CIRCUMSTANCE");
            logger.log(Level.SEVERE,e.toString());
        }
        return JFrame.EXIT_ON_CLOSE;
    }
}
