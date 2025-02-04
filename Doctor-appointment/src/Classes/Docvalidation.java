package Classes;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;
import java.sql.DriverManager;
import Classes.Hhelper;

public class Docvalidation {
	Hhelper hh = new Hhelper();
	public String mess = "";
	String ss;
	Boolean yes = false;
	
	public boolean namevalid(String content) {
		if (hh.zempty(content)) {
			mess= "Name is empty !";
			return false;
		}
		return true;
	}
	public boolean phonevalid(String content) {
		if (hh.zempty(content)) {
			mess= "Phone is empty !";
			return false;
		}
		return true;
	}
	public boolean emailvalid(String content) {
		if (hh.zempty(content)) {
			mess= "Email  is empty !";
			return false;
		}
		return true;
	}
	public boolean ssnvalid(String content) {
		if (hh.zempty(content)) {
			mess= "SSN is empty !";
			return false;
		}
		return true;
	}
	public boolean gendervalid(String content) {
		if (hh.zempty(content)) {
			mess= "Gender is empty !";
			return false;
		}
		return true;
	}
	public boolean qualvalid(String content) {
		if (hh.zempty(content)) {
			mess= "Qualification is empty !";
			return false;
		}
		return true;
	}
	public boolean addressvalid(String content) {
		if (hh.zempty(content)) {
			mess= "Address is empty !";
			return false;
		}
		return true;
	}
	
	public boolean insuranceidvalid(String content) {
		if (hh.zempty(content)) {
			mess= "insuranceid is empty !";
			return false;
		}
		return true;
	}
	public boolean positionvalid(String content) {
		if (hh.zempty(content)) {
			mess= "Position  is empty !";
			return false;
		}
		return true;
	}
	
}
