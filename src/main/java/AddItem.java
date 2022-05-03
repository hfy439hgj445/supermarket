import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

/**
 * @liwei
 */
public class AddItem extends JFrame {
    Item item;

    public AddItem(Item item) {
        this.item = item;
        initComponents();
    }

    private void initComponents() {
        label1 = new JLabel();
        textField1 = new JTextField();
        label2 = new JLabel();
        textField2 = new JTextField();
        label3 = new JLabel();
        textField3 = new JTextField();
        label4 = new JLabel();
        textField4 = new JTextField();
        label5 = new JLabel();
        textField5 = new JTextField();
        label6 = new JLabel();
        textField6 = new JTextField();
        button1 = new JButton();

        //======== this ========
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("\u5546\u54c1ID\uff1a");
        contentPane.add(label1);
        label1.setBounds(20, 20, 55, 20);
        contentPane.add(textField1);
        textField1.setBounds(70, 20, 130, 20);


        //---- label2 ----
        label2.setText("\u5546\u54c1\u540d\u79f0\uff1a");
        contentPane.add(label2);
        label2.setBounds(240, 20, 90, 20);
        contentPane.add(textField2);
        textField2.setBounds(300, 20, 130, 20);


        //---- label3 ----
        label3.setText("\u5355\u4ef7\uff1a");
        contentPane.add(label3);
        label3.setBounds(20, 80, 55, 20);
        contentPane.add(textField3);
        textField3.setBounds(70, 80, 130, 20);


        //---- label4 ----
        label4.setText("\u63cf\u8ff0\uff1a");
        contentPane.add(label4);
        label4.setBounds(240, 80, 90, 20);
        contentPane.add(textField4);
        textField4.setBounds(300, 80, 130, 20);


        //---- label5 ----
        label5.setText("\u4fc3\u9500\u4ef7\uff1a");
        contentPane.add(label5);
        label5.setBounds(20, 140, 55, 20);
        contentPane.add(textField5);
        textField5.setBounds(70, 140, 130, 20);


        //---- label6 ----
        label6.setText("\u5546\u54c1\u56fe\u7247\uff1a");
        contentPane.add(label6);
        label6.setBounds(240, 140, 90, 20);
        contentPane.add(textField6);
        textField6.setBounds(300, 140, 130, 20);


        //---- button1 ----
        button1.setText("保存");
        contentPane.add(button1);
        button1.setBounds(200, 300, 100, 30);
        button1.addActionListener(
                (e)->{
                    String id=textField1.getText();
                    String title=textField2.getText();
                    String price=textField3.getText();
                    String description=textField4.getText();
                    String sales=textField5.getText();
                    String img_url=textField6.getText();

                    Connection conn = null;
                    String user = "root";
                    String dbPassword = "Mysql_123456";
                    String url = "jdbc:mysql://118.190.148.144:3306/supermarket?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
                    PreparedStatement pstmt = null;
                    String sql ="INSERT INTO item values('"+id+"','"+title+"','"+price+"','"+description+"',"+sales+",'"+img_url+"')";

                    try{
                        conn = DriverManager.getConnection(url, user, dbPassword);
                        pstmt = conn.prepareStatement(sql);

                        pstmt.executeUpdate();

                        if(sql!=null){
                            this.setVisible(false);
                            System.out.println("新增成功");

                            Main main=new Main();
                            main.setVisible(true);
                        }


                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
        );

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for (int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        this.setBounds(600, 300, 480, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JLabel label1;
    private JTextField textField1;
    private JLabel label2;
    private JTextField textField2;
    private JLabel label3;
    private JTextField textField3;
    private JLabel label4;
    private JTextField textField4;
    private JLabel label5;
    private JTextField textField5;
    private JLabel label6;
    private JTextField textField6;
    private JButton button1;
}
