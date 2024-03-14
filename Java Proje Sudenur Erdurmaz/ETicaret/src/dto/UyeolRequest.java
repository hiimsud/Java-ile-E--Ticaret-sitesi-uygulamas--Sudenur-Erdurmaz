package dto;
/*Burada yapılan işlem geleb üye ol isteğini builder sayesinde döner.*/
import java.io.Serializable;
import java.lang.Thread.Builder;
import java.util.Random;

/**
 * @author busrayral
 */





public class UyeolRequest implements Serializable{
	private static final long serialVersionUID = 2499376845717117573L;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Double bakiye;
    private Long id = new Random().nextLong();
    
    
    public UyeolRequest (Builder builder) {
    	this.email= builder.email;
    	this.name= builder.name;
    	this.surname= builder.surname;
    	this.password= builder.password;
    	this.bakiye=builder.bakiye;
    }
   
    public Double getBakiye() {
		return bakiye;
	}

	public void setBakiye(Double bakiye) {
		this.bakiye = bakiye;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	public static final class Builder{
		private String name;
	    private String surname;
	    private String email;
	    private String password;
        private Double bakiye;
	    
	 public Builder () {
		 
	 }
	  
     public Builder bakiye(Double bakiye) {
			 this.bakiye= bakiye;
			 return this;
		 }
	  
	 public Builder name (String name) {
		 this.name= name;
		 return this;
	 }
	 public Builder surname (String surname) {
		 this.surname= surname;
		 return this;
	 }
	 public Builder email (String email) {
		 this.email= email;
		 return this;
	 }
	 public Builder password (String password) {
		 this.password= password;
		 return this;
	 }
	 public UyeolRequest build() {
		 return new UyeolRequest(this);
		
	 }
	}
}





















