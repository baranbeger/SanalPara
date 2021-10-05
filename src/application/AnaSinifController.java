package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AnaSinifController implements Initializable{
	Connection connection;
	Statement std;
	ResultSet rs;
	
	public static int idler;
	
	
	
	
	  @FXML
	private JFXButton girisBtnTxt;

	    @FXML
	private JFXButton kayitBtnTxt;
	    
	    @FXML
	    private JFXButton btnGirisTxt;

	    @FXML
	    private JFXButton btnKayitTxt;

    @FXML
    private TextField girisAd;

    @FXML
    private TextField girisSifre;

   

    @FXML
    private TextField kayitAd;

    @FXML
    private TextField kayitSifre;

    @FXML
    private TextField kayitSifreTekrar;

    @FXML
    private TextField kayitMail;

    @FXML
    void girisBtn(ActionEvent event) {
    	girisAd.setVisible(true);
		girisSifre.setVisible(true);
		
		
		kayitAd.setVisible(false);
		kayitMail.setVisible(false);
		kayitSifre.setVisible(false);
		kayitSifreTekrar.setVisible(false);
		
		girisBtnTxt.setVisible(true);
		kayitBtnTxt.setVisible(false);
		btnGirisTxt.setVisible(true);
		btnKayitTxt.setVisible(false);	
		
		

    }

    @FXML
    void kayitButton(ActionEvent event) {
    	girisAd.setVisible(false);
		girisSifre.setVisible(false);
		
		
		kayitAd.setVisible(true);
		kayitMail.setVisible(true);
		kayitSifre.setVisible(true);
		kayitSifreTekrar.setVisible(true);
		
		girisBtnTxt.setVisible(false);
		kayitBtnTxt.setVisible(true);
		btnGirisTxt.setVisible(false);
		btnKayitTxt.setVisible(true);	
		
		
    }
    

    @FXML
    void btnGirisBtn(ActionEvent event) {
    	try {
			Class.forName("com.mysql.jdbc.Driver");
			
			try {
				connection=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","123456789.");
				std=(Statement) connection.createStatement();
				String ad=girisAd.getText(),sifre=girisSifre.getText();
				
				
				
				String sorgu="select count(idKullanici) as giris from kullanici where kullaniciadi= '"+
				ad+"' and sifre='"+sifre+"';";
				String sorgu2="select idKullanici as idsi from kullanici where kullaniciadi= '"+
						ad+"' and sifre='"+sifre+"';";
				
				rs=std.executeQuery(sorgu2);
				while(rs.next()) {
					idler=rs.getInt("idsi");
					
				}
				rs=null;
				System.out.println("id si:"+idler);
				rs=std.executeQuery(sorgu);
				while(rs.next()){
					if(rs.getInt("giris")==1) {
						
						try {
							URL url = new File("src/application/SanalParaListeleme.fxml").toURI().toURL();
							Parent fxml = FXMLLoader.load(url);
							Stage stage = new Stage();
							Scene scene = new Scene(fxml);
						
							
							stage.setScene(scene);
							stage.show();
						} catch (IOException e) {

							System.out.println( "Hata :" + e.getMessage());
						}
						
						
						
					}
				}
				
				
				
			
				
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    
    	
    	

    }

    @FXML
    void btnKayitBtn(ActionEvent event) {
    	try {
			Class.forName("com.mysql.jdbc.Driver");
			
			try {
				connection=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","123456789.");
				std=(Statement) connection.createStatement();
				String add=kayitAd.getText();
				String sifre=kayitSifre.getText();
				String sifreTekrar=kayitSifreTekrar.getText();
				String mail=kayitMail.getText();
				
				String sorgu="insert into kullanici (mail,kullaniciadi,sifre) values ( '"+mail+
						"','"+add+"','"+sifre+"')";
				
				
				if(sifre.equals(sifreTekrar)) {
					std.execute(sorgu);
					
					
					
				}
				String sorgu3="select idKullanici as idsi from kullanici where kullaniciadi= '"+
						add+"' and sifre='"+sifre+"';";
				
				rs=std.executeQuery(sorgu3);
				int id = 0;
				while(rs.next()) {
					 id=rs.getInt("idsi");
					
					
				}
				String sorgu4="insert into kayitlar (toplamsanalpara,toplamtlpara,kullaniciid) values"+
						"(0,10000,"+id+");";
				
				std.execute(sorgu4);
				
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    	
    	girisAd.setVisible(true);
		girisSifre.setVisible(true);
		
		
		kayitAd.setVisible(false);
		kayitMail.setVisible(false);
		kayitSifre.setVisible(false);
		kayitSifreTekrar.setVisible(false);
		
		girisBtnTxt.setVisible(true);
		kayitBtnTxt.setVisible(false);
		btnGirisTxt.setVisible(true);
		btnKayitTxt.setVisible(false);	

    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		girisAd.setVisible(false);
		girisSifre.setVisible(false);
		
		
		kayitAd.setVisible(false);
		kayitMail.setVisible(false);
		kayitSifre.setVisible(false);
		kayitSifreTekrar.setVisible(false);
		
		girisBtnTxt.setVisible(true);
		kayitBtnTxt.setVisible(true);
		btnGirisTxt.setVisible(false);
		btnKayitTxt.setVisible(false);
		
		
		
		
		
		
	}
	
	

}
