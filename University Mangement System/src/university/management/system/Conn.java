package university.management.system;
import java.sql.*;

class Conn {

        Connection c;
        Statement s;
        
        Conn(){
            
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                c = DriverManager.getConnection("jdbc:mysql:///universitymanagementsystem","root","password");
                s = c.createStatement();
                
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
