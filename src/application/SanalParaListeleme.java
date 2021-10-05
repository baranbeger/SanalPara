package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.OrderBook;
import com.binance.api.client.domain.market.OrderBookEntry;

import com.binance.api.client.domain.market.TickerStatistics;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SanalParaListeleme implements Initializable{
	Connection con;
	Statement std;
	ResultSet rs;

    @FXML
    private ComboBox<String> cmbtxt;

    @FXML
    private TextField txtTutar;

    @FXML
    private Label birinci;

    @FXML
    private Label ucuncu;

    @FXML
    private Label besinci;

    @FXML
    private Label yedinci;

    @FXML
    private Label ikinci;

    @FXML
    private Label dorduncu;

    @FXML
    private Label altinci;

    @FXML
    private Label sekizinci;

    @FXML
    private Label toplamParaTxt;
    
    @FXML
    private Label toplamParaTxt1sanal;
    
    String bir,iki,uc,dort,bes,alti,yedi,sekiz;
    ArrayList<String> comboList=new ArrayList<String>();
    
    String outCmb;
    Double secilenPara;
    Double toplampara,toplamsanalpara;

    @FXML
    void cmbSec(ActionEvent event) {
    	BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("API-KEY", "SECRET");
		BinanceApiRestClient client = factory.newRestClient();
		
		client.ping();
		long serverTime = client.getServerTime();
    	outCmb=cmbtxt.getSelectionModel().getSelectedItem().toString();
    	
    	
    	
    	
    	if(!outCmb.equals(null)) {
    		if(outCmb.equals("Bitcoin")) {
    			TickerStatistics bitcoin = client.get24HrPriceStatistics("BTCUSDT");
    			 System.out.println("bitcoin"+" "+bitcoin.getLastPrice());
    			secilenPara=Double.parseDouble(bitcoin.getLastPrice());
    			System.out.println("yuksek para:"+bitcoin.getHighPrice());
    			System.out.println("yuksek para:"+bitcoin.getWeightedAvgPrice());
    			
    		}
    		else if(outCmb.equals("Etherium")){
    			TickerStatistics etherium = client.get24HrPriceStatistics("ETHUSDT");
    			System.out.println("eth:"+etherium.getLastPrice());
    			secilenPara=Double.parseDouble(etherium.getLastPrice());
    		}
             else if(outCmb.equals("Ripple")){
            	 TickerStatistics ripple = client.get24HrPriceStatistics("XRPUSDT");
            	 secilenPara=Double.parseDouble(ripple.getLastPrice());
    		}
    		
             else if(outCmb.equals("Litecoin")){
            	 TickerStatistics Litecoin = client.get24HrPriceStatistics("LTCUSDT");
            	 secilenPara=Double.parseDouble(Litecoin.getLastPrice());
     		}
    		
             else if(outCmb.equals("NEOETH")){
            	 TickerStatistics neoeth = client.get24HrPriceStatistics("NEOETH");
            	 secilenPara=Double.parseDouble(neoeth.getLastPrice());
     			
     		}
    		
             else if(outCmb.equals("ETHBTC")){
            	 TickerStatistics ethbtc = client.get24HrPriceStatistics("ETHBTC");
            	 secilenPara=Double.parseDouble(ethbtc.getLastPrice());
     		}
    		
             else if(outCmb.equals("LTCBTC")){
            	 TickerStatistics ltcbtc = client.get24HrPriceStatistics("LTCBTC");
            	 secilenPara=Double.parseDouble(ltcbtc.getLastPrice());
     		}
    		
             else if(outCmb.equals("LINKETH")){
            	 TickerStatistics eth = client.get24HrPriceStatistics("LINKETH");
            	 secilenPara=Double.parseDouble(eth.getLastPrice());
     		}
    		
    		
    		
    	}
    	else {
    		Alert al=new Alert(AlertType.ERROR);
    		al.setHeaderText("Boþluk hatasý");
    		al.setContentText("lütfen iþlem yapacaðýn coin seç:");
    		al.show();
    		
    	}
    	

    }
    @FXML
    void fonkGecmis(ActionEvent event) {
    	
    	try {
			URL url = new File("src/application/GecmisTablo.fxml").toURI().toURL();
			Parent fxml = FXMLLoader.load(url);
			Stage stage = new Stage();
			Scene scene = new Scene(fxml);
		
			
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {

			System.out.println( "Hata :" + e.getMessage());
		}

    }

    @FXML
    void satinalBtn(ActionEvent event) {
    	double a,toplam;
    	
    	String textPara=txtTutar.getText();
    	if(!textPara.equals(null)) {
    		a=Double.parseDouble(textPara);
    		toplam=a*secilenPara;
    		toplampara=toplampara-toplam;
    		toplamsanalpara=toplamsanalpara+a;
    		System.out.println("toplampara:"+toplampara);
    		System.out.println("sanalpara:"+toplamsanalpara);
    		
    		toplamParaTxt.setText(toplampara.toString());
    		toplamParaTxt1sanal.setText(toplamsanalpara.toString());
    		
    		try {
				Class.forName("com.mysql.jdbc.Driver");
				try {
					con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","123456789.");
					std=(Statement) con.createStatement();
					String sorgu="update kayitlar set toplamsanalpara="+toplamsanalpara+" and toplamtlpara="+
					toplampara+" where kullaniciid="+AnaSinifController.idler+";";
					std.execute(sorgu);
					System.out.println("update:  "+sorgu);
					
					
					String cmbChosean=cmbtxt.getSelectionModel().getSelectedItem().toString();
					
					String sorgu2="insert into gecmis (coinisim,alissatis,miktar,tlkarsiligi,kullaniciid)"+
					" values('"+cmbChosean+"','alis',"+textPara+","+a*8.52+","+AnaSinifController.idler+")";
					std.execute(sorgu2);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		
    		
    		
    		
    		
    		
    		
    	}
    	else {
    		Alert al=new Alert(AlertType.ERROR);
    		al.setHeaderText("Boþluk hatasý");
    		al.setContentText("lütfen texti doldur:");
    		al.show();
    	}

    }
    
    @FXML
    void satBtn(ActionEvent event) {
    	double a,toplam;
    	String textPara=txtTutar.getText();
    	if(!textPara.equals(null)) {
    		a=Double.parseDouble(textPara);
    		toplam=a*secilenPara;
    		
    		toplampara=toplampara+toplam;
    		
    		toplamsanalpara=toplamsanalpara-a;
    		if(toplamsanalpara<0 && toplamsanalpara==0) {
    			toplamsanalpara=0.0;
    			Alert al=new Alert(AlertType.ERROR);
    			al.setHeaderText("sanal para bakiyen yetersiz");
    			al.show();
    		}
    		
    		toplamParaTxt.setText(toplampara.toString());
    		toplamParaTxt1sanal.setText(toplamsanalpara.toString());
    		
    		try {
				Class.forName("com.mysql.jdbc.Driver");
				try {
					con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","123456789.");
					std=(Statement) con.createStatement();
					String sorgu="update kayitlar set toplamsanalpara="+toplamsanalpara+" and toplamtlpara="+
					toplampara+" where kullaniciid="+AnaSinifController.idler+";";
					std.execute(sorgu);
					String cmbChosean=cmbtxt.getSelectionModel().getSelectedItem().toString();
					String sorgu2="insert into gecmis (coinisim,alissatis,miktar,tlkarsiligi,kullaniciid)"+
							" values('"+cmbChosean+"','satis',"+textPara+","+a*8.52+","+AnaSinifController.idler+")";
							std.execute(sorgu2);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		
    		
    	}
    	else {
    		Alert al=new Alert(AlertType.ERROR);
    		al.setHeaderText("Boþluk hatasý");
    		al.setContentText("lütfen texti doldur:");
    		al.show();
    	}
    }

    @FXML
    void yenileBtn(ActionEvent event) {
    	BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("API-KEY", "SECRET");
		BinanceApiRestClient client = factory.newRestClient();
		
		client.ping();
		long serverTime = client.getServerTime();
	     System.out.println(serverTime);
	     
	     OrderBook orderBook = client.getOrderBook("NEOETH", 10);
	     List<OrderBookEntry> asks = orderBook.getAsks();
	     OrderBookEntry firstAskEntry = asks.get(0);
	     System.out.println(firstAskEntry.getPrice() + " / " + firstAskEntry.getQty());
	     System.out.println("----------------");
	     TickerStatistics neoeth = client.get24HrPriceStatistics("NEOETH");
	     System.out.println("neoeth"+" "+neoeth.getLastPrice());
	     System.out.println("--------------");
	     
	     TickerStatistics ethbtc = client.get24HrPriceStatistics("ETHBTC");
	     System.out.println("ETHBTC"+" "+ethbtc.getLastPrice());
	     
 System.out.println("--------------");
	     
	     TickerStatistics ltcbtc = client.get24HrPriceStatistics("LTCBTC");
	     System.out.println("LTCBTC"+" "+ltcbtc.getLastPrice());
	     
	     
 System.out.println("--------------");
	     
	     TickerStatistics eth = client.get24HrPriceStatistics("LINKETH");
	     System.out.println("LINKETH"+" "+eth.getLastPrice());
	     
 System.out.println("--------------");
	  
 TickerStatistics bitcoin = client.get24HrPriceStatistics("BTCUSDT");
 System.out.println("bitcoin"+" "+bitcoin.getLastPrice());
 
 System.out.println("--------------");
 
 TickerStatistics etherium = client.get24HrPriceStatistics("ETHUSDT");
 System.out.println("etherium"+" "+etherium.getLastPrice());
 
 System.out.println("--------------");
 TickerStatistics Litecoin = client.get24HrPriceStatistics("LTCUSDT");
 System.out.println("litecoin"+" "+Litecoin.getLastPrice());
 
 System.out.println("--------------");
 TickerStatistics ripple = client.get24HrPriceStatistics("XRPUSDT");
 System.out.println("ripple"+" "+ripple.getLastPrice());
 
 bir=bitcoin.getLastPrice();
 birinci.setText(bir);
 iki=neoeth.getLastPrice();
 ikinci.setText(iki);
 uc=etherium.getLastPrice();
 ucuncu.setText(uc);
 dort=ethbtc.getLastPrice();
 dorduncu.setText(dort);
 bes=ripple.getLastPrice();
 besinci.setText(bes);
 alti=ltcbtc.getLastPrice();
 altinci.setText(alti);
 yedi=Litecoin.getLastPrice();
 yedinci.setText(yedi);
 sekiz=eth.getLastPrice();
 sekizinci.setText(sekiz);
    	
    }
    


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("API-KEY", "SECRET");
		BinanceApiRestClient client = factory.newRestClient();
		
		client.ping();
		long serverTime = client.getServerTime();
	     System.out.println(serverTime);
	     
	     OrderBook orderBook = client.getOrderBook("NEOETH", 10);
	     List<OrderBookEntry> asks = orderBook.getAsks();
	     OrderBookEntry firstAskEntry = asks.get(0);
	     System.out.println(firstAskEntry.getPrice() + " / " + firstAskEntry.getQty());
	     System.out.println("----------------");
	     TickerStatistics neoeth = client.get24HrPriceStatistics("NEOETH");
	     System.out.println("neoeth"+" "+neoeth.getLastPrice());
	     System.out.println("--------------");
	     
	     TickerStatistics ethbtc = client.get24HrPriceStatistics("ETHBTC");
	     System.out.println("ETHBTC"+" "+ethbtc.getLastPrice());
	     
 System.out.println("--------------");
	     
	     TickerStatistics ltcbtc = client.get24HrPriceStatistics("LTCBTC");
	     System.out.println("LTCBTC"+" "+ltcbtc.getLastPrice());
	     
	     
 System.out.println("--------------");
	     
	     TickerStatistics eth = client.get24HrPriceStatistics("LINKETH");
	     System.out.println("LINKETH"+" "+eth.getLastPrice());
	     
 System.out.println("--------------");
	  
 TickerStatistics bitcoin = client.get24HrPriceStatistics("BTCUSDT");
 System.out.println("bitcoin"+" "+bitcoin.getLastPrice());
 
 System.out.println("--------------");
 
 TickerStatistics etherium = client.get24HrPriceStatistics("ETHUSDT");
 System.out.println("etherium"+" "+etherium.getLastPrice());
 
 System.out.println("--------------");
 TickerStatistics Litecoin = client.get24HrPriceStatistics("LTCUSDT");
 System.out.println("litecoin"+" "+Litecoin.getLastPrice());
 
 System.out.println("--------------");
 TickerStatistics ripple = client.get24HrPriceStatistics("XRPUSDT");
 System.out.println("ripple"+" "+ripple.getLastPrice());
 
 bir=bitcoin.getLastPrice();
 birinci.setText(bir);
 iki=neoeth.getLastPrice();
 ikinci.setText(iki);
 uc=etherium.getLastPrice();
 ucuncu.setText(uc);
 dort=ethbtc.getLastPrice();
 dorduncu.setText(dort);
 bes=ripple.getLastPrice();
 besinci.setText(bes);
 alti=ltcbtc.getLastPrice();
 altinci.setText(alti);
 yedi=Litecoin.getLastPrice();
 yedinci.setText(yedi);
 sekiz=eth.getLastPrice();
 sekizinci.setText(sekiz);
 
 comboList.add("Bitcoin");
 comboList.add("Etherium");
 comboList.add("Ripple");
 comboList.add("Litecoin");
 comboList.add("NEOETH");
 comboList.add("ETHBTC");
 comboList.add("LTCBTC");
 comboList.add("LINKETH");
 
 cmbtxt.getItems().addAll(comboList);
 
 
 
System.out.println("asdasdas:"+AnaSinifController.idler);
 

	try {
		Class.forName("com.mysql.jdbc.Driver");
		
		try {
			con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","123456789.");
			
			std=(Statement) con.createStatement();
			System.out.println("bir");
			String sorgu = "select toplamsanalpara as sanal,toplamtlpara as tl from kayitlar"+
					" where kullaniciid="+AnaSinifController.idler+";";
			
			System.out.println(sorgu);
			
					rs=std.executeQuery(sorgu);
					
					while(rs.next()) {
						toplampara=rs.getDouble("tl");
						toplamsanalpara=rs.getDouble("sanal");
						
						System.out.println("sanal:"+toplamsanalpara);
						System.out.println("toplam:"+toplampara);
						
					}
					
					toplamParaTxt.setText(toplampara.toString());
					toplamParaTxt1sanal.setText(toplamsanalpara.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
		
		
		
		
		
		
	}

}