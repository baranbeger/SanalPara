package application;

import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GecmisTabloController implements Initializable{
	Connection con;
	Statement std;
	ResultSet rs;

    @FXML
    private TableView<Model> tablo;

    @FXML
    private TableColumn<Model, String> ad;

    @FXML
    private TableColumn<Model, String> islem;

    @FXML
    private TableColumn<Model, Integer> miktar;

    @FXML
    private TableColumn<Model, Double> fiyat;
    
    ObservableList<Model> list=FXCollections.observableArrayList();
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","123456789.");
				
				std=(Statement) con.createStatement();
				String sorgu="select *from gecmis";
				rs=std.executeQuery(sorgu);
				
				while (rs.next()) {
					int a=rs.getInt("gecmisid");
					String b=rs.getString("coinisim");
					String c=rs.getString("alissatis");
					int d=rs.getInt("miktar");
					
					double e=rs.getDouble("tlkarsiligi");
					int f=rs.getInt("kullaniciid");
					Model obj=new Model(b, c, d, e);
					
					
					list.add(obj);
					
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		ad.setCellValueFactory(new PropertyValueFactory<Model,String>("coinisim"));
		islem.setCellValueFactory(new PropertyValueFactory<Model,String>("alissatis"));
		miktar.setCellValueFactory(new PropertyValueFactory<Model,Integer>("miktar"));
		fiyat.setCellValueFactory(new PropertyValueFactory<Model,Double>("tlkarsiligi"));
		
		
		tablo.setItems(list);
		
	}

}
