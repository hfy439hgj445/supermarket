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
            Connection conn = null;
            String user = "root";
            String dbPassword = "Mysql_123456";
            String url = "jdbc:mysql://118.190.148.144:3306/supermarket?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String sql="DELETE from item  where id=?";
            conn = DriverManager.getConnection(url, user, dbPassword);
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,item.getId());
            pstmt.executeUpdate();



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    };
}