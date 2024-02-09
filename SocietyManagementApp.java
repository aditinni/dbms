import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SocietyManagementApp extends JFrame {
    private Connection connection;

    public SocietyManagementApp() {
        super("GrandLand Society App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);

        // Connect to the database
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finalsocietypro", "root", "Aditya@1105");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create welcome message panel
        JPanel welcomePanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome to GrandLand Society App", SwingConstants.CENTER);
        JButton continueButton = new JButton("Continue");
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
        welcomePanel.add(continueButton, BorderLayout.SOUTH);
        add(welcomePanel);

        // Action listener for continue button
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open new window with options
                openOptionsWindow();
            }
        });
    }

    private void openOptionsWindow() {
        JFrame optionsFrame = new JFrame("Select From Menu");
        optionsFrame.setSize(400, 200);

        // Create options panel
        JPanel optionsPanel = new JPanel(new GridLayout(6, 2));
        JButton viewResidentsButton = new JButton("View Residents");
        JButton addResidentsButton = new JButton("Add Residents");
        JButton viewClubsButton = new JButton("View Clubs");
        JButton viewVisitorButton = new JButton("View Visitors");
        JButton addVisitorButton = new JButton("Add Visitors");
        JButton viewHouseHelpButton = new JButton("View House Help");

        // Add buttons to panel
        optionsPanel.add(viewResidentsButton);
        optionsPanel.add(addResidentsButton);
        optionsPanel.add(viewClubsButton);
        optionsPanel.add(viewVisitorButton);
        optionsPanel.add(addVisitorButton);
        optionsPanel.add(viewHouseHelpButton);

        // Add panel to frame
        optionsFrame.add(optionsPanel);

        // Action listeners for buttons (implement functionality)
        viewHouseHelpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Create a statement
                    Statement statement = connection.createStatement();

                    // Execute a query to get house help information
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM Househelp_s");

                    // Create a StringBuilder to hold the formatted information
                    StringBuilder houseHelpInfo = new StringBuilder();

                    // Append the table header
                    houseHelpInfo.append(String.format("%-15s %-25s %-20s %-15s %-5s\n", "House Help ID", "Name", "Phone Number", "Availability", "Block"));

                    // Append the retrieved information to the StringBuilder
                    while (resultSet.next()) {
                        houseHelpInfo.append(String.format("%-15d %-25s %-20s %-15s %-5s\n",
                                resultSet.getInt("househelp_id"),
                                resultSet.getString("househelp_name"),
                                resultSet.getString("househelp_number"),
                                resultSet.getBoolean("househelp_availability"),
                                resultSet.getString("block")));
                    }

                    // Close the statement and result set
                    statement.close();
                    resultSet.close();

                    // Create a JTextArea with the formatted information
                    JTextArea textArea = new JTextArea(houseHelpInfo.toString());
                    textArea.setEditable(false);
                    textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Set monospaced font

                    // Create a border for the JTextArea
                    textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    // Show the JTextArea in a scroll pane
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(null, scrollPane, "House Help Information", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred while retrieving house help information.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        viewResidentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Create a statement
                    Statement statement = connection.createStatement();

                    // Execute a query to get resident information
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM Residents_s");

                    // Create a StringBuilder to hold the formatted information
                    StringBuilder residentsInfo = new StringBuilder();

                    // Append the table header
                    residentsInfo.append(String.format("%-15s %-25s %-20s %-5s\n", "Resident ID", "Name", "Flat Number", "Block"));

                    // Append the retrieved information to the StringBuilder
                    while (resultSet.next()) {
                        residentsInfo.append(String.format("%-15d %-25s %-20s %-5s\n",
                                resultSet.getInt("resident_id"),
                                resultSet.getString("resident_name"),
                                resultSet.getString("flat_number"),
                                resultSet.getString("block")));
                    }

                    // Close the statement and result set
                    statement.close();
                    resultSet.close();

                    // Create a JTextArea with the formatted information
                    JTextArea textArea = new JTextArea(residentsInfo.toString());
                    textArea.setEditable(false);
                    textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Set monospaced font

                    // Create a border for the JTextArea
                    textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    // Show the JTextArea in a scroll pane
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(null, scrollPane, "Resident Information", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred while retrieving resident information.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        addResidentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create input fields for the new resident information
                JTextField residentIdField = new JTextField(5); // New field for resident ID
                JTextField nameField = new JTextField(20);
                JTextField flatNumberField = new JTextField(10);
                JTextField blockField = new JTextField(1);

                JPanel inputPanel = new JPanel(new GridLayout(4, 2)); // Updated to accommodate the new field
                inputPanel.add(new JLabel("Resident ID:")); // New label for resident ID
                inputPanel.add(residentIdField);
                inputPanel.add(new JLabel("Name:"));
                inputPanel.add(nameField);
                inputPanel.add(new JLabel("Flat Number:"));
                inputPanel.add(flatNumberField);
                inputPanel.add(new JLabel("Block:"));
                inputPanel.add(blockField);

                // Show input dialog for adding resident
                int result = JOptionPane.showConfirmDialog(null, inputPanel, "Add Resident", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        // Retrieve input values
                        int residentId = Integer.parseInt(residentIdField.getText().trim()); // Parse resident ID as an integer
                        String name = nameField.getText().trim();
                        String flatNumber = flatNumberField.getText().trim();
                        String block = blockField.getText().trim();

                        // Validate input
                        if (name.isEmpty() || flatNumber.isEmpty() || block.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Create a prepared statement to insert the new resident
                        String query = "INSERT INTO Residents_s (resident_id, resident_name, flat_number, block) VALUES (?, ?, ?, ?)";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setInt(1, residentId);
                        preparedStatement.setString(2, name);
                        preparedStatement.setString(3, flatNumber);
                        preparedStatement.setString(4, block);

                        // Execute the insert query
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Resident added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to add resident.", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        // Close the prepared statement
                        preparedStatement.close();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid resident ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error occurred while adding resident.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        viewClubsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Create a statement
                    Statement statement = connection.createStatement();

                    // Execute a query to get club information
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM ClubStable");

                    // Create a StringBuilder to hold the formatted information
                    StringBuilder clubInfo = new StringBuilder();

                    // Append the table header
                    clubInfo.append(String.format("%-25s %-5s\n", "Club Name", "Block"));

                    // Append the retrieved information to the StringBuilder
                    while (resultSet.next()) {
                        clubInfo.append(String.format("%-25s %-5s\n",
                                resultSet.getString("club_name"),
                                resultSet.getString("block")));
                    }

                    // Close the statement and result set
                    statement.close();
                    resultSet.close();

                    // Create a JTextArea with the formatted information
                    JTextArea textArea = new JTextArea(clubInfo.toString());
                    textArea.setEditable(false);
                    textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Set monospaced font

                    // Create a border for the JTextArea
                    textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    // Show the JTextArea in a scroll pane
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(null, scrollPane, "Club Information", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred while retrieving club information.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        viewVisitorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Create a statement
                    Statement statement = connection.createStatement();

                    // Execute a query to get visitor information
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM Visitors_s");

                    // Create a StringBuilder to hold the formatted information
                    StringBuilder visitorInfo = new StringBuilder();

                    // Append the table header
                    visitorInfo.append(String.format("%-15s %-25s %-20s %-10s\n", "Visitor ID", "Visitor Name", "Phone Number", "Resident ID"));

                    // Append the retrieved information to the StringBuilder
                    while (resultSet.next()) {
                        visitorInfo.append(String.format("%-15d %-25s %-20s %-10d\n",
                                resultSet.getInt("visitor_id"),
                                resultSet.getString("visitor_name"),
                                resultSet.getString("phone_number"),
                                resultSet.getInt("resident_id")));
                    }

                    // Close the statement and result set
                    statement.close();
                    resultSet.close();

                    // Create a JTextArea with the formatted information
                    JTextArea textArea = new JTextArea(visitorInfo.toString());
                    textArea.setEditable(false);
                    textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Set monospaced font

                    // Create a border for the JTextArea
                    textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    // Show the JTextArea in a scroll pane
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(null, scrollPane, "Visitor Information", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred while retrieving visitor information.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        addVisitorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create input fields for the new visitor information
                JTextField nameField = new JTextField(20);
                JTextField phoneNumberField = new JTextField(15);
                JTextField residentIdField = new JTextField(5);

                JPanel inputPanel = new JPanel(new GridLayout(3, 2));
                inputPanel.add(new JLabel("Name:"));
                inputPanel.add(nameField);
                inputPanel.add(new JLabel("Phone Number:"));
                inputPanel.add(phoneNumberField);
                inputPanel.add(new JLabel("Resident ID:"));
                inputPanel.add(residentIdField);

                // Show input dialog for adding visitor
                int result = JOptionPane.showConfirmDialog(null, inputPanel, "Add Visitor", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        // Retrieve input values
                        String name = nameField.getText().trim();
                        String phoneNumber = phoneNumberField.getText().trim();
                        int residentId = Integer.parseInt(residentIdField.getText().trim());

                        // Validate input
                        if (name.isEmpty() || phoneNumber.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Create a prepared statement to insert the new visitor
                        String query = "INSERT INTO Visitors_s (visitor_name, phone_number, resident_id) VALUES (?, ?, ?)";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, name);
                        preparedStatement.setString(2, phoneNumber);
                        preparedStatement.setInt(3, residentId);

                        // Execute the insert query
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Visitor added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to add visitor.", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        // Close the prepared statement
                        preparedStatement.close();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid resident ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error occurred while adding visitor.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });







        // Display the frame
        optionsFrame.setVisible(true);
    }

    public static void main(String[] args) {
        // Create and display the main frame
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SocietyManagementApp().setVisible(true);
            }
        });
    }
}
