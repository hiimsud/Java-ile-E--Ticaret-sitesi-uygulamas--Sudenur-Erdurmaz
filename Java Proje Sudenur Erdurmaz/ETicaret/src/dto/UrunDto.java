package dto;

import java.lang.Thread.Builder;
import java.util.Random;



/**
 * @author busrayral
 */

public class UrunDto {
	
	private String urunAdi;
	private String urunTuru;
	private String urunMiktar;
	private String urunBirimFiyat;
    private int id ;
    
    
    public UrunDto(Builder builder) {
    	this.id = builder.id;
    	this.urunAdi = builder.urunAdi;
    	this.urunMiktar=builder.urunMiktar;
    	this.urunBirimFiyat = builder.urunBirimFiyat;
    	this.urunTuru = builder.urunTuru;
    	
    }

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrunAdi() {
		return urunAdi;
	}
	public void setUrunAdi(String urunAdi) {
		this.urunAdi = urunAdi;
	}
	public String getUrunTuru() {
		return urunTuru;
	}
	public void setUrunTuru(String urunTuru) {
		this.urunTuru = urunTuru;
	}
	public String getUrunMiktar() {
        return urunMiktar != null && !urunMiktar.isEmpty() ? urunMiktar : "0";
    }
	public void setUrunMiktar(String urunMiktar) {
		this.urunMiktar = urunMiktar;
	}
	public String getUrunBirimFiyat() {
        return urunBirimFiyat != null ? urunBirimFiyat : "0";
    }
	public void setUrunBirimFiyat(String urunBirimFiyat) {
		this.urunBirimFiyat = urunBirimFiyat;
	}
	
	
	public static final class Builder{
		
		private String urunAdi;
		private String urunTuru;
		private String urunMiktar;
		private String urunBirimFiyat;
	    private int id;
	    
		public Builder() {
			
		}
		
		public Builder urunID(int urunID) {
			this.id = urunID;
			return this;
		}
		
		public Builder urunAdi(String urunAdi) {
			this.urunAdi = urunAdi;
			return this;
		}
		
		
		public Builder urunTuru(String urunTuru) {
			this.urunTuru = urunTuru;
			return this;
		}
		
		public Builder urunMiktar(String urunMiktar) {
			this.urunMiktar = urunMiktar;
			return this;

		}
		
		public Builder urunBirimFiyat(String urunBirimFiyat) {
			this.urunBirimFiyat = urunBirimFiyat;
			return this;
		}
		
		
		 public UrunDto build() {
			 return new UrunDto(this);
			
		 }
		
	}
	

	
	
	
	
}
