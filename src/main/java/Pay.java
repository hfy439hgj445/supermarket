import cn.juntai.wxpaydemo.pay.WXPay;

import java.awt.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Tue May 03 10:39:04 CST 2022
 */



/**
 * @author 1
 */
public class Pay extends JFrame {
    public Pay() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        button1 = new JButton();
        button2 = new JButton();
        textField1 = new JTextField();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- button1 ----
        button1.setText("\u4e8c\u7ef4\u7801\u652f\u4ed8");//二维码支付
        contentPane.add(button1);
        button1.setBounds(60, 405, 120, 50);
        button1.addActionListener(//监听器，输出二维码
                (e)->{
                    WXPay wxPay=new WXPay();
                    wxPay.unifiedOrder();

                    Graphics g=getGraphics();
                    ImageIcon icon=new ImageIcon("C:\\Users\\123\\new.jpg");
                    Image img=icon.getImage();
                    g.drawImage(img,150,100,250,250,null);
                    g.dispose();

                }
        );

        //---- button2 ----
        button2.setText("\u4ed8\u6b3e\u7801\u652f\u4ed8");//条形码支付
        contentPane.add(button2);
        button2.setBounds(240, 405, 120, 50);
        button2.addActionListener(//监听器，条形码支付
                (e)->{
                    WXPay wxPay=new WXPay();
                    try {
                        wxPay.scanCodeToPay("133923796890419810");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
        );
        contentPane.add(textField1);
        textField1.setBounds(360, 416, 225, 35);

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
    private JButton button1;
    private JButton button2;
    private JTextField textField1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}


