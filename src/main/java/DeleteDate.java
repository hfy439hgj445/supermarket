import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class DeleteDate extends JFrame {
    Item item;

    public DeleteDate(Item item) {
        this.item = item;
        delete();
    }
    private void delete(){
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setLayout(null);
        try{
            PreparedStatement pstmt = null;
            PreparedStatement pstmt2 = null;
            Connection conn = null;
            String user = "root";
            String dbPassword = "Mysql_123456";
            String url = "jdbc:mysql://118.190.148.144:3306/supermarket?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String sql="DELETE from item  where id=?";
            String sql2="DELETE from stock  where item_id=?";
            conn = DriverManager.getConnection(url, user, dbPassword);
            pstmt = conn.prepareStatement(sql);
            pstmt2 = conn.prepareStatement(sql2);

            pstmt.setInt(1,item.getId());
            pstmt2.setInt(1,item.getId());
            pstmt.executeUpdate();
            pstmt2.executeUpdate();



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    };
}