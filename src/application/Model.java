package application;

public class Model {
	private String isim,islem;
	private int miktar;
	private double fiyat;
	
	
	public Model(String isim, String islem, int miktar, double fiyat) {
		
		this.isim = isim;
		this.islem = islem;
		this.miktar = miktar;
		this.fiyat = fiyat;
	}
	public String getIsim() {
		return isim;
	}
	public void setIsim(String isim) {
		this.isim = isim;
	}
	public String getIslem() {
		return islem;
	}
	public void setIslem(String islem) {
		this.islem = islem;
	}
	public int getMiktar() {
		return miktar;
	}
	public void setMiktar(int miktar) {
		this.miktar = miktar;
	}
	public double getFiyat() {
		return fiyat;
	}
	public void setFiyat(double fiyat) {
		this.fiyat = fiyat;
	}
	

}
