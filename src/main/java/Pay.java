import cn.juntai.wxpaydemo.pay.WXPay;

import java.awt.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Tue May 03 19:54:05 CST 2022
 */



/**
 * @author 1
 */
public class Pay extends JFrame {
    Item item;
    public Pay(Item item) {
        this.item=item;
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        textField1 = new JTextField();
        textField2 = new JTextField();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        textField3 = new JTextField();
        textField4 = new JTextField();
        button1 = new JButton();
        button2 = new JButton();
        label5 = new JLabel();
        textField5 = new JTextField();

        JLabel label =new JLabel();//二维码标签
        JTextField textField = new JTextField();//条形码标签

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("\u5546\u54c1ID");//商品ID
        contentPane.add(label1);
        label1.setBounds(25, 115, 50, 25);
        contentPane.add(textField1);
        textField1.setBounds(75, 115, 200,25);
        textField1.setText(String.valueOf(item.getId()));//获取商品ID
        contentPane.add(textField2);
        textField2.setBounds(75, 165, 200, 25);
        textField2.setText(item.getTitle());//获取商品名字

        //---- label2 ----
        label2.setText("\u5546\u54c1");//商品
        contentPane.add(label2);
        label2.setBounds(35, 165, 55, 25);

        //---- label3 ----
        label3.setText("\u5355\u4ef7");//单价
        contentPane.add(label3);
        label3.setBounds(35, 215, 45, 25);

        //---- label4 ----
        label4.setText("\u6570\u91cf");//数量
        contentPane.add(label4);
        label4.setBounds(35, 260, 55, 25);
        contentPane.add(textField3);
        textField3.setBounds(75, 215, 200, 25);
        textField3.setText(String.valueOf(item.getPrice()));//获取商品单价
        contentPane.add(textField4);
        textField4.setBounds(75, 265, 200, 25);
        //商品数量自填

        //---- button1 ----
        button1.setText("\u4e8c\u7ef4\u7801\u652f\u4ed8");//二维码支付
        contentPane.add(button1);
        button1.setBounds(new Rectangle(new Point(290, 50), button1.getPreferredSize()));
        button1.addActionListener(//监听器，输出二维码
                (e)->{
                    String id=textField1.getText();
                    String name=textField2.getText();
                    String price=textField3.getText();
                    String amount=textField4.getText();

                    WXPay wxPay=new WXPay();
                    wxPay.unifiedOrder(id,name,price,amount);

                    Graphics g=getGraphics();
                    ImageIcon icon=new ImageIcon("C:\\Users\\123\\new.jpg");
                    Image img=icon.getImage();
                    g.drawImage(img,300,115,200,200,null);
                    g.dispose();

                }
        );

        //---- button2 ----
        button2.setText("\u4ed8\u6b3e\u7801\u652f\u4ed8");//付款码支付
        contentPane.add(button2);
        button2.setBounds(new Rectangle(new Point(400, 50), button2.getPreferredSize()));
        button2.addActionListener((e)->{
            String id=textField1.getText();
            String name=textField2.getText();
            String price=textField3.getText();
            String amount=textField4.getText();
            String auth_code=textField5.getText();
            WXPay wxPay=new WXPay();
            try {
                wxPay.scanCodeToPay(auth_code,id,name,price,amount);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //---- label5 ----
        label5.setText("\u652f\u4ed8\u65b9\u5f0f");//支付方式
        contentPane.add(label5);
        label5.setBounds(368, 5, 85, 25);
        contentPane.add(textField5);
        textField5.setBounds(500, 50, 175, 25);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
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
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JTextField textField3;
    private JTextField textField4;
    private JButton button1;
    private JButton button2;
    private JLabel label5;
    private JTextField textField5;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
