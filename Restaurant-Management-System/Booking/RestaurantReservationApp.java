import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RestaurantReservationApp extends JFrame {
    private ReservationSystem reservationSystem;
    private JLabel lblTableNumber;
    private JTextField txtTableNumber;
    private JLabel lblCustomerName;
    private JTextField txtCustomerName;
    private JButton btnMakeReservation;
    private JButton btnCancelReservation;
    private JButton btnViewReservation;
    private JButton btnMenuList;
    private JTextArea txtAreaTableStatus;

    public RestaurantReservationApp(int numTables) {
        // Initialize reservation system
        reservationSystem = new ReservationSystem(numTables);

        // Set up frame
        setTitle("Restaurant Reservation App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize components
        lblTableNumber = new JLabel("Table Number:");
        txtTableNumber = new JTextField(10);
        lblCustomerName = new JLabel("Customer Name:");
        txtCustomerName = new JTextField(10);
        btnMakeReservation = new JButton("Make Reservation");
        btnCancelReservation = new JButton("Cancel Reservation");
        btnViewReservation = new JButton("View Reservation");
        btnMenuList = new JButton("Menu List");
        txtAreaTableStatus = new JTextArea(10, 30);
        txtAreaTableStatus.setEditable(false);

        // Set layout manager
        setLayout(new FlowLayout());

        // Add components to the frame
        add(lblTableNumber);
        add(txtTableNumber);
        add(lblCustomerName);
        add(txtCustomerName);
        add(btnMakeReservation);
        add(btnCancelReservation);
        add(btnViewReservation);
        add(btnMenuList);
        add(txtAreaTableStatus);

        // Add action listeners for buttons
        btnMakeReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tableNumber = Integer.parseInt(txtTableNumber.getText());
                String customerName = txtCustomerName.getText();
                boolean success = reservationSystem.makeReservation(tableNumber, customerName);
                if (success) {
                    txtAreaTableStatus.append("Table " + tableNumber + " reserved for " + customerName + " at " + reservationSystem.getReservationTime(tableNumber).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
                }
            }
        });

        btnCancelReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tableNumber = Integer.parseInt(txtTableNumber.getText());
                boolean success = reservationSystem.cancelReservation(tableNumber);
                if (success) {
                    txtAreaTableStatus.append("Reservation for table " + tableNumber + " cancelled.\n");
                }
            }
        });

        btnViewReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tableNumber = Integer.parseInt(txtTableNumber.getText());
                String customerName = reservationSystem.getCustomerName(tableNumber);
                LocalDateTime reservationTime = reservationSystem.getReservationTime(tableNumber);
                if (customerName == null || reservationTime == null) {
                    txtAreaTableStatus.append("Table " + tableNumber + " is not reserved.\n");
                } else {
                    txtAreaTableStatus.append("Table " + tableNumber + " is reserved for " + customerName + " at " + reservationTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
                }
            }
        });

        btnMenuList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                                // Display menu list
                                String menuList = reservationSystem.getMenuList();
                                txtAreaTableStatus.append(menuList);
                            }
                        });
                
                        // Set frame visible
                        setVisible(true);
                    }
                
                    public static void main(String[] args) {
                        int numTables = 10; // Number of tables in the restaurant
                        RestaurantReservationApp app = new RestaurantReservationApp(numTables);
                    }
                
                    // ReservationSystem class
                    private class ReservationSystem {
                        private int numTables;
                        private String[] customerNames;
                        private LocalDateTime[] reservationTimes;
                        private String[] menuItems;
                
                        public ReservationSystem(int numTables) {
                            this.numTables = numTables;
                            customerNames = new String[numTables];
                            reservationTimes = new LocalDateTime[numTables];
                            menuItems = new String[] { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
                        }
                
                        public boolean makeReservation(int tableNumber, String customerName) {
                            if (tableNumber < 1 || tableNumber > numTables) {
                                return false; // Invalid table number
                            }
                            if (customerNames[tableNumber - 1] != null) {
                                return false; // Table already reserved
                            }
                            customerNames[tableNumber - 1] = customerName;
                            reservationTimes[tableNumber - 1] = LocalDateTime.now();
                            return true;
                        }
                
                        public boolean cancelReservation(int tableNumber) {
                            if (tableNumber < 1 || tableNumber > numTables) {
                                return false; // Invalid table number
                            }
                            if (customerNames[tableNumber - 1] == null) {
                                return false; // Table not reserved
                            }
                            customerNames[tableNumber - 1] = null;
                            reservationTimes[tableNumber - 1] = null;
                            return true;
                        }
                
                        public String getCustomerName(int tableNumber) {
                            if (tableNumber < 1 || tableNumber > numTables) {
                                return null; // Invalid table number
                            }
                            return customerNames[tableNumber - 1];
                        }
                
                        public LocalDateTime getReservationTime(int tableNumber) {
                            if (tableNumber < 1 || tableNumber > numTables) {
                                return null; // Invalid table number
                            }
                            return reservationTimes[tableNumber - 1];
                        }
                
                        public String getMenuList() {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Menu List:\n");
                            for (int i = 0; i < menuItems.length; i++) {
                                sb.append((i + 1) + ". " + menuItems[i] + "\n");
                            }
                            return sb.toString();
                        }
                    }
                }
                
