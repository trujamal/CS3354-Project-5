import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        JPanel buttonPanel = new JPanel(new GridLayout(11, 0));
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
        buttonPanel.add(listUsers);
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
                  JOptionPane.ERROR_MESSAGE);
          logger.log(Level.WARNING, "User attempted to view an empty database");
          return;
      }

      // Implement right header
      String[] header = {"PACKAGE TYPE", "TRACKING #", "SPECIFICATION", "MAILING CLASS", "OTHER DETAILS"};
      Object[][] data = new Object[db.getPackageList().size()][header.length];
      try {
          // Do something
          for (int i = 0; i < db.getPackageList().size(); ++i) {

              System.out.println(db.getPackageList().get(i));
              System.out.println(db.getAllPackagesFormatted());


              data[i][0] = db.getPackageList().get(i).getClass().getName();
              data[i][1] = db.getPackageList().get(i).ptn;
              data[i][2] = db.getPackageList().get(i).specification;
              data[i][3] = db.getPackageList().get(i).mailingClass;
              data[i][4] = db.getPackageList().get(i);

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


    /**
     * Add
     */
    public void addPackageUI() {
        JFrame frame = new JFrame("Adding package");
              frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        MainAppGUI n = new MainAppGUI();
        n.newPanelComponentPackage(frame.getContentPane());

              //Display the window.
              frame.pack();
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
                  JOptionPane.ERROR_MESSAGE);
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
                      }
                      else {
                          JOptionPane.showMessageDialog(frame, "Removal was unsuccessful! Please check your input" +
                                          " and try again!", "Failure!",
                                  JOptionPane.ERROR_MESSAGE);
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
    public void searchPackUI() {}

    // Maria
    public void listUsersUI() {}

    // Zach
    public void addUserUI() {}

    // zach
    public void updateUserInfoUI() {}

    // Krisof
    public void deliverPackageUI() {}

    // Kristof
    public void showListOfTransactionsUI() {}

    // ?
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

    public void newPanelComponentPackage(Container pane) {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Panel for adding a new passenger vehicle
        JPanel envelopeTab = new JPanel(new GridLayout(7,1,1,1)) {
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
        JButton envelopeSubmit = new JButton ("Submit");
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
            public void actionPerformed (ActionEvent e) {
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
                                    JOptionPane.showMessageDialog(frame, "Package has been successfully added!\n" +
                                                    "You may continue to add packages by pressing \"Ok\" \n" +
                                                    "or you may exit from this operation by pressing \"Ok\" (in this window)\n" +
                                                    "then \"Exit\" (in the 'Adding package' window)",
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

        // Panel for adding a new motorcycle
        JPanel boxTab = new JPanel(new GridLayout(7,1,1,1)) {
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

        JButton bSubmit = new JButton ("Submit");
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
            public void actionPerformed (ActionEvent e) {
                Thread qThread = new Thread() {
                    public void run() {

                        String tracking, specification, mailing;
                        int dimension, volume;

                        ArrayList<String> err = new ArrayList<>();

                        if (bTrackInput.getText().length() == 0) {
                            err.add("Field 'Tracking number' is empty");
                            logger.log(Level.WARNING, "User submitted empty field, tracking number");
                        }
                        else if (bTrackInput.getText().length() != 5) {
                            err.add("'tracking number' length is not 5 characters!");
                            logger.log(Level.WARNING, "User submitted tracking number that is wrong length");
                        }
                        else if (db.packageExists(bTrackInput.getText())) {
                            err.add("'VIN' already exists in the database!");
                            logger.log(Level.WARNING, "Vehicle already exists, invalid VIN to add");
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
                        }
                        else if (Integer.parseInt(bDimensionT.getText()) < 0) {
                            err.add("'Year' cannot be a negative value");
                            logger.log(Level.WARNING, "User submitted negative value");
                        }

                        dimension = Integer.parseInt(bDimensionT.getText());

                        if (bVolumeT.getText().length() == 0) {
                            err.add("Field 'Volume' is empty");
                            logger.log(Level.WARNING, "User submitted field that is empty, volume");
                        }
                        else if (Integer.parseInt(bVolumeT.getText()) < 0) {
                            err.add("'Volume' cannot be a negative value");
                            logger.log(Level.WARNING, "User submitted a negative value for volume");
                        }

                        volume = Integer.parseInt(bVolumeT.getText());

                        if (err.isEmpty()) {
                            db.addBox(tracking,specification,mailing,dimension,volume);

                            if (db.findPackage(tracking).ptn == tracking) {
                                Container frame = boxTab.getParent();
                                do {
                                    frame = frame.getParent();
                                } while (!(frame instanceof JFrame));
                                JOptionPane.showMessageDialog(frame, "Box has been successfully added!\n",
                                        "Success!", JOptionPane.INFORMATION_MESSAGE);
                                logger.log(Level.INFO, "Box object added successfully!");
                            }
                            else {
                                Container frame = boxTab.getParent();
                                do {
                                    frame = frame.getParent();
                                } while (!(frame instanceof JFrame));
                                JOptionPane.showMessageDialog(frame, "box adding method failed, " +
                                                "despite criteria being met. An unknown error has occurred!", "Failure!",
                                        JOptionPane.ERROR_MESSAGE);
                                logger.log(Level.INFO, "box failed unexpectedly");
                            }
                        }
                        else {
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
                logger.log(Level.INFO, "User cleared the form for adding a motorcycle");
                bTrackInput.setText("");
                bMailT.setText("");
                bSpecT.setText("");
                bVolumeT.setText("");
                bDimensionT.setText("");
            }
        });

        // Panel for adding a new truck
        JPanel cratePanel = new JPanel(new GridLayout(7,1,1,1)) {
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


        JButton cSUBMIT = new JButton ("Submit");
        cratePanel.add(cSUBMIT);
        JButton cCLEAR = new JButton("Clear");
        cratePanel.add(cCLEAR);
        exitFromPackage = new JButton("Exit");
        cratePanel.add(exitFromPackage);

        cSUBMIT.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                Thread qThread = new Thread() {
                    public void run() {

                        String tracking, spec, mailing, content;
                        float weight;

                        ArrayList<String> err = new ArrayList<>();

                        if (cTrackINPUT.getText().length() == 0) {
                            err.add("Field 'tracking number' is empty");
                            logger.log(Level.WARNING, "Submission of empty field attempted");
                        }
                        else if (cTrackINPUT.getText().length() != 5) {
                            err.add("'tracking number' length is needs to be 5!");
                            logger.log(Level.WARNING, "Number is not 5 bad");
                        }
                        else if (db.packageExists(cTrackINPUT.getText())) {
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
                        }
                        else if (cWeightINPUT.getText().length() < 1) {
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
                            db.addCrate(tracking,spec,mailing,weight,content);
                            if (db.findPackage(tracking).ptn.matches(tracking)) {
                                Container frame = cratePanel.getParent();
                                do {
                                    frame = frame.getParent();
                                } while (!(frame instanceof JFrame));
                                JOptionPane.showMessageDialog(frame, "Crate has been successfully added!\n",
                                        "Success!", JOptionPane.INFORMATION_MESSAGE);
                                logger.log(Level.INFO, "User added a crate object successfully");
                            }
                            else {
                                Container frame = cratePanel.getParent();
                                do {
                                    frame = frame.getParent();
                                } while (!(frame instanceof JFrame));
                                JOptionPane.showMessageDialog(frame, "addCrate method failed, " +
                                                "despite criteria being met. An unknown error has occurred!", "Failure!",
                                        JOptionPane.ERROR_MESSAGE);
                                logger.log(Level.SEVERE, "crate failed");
                            }
                        }
                        else {
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

        cCLEAR.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cTrackINPUT.setText("");
                cSpecINPUT.setText("");
                cMailingINPUT.setText("");
                cWeightINPUT.setText("");
                cContentINPUT.setText("");
            }
        });

        JPanel drumPanel = new JPanel(new GridLayout(7,1,1,1)) {
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

        JButton drumSubmit = new JButton ("Submit");
        drumPanel.add(drumSubmit);

        JButton drumClear = new JButton("Clear");
        drumPanel.add(drumClear);

        exitFromPackage = new JButton("Exit");
        drumPanel.add(exitFromPackage);



        exitFromPackage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Container frame = exitFrom.getParent();
                do {
                    frame = frame.getParent();
                } while (!(frame instanceof JFrame));
                ((JFrame) frame).dispose();
            }
        });

        drumSubmit.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                Thread qThread = new Thread() {
                    public void run() {

                        String tracking, spec, mailing, material;
                        float diameter;

                        ArrayList<String> err = new ArrayList<>();

                        if (dTrackingINPUT.getText().length() == 0) {
                            err.add("Field 'Tracking number' is empty");
                            logger.log(Level.WARNING, "Submission of empty field attempted");
                        }
                        else if (dTrackingINPUT.getText().length() != 5) {
                            err.add("'Tracking number' length is needs to be 5!");
                            logger.log(Level.WARNING, "Number is not 5 bad!!!!!!!!!!!");
                        }
                        else if (db.packageExists(dTrackingINPUT.getText())) {
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


                        if(drumMaterialINPUT.getText().length() == 0) {
                            err.add("Field is empty");
                            logger.log(Level.WARNING, "Empty field detected");
                        }

                        material = drumMaterialINPUT.getText();

                        if(drumDiameterINPUT.getText().length() == 0) {
                            err.add("Field is empty");
                            logger.log(Level.WARNING, "Empty field detected");
                        } else if (Float.parseFloat(drumDiameterINPUT.getText()) <= 0) {
                            err.add("Number for drum diameter is less than 0");
                            logger.log(Level.WARNING, "Number is lower than 0");
                        }

                        diameter = Float.parseFloat(drumDiameterINPUT.getText());

                        if (err.isEmpty()) {
                            db.addDrum(tracking,spec,mailing,material,diameter);
                            if (db.findPackage(tracking).ptn.matches(tracking)) {
                                Container frame = drumPanel.getParent();
                                do {
                                    frame = frame.getParent();
                                } while (!(frame instanceof JFrame));
                                JOptionPane.showMessageDialog(frame, "Drum has been successfully added!\n",
                                        "Success!", JOptionPane.INFORMATION_MESSAGE);
                                logger.log(Level.INFO, "User added a drum object successfully");
                            }
                            else {
                                Container frame = drumPanel.getParent();
                                do {
                                    frame = frame.getParent();
                                } while (!(frame instanceof JFrame));
                                JOptionPane.showMessageDialog(frame, "addDrum method failed, " +
                                                "despite criteria being met. An unknown error has occurred!", "Failure!",
                                        JOptionPane.ERROR_MESSAGE);
                                logger.log(Level.SEVERE, "drum critically failed");
                            }
                        }
                        else {
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

        tabbedPane.addTab(ENVELOPEPANEL, envelopeTab);
        tabbedPane.addTab(BOXPANEL, boxTab);
        tabbedPane.addTab(CRATEPANEL, cratePanel);
        tabbedPane.addTab(DRUMPANEL, drumPanel);

        pane.add(tabbedPane, BorderLayout.WEST);
    }
}

