package AGOS.PROJECT;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;


public class loginPage{
    public static void main(String[] args) {
        new titleFrame();
    }
}

class titleFrame extends JFrame {
    
    private final Color COLOR_1 = new Color(78, 128, 193); //  #4e80c1
    private final Color COLOR_2 = new Color(0, 0, 102); // #000066
    private final Color COLOR_3 = new Color(137, 217, 250); // #89d9fa
    private final Color COLOR_4 = new Color(50, 79, 124);  // #324f7c
    private final Color COLOR_5 = new Color(99, 117, 156); // #63759c
    private final Color COLOR_6 = new Color(182, 190, 209); // #b6bed1
    private final Color COLOR_7 = new Color(151, 180, 220); // #97b4dc

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public titleFrame() {

        ImageIcon imgLOGO = new ImageIcon("agosLogo.png");
        setIconImage(imgLOGO.getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 911, 499);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setResizable(false);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Welcome to AGOS");
        lblTitle.setFont(new Font("Century Gothic", Font.BOLD, 24));
        lblTitle.setBounds(80, 49, 248, 95);
        lblTitle.setForeground(Color.BLACK);
        contentPane.add(lblTitle);

        JLabel lblSubtitle = new JLabel("Please click//tap the appropriate button");
        lblSubtitle.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        lblSubtitle.setBounds(60, 140, 400, 48);
        lblSubtitle.setForeground(Color.BLACK);
        contentPane.add(lblSubtitle);

        JLabel lblSubtitle1 = new JLabel("to help you in your navigation of our services");
        lblSubtitle1.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        lblSubtitle1.setForeground(Color.BLACK);
        lblSubtitle1.setBounds(45, 160, 400, 48);
        contentPane.add(lblSubtitle1);

        RoundedBorder btnAdmin = new RoundedBorder("ADMIN");
        btnAdmin.setFont(new Font("Century Gothic", Font.BOLD, 14));
        btnAdmin.setBounds(120, 215, 170, 38);
        btnAdmin.setFocusable(false);
        contentPane.add(btnAdmin);

        RoundedBorder btnPassenger = new RoundedBorder("PASSENGER");
        btnPassenger.setFont(new Font("Century Gothic", Font.BOLD, 14));
        btnPassenger.setBounds(120, 270, 170, 38);
        btnPassenger.setFocusable(false);
        contentPane.add(btnPassenger);

        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (event.getSource() == btnAdmin) {
                    new LogInFrame();
                    dispose();
                } else if (event.getSource() == btnPassenger) {
                    new passengerDashboard();
                    dispose();
                }
            }
        };

        btnAdmin.addActionListener(action);
        btnPassenger.addActionListener(action);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(135, 149, 176));
        panel.setBounds(406, 0, 508, 475);
        contentPane.add(panel);
        panel.setLayout(null);

        ImageIcon logo = new ImageIcon("Pasig-River-Ferry-interior.jpg");
        JLabel lblNewLabel = new JLabel();
        lblNewLabel.setIcon(logo);
        lblNewLabel.setBounds(-5, -20, 500, 500);
        Image imgLogo = logo.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        lblNewLabel.setIcon(new ImageIcon(imgLogo));
        panel.add(lblNewLabel);

        setVisible(true);
    }
}

class LogInFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private final String strCorrectUsername = "adminPUP";
    private final String strCorrectPassword = "passwordPUP";

    public LogInFrame() {

        ImageIcon imgLOGO = new ImageIcon("agosLogo.png");
        setIconImage(imgLOGO.getImage());
    

        setTitle("AGOS Admin Log-In");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 912, 499);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setResizable(false);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(135, 149, 176));
        panel.setBounds(-12, 0, 492, 475);
        contentPane.add(panel);
        panel.setLayout(null);

        ImageIcon pic2 = new ImageIcon("pasigpic.jpg");
        JLabel pasigL = new JLabel();
        pasigL.setIcon(pic2);
        pasigL.setBounds(-5, -20, 500, 500);
        Image imgPasig = pic2.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        pasigL.setIcon(new ImageIcon(imgPasig));
        panel.add(pasigL);

        JLabel lblTitleLabel = new JLabel("Admin Log-In");
        lblTitleLabel.setFont(new Font("Century Gothic", Font.BOLD, 24));
        lblTitleLabel.setBounds(628, 92, 163, 49);
        contentPane.add(lblTitleLabel);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Century Gothic", Font.BOLD, 14));
        lblUsername.setBounds(545, 188, 97, 19);
        contentPane.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(633, 185, 195, 29);
        contentPane.add(txtUsername);
        txtUsername.setColumns(10);

        JLabel lblNewLabel = new JLabel("Password");
        lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
        lblNewLabel.setBounds(545, 240, 78, 29);
        contentPane.add(lblNewLabel);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(633, 240, 195, 29);
        txtPassword.setEchoChar('*');
        contentPane.add(txtPassword);

        RoundedBorder btnNewButton = new RoundedBorder("Log-In");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String strUsername = txtUsername.getText();
                char[] charPassword = txtPassword.getPassword();
                String strPassword = new String(charPassword);

                if (strUsername.equals(strCorrectUsername) && strPassword.equals(strCorrectPassword)) {
                    JOptionPane.showMessageDialog(null, "You have successfully logged in!", "Log-In", JOptionPane.INFORMATION_MESSAGE);
                    new adminDB();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "You have entered an incorrect account!", "Log-in Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnNewButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        btnNewButton.setBounds(668, 301, 123, 29);
        btnNewButton.setFocusable(false);
        contentPane.add(btnNewButton);

        JLabel lblNote = new JLabel("Note: Please refer for the admin account in your designated Ferry Station.");
        lblNote.setFont(new Font("Century Gothic", Font.ITALIC, 10));
        lblNote.setBounds(510, 439, 1000, 13);
        contentPane.add(lblNote);

        JLabel lblMessage = new JLabel("");
        lblMessage.setBounds(654, 351, 45, 13);
        contentPane.add(lblMessage);

        JLabel lblNewLabel_3 = new JLabel("Not an Admin?");
        lblNewLabel_3.setFont(new Font("Century Gothic", Font.PLAIN, 10));
        lblNewLabel_3.setBounds(665, 335, 80, 13);
        contentPane.add(lblNewLabel_3);

        JLabel lblGoBack = new JLabel("Go back.");
        lblGoBack.setFont(new Font("Century Gothic", Font.BOLD, 10));
        lblGoBack.setBounds(745, 335, 50, 13);
        lblGoBack.setForeground(Color.RED);
        contentPane.add(lblGoBack);

        lblGoBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                new titleFrame();
                dispose();
            }
        });

        setVisible(true);
    }
}

class RoundedBorder extends JButton {

    private final Color COLOR_1 = new Color(78, 128, 193); //  #4e80c1
    private final Color COLOR_2 = new Color(0, 0, 102); // #000066
    private final Color COLOR_3 = new Color(137, 217, 250); // #89d9fa
    private final Color COLOR_4 = new Color(50, 79, 124);  // #324f7c
    private final Color COLOR_5 = new Color(99, 117, 156); // #63759c
    private final Color COLOR_6 = new Color(182, 190, 209); // #b6bed1
    private final Color COLOR_7 = new Color(151, 180, 220); // #97b4dc

    public RoundedBorder(String text) {
        super(text);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setBackground(COLOR_4);
        setForeground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isPressed()) {
            g2.setColor(getBackground().darker());
        } else if (getModel().isRollover()) {
            g2.setColor(getBackground().brighter());
        } else {
            g2.setColor(getBackground());
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
    }
}