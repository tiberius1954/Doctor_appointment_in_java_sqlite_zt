package Classes;

public class Doctor {
	 private int did;
	 private String dname;
	  
	    
	    public Doctor( int did, String dname ) 
		  { 
		  this.did = did; 
		  this.dname = dname; 		 
		  } 	 
	    @Override
		  public String toString() 
		  { 	
		     	return dname; 	 
		  } 

	    public int getDid() {
	        return did;
	    }

	    public void setDid(int did) {
	        this.did = did;
	    }

	    public String getDname() {
	        return dname;
	    }
	    public void setDname(String dname) {
	        this.dname = dname;
	    }
	  }
