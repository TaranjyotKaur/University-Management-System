package university.management.system;
import java.awt.Font;
import javax.swing.*;
import java.util.*;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.event.*;
//import java.sql.ResultSet;

public class AddTeacher extends JFrame implements ActionListener {
    
    JTextField tfname,tffname,tfaddress,tfphone,tfemail,tfx,tfxii,tfaadhar;
    JLabel labelempid;
    JDateChooser dcdob;
    JComboBox cbqual,cbdpt;
    JButton Submit,Cancel;
    
    Random ran = new Random();
    long first2 = Math.abs((ran.nextLong() %90L) + 10L);
    
    AddTeacher(){
        setSize(900,700);
        setLocation(350,50);
        setLayout(null);
        
        JLabel heading = new JLabel ("New Teacher Details");
        heading.setBounds(310,30,500,50);
        heading.setFont(new Font("serif",Font.BOLD,30));
        add(heading);
        
        //name
        JLabel lblname = new JLabel ("Name");
        lblname.setBounds(50,150,100,30);
        lblname.setFont(new Font("serif",Font.BOLD,20));
        add(lblname);
        
        tfname = new JTextField();
        tfname.setBounds(200,150,150,30);
        add(tfname);
        
        //father's name
        JLabel lblfname = new JLabel ("Father's Name");
        lblfname.setBounds(400,150,200,30);
        lblfname.setFont(new Font("serif",Font.BOLD,20));
        add(lblfname);
        
        tffname = new JTextField();
        tffname.setBounds(600,150,150,30);
        add(tffname);
        
        //roll no.
        JLabel lblempid = new JLabel ("Employee ID");
        lblempid.setBounds(50,200,200,30);
        lblempid.setFont(new Font("serif",Font.BOLD,20));
        add(lblempid);
        
        labelempid = new JLabel ("101"+first2);
        labelempid.setBounds(200,200,200,30);
        labelempid.setFont(new Font("serif",Font.BOLD,20));
        add(labelempid);
         
        //dob
        JLabel lbldob = new JLabel ("Date of Birth");
        lbldob.setBounds(400,200,200,30);
        lbldob.setFont(new Font("serif",Font.BOLD,20));
        add(lbldob);
        
        dcdob = new JDateChooser();
        dcdob.setBounds(600,200,150,30);
        add(dcdob);
       
        //address
        JLabel lbladdress = new JLabel ("Address");
        lbladdress.setBounds(50,250,200,30);
        lbladdress.setFont(new Font("serif",Font.BOLD,20));
        add(lbladdress);
        
        tfaddress = new JTextField();
        tfaddress.setBounds(200,250,150,30);
        add(tfaddress);
        
        
        //phone no.
        JLabel lblphone = new JLabel ("Phone No.");
        lblphone.setBounds(400,250,200,30);
        lblphone.setFont(new Font("serif",Font.BOLD,20));
        add(lblphone);
        
        tfphone = new JTextField();
        tfphone.setBounds(600,250,150,30);
        add(tfphone);
        
        //email
        JLabel lblemail = new JLabel ("Email ID");
        lblemail.setBounds(50,300,200,30);
        lblemail.setFont(new Font("serif",Font.BOLD,20));
        add(lblemail);
        
        tfemail = new JTextField();
        tfemail.setBounds(200,300,150,30);
        add(tfemail);
        
        
        //class X marks 
        JLabel lblx = new JLabel ("Class X (%)");
        lblx.setBounds(400,300,200,30);
        lblx.setFont(new Font("serif",Font.BOLD,20));
        add(lblx);
        
        tfx = new JTextField();
        tfx.setBounds(600,300,150,30);
        add(tfx);
        
        //class XII marks
        JLabel lblxii = new JLabel ("Class XII(%)");
        lblxii.setBounds(50,350,200,30);
        lblxii.setFont(new Font("serif",Font.BOLD,20));
        add(lblxii);
        
        tfxii = new JTextField();
        tfxii.setBounds(200,350,150,30);
        add(tfxii);
        
        //class XII marks
        JLabel lblaadhar = new JLabel ("Aadhar No.");
        lblaadhar.setBounds(400,350,200,30);
        lblaadhar.setFont(new Font("serif",Font.BOLD,20));
        add(lblaadhar);
        
        tfaadhar = new JTextField();
        tfaadhar.setBounds(600,350,150,30);
        add(tfaadhar);
        
        //qualification dropdown
        JLabel lblqual = new JLabel ("Qualification");
        lblqual.setBounds(50,400,200,30);
        lblqual.setFont(new Font("serif",Font.BOLD,20));
        add(lblqual);
        
        String qual[]= {"B Tech","BBA","BCA","MSc","BSc","Bcom","Mcom","MBA","MA","BA","HSC","SSC"};
        cbqual = new JComboBox(qual);
        cbqual.setBounds(200,400,150,30);
        cbqual.setBackground(Color.WHITE);
        add(cbqual);
        
        
        //deaprtment dropdown
        JLabel lbldpt = new JLabel ("Department");
        lbldpt.setBounds(400,400,200,30);
        lbldpt.setFont(new Font("serif",Font.BOLD,20));
        add(lbldpt);
        
        String branch[]= {"Computer Science","IT","General Science","Commerce","Arts"};
        cbdpt = new JComboBox(branch);
        cbdpt.setBounds(600,400,150,30);
        cbdpt.setBackground(Color.WHITE);
        add(cbdpt);
        
        //submit button
        Submit = new JButton("Submit");
        Submit.setBounds(250, 550, 120, 30);
        Submit.setBackground(Color.BLACK);
        Submit.setForeground(Color.WHITE);
        Submit.addActionListener(this);
        Submit.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(Submit);
        
        //cancel button
        Cancel = new JButton("Cancel");
        Cancel.setBounds(450, 550, 120, 30);
        Cancel.setBackground(Color.BLACK);
        Cancel.setForeground(Color.WHITE);
        Cancel.addActionListener(this);
        Cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(Cancel);
        
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()== Submit){
          String name = tfname.getText();  
          String fname = tffname.getText();
          String rollno = labelempid.getText();
          String dob = ((JTextField) dcdob.getDateEditor().getUiComponent()).getText();
          String address = tfaddress.getText();
          String phone = tfphone.getText();
          String email = tfemail.getText();
          String x = tfx.getText();
          String xii = tfxii.getText();
          String aadhar = tfaadhar.getText();
          String course = (String)cbqual.getSelectedItem();
          String branch = (String)cbdpt.getSelectedItem();
          
          try{
              String query = "insert into teacher values('"+name+"','"+fname+"','"+rollno+"','"+dob+"','"+address+"','"+phone+"','"+email+"','"+x+"','"+xii+"','"+aadhar+"','"+course+"','"+branch+"')";
              Conn con = new Conn();
              con.s.executeUpdate(query);
              
              JOptionPane.showMessageDialog(null,"Teacher Details Inserted Successfully");
              setVisible(false);
              
          }catch (Exception e){
              e.printStackTrace();
          }
          
        } else { 
            setVisible(false);
        }
        
    }
           

    
    public static void main(String[]args){
        new AddTeacher();
   }
}