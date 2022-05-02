import cn.juntai.wxpaydemo.pay.WXPay;

import java.awt.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Mon May 02 18:13:07 CST 2022
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

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- button1 ----
        button1.setText("\u4e8c\u7ef4\u7801\u4ed8\u6b3e");
        contentPane.add(button1);
        button1.setBounds(85, 290, 140, 55);
        button1.addActionListener(
                (e)->{
                    WXPay wxPay=new WXPay();
                    wxPay.unifiedOrder();
                }
        );

        //---- button2 ----
        button2.setText("\u626b\u7801\u4ed8\u6b3e");
        contentPane.add(button2);
        button2.setBounds(330, 290, 130, 50);

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
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
