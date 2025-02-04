package Classes;

import java.util.Date;
import java.util.List;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Calhelper {
	Hhelper hh = new Hhelper();
	Color vzold = new Color(119, 191, 65);
	Color vred = new Color(246, 85, 31);
	Color ssarga = new Color(245, 212, 9);
	Color hatter = new Color(67, 62, 59);
	Color slilla = new Color(205, 134, 150);
	Color vlilla = new Color(170, 144, 189);
	public Color skek1 = new Color(74, 108, 180);
	Color vvkek1 = new Color(13, 182, 213);
	Color vpiros = new Color(217, 58, 39);
	Color spiros = new Color(233, 82, 113);
	Color vrozsaszin = new Color(205, 134, 150);
	Color rrozsaszin = new Color(236, 80, 83);
	Color rozsaszin = new Color(253, 99, 97);
	Color prozsaszin = new Color(255, 82, 136);
	Color plilla = new Color(243, 109, 230);
	Color psargaszold = new Color(215, 238, 98);
	Color pvzold = new Color(213, 255, 118);
	Color vvzold = new Color(85, 240, 86);
	public Color psarga = new Color(255, 255, 0);
	Color vszold = new Color(186, 255, 113);
	public Color dblue = new Color(57, 130, 136);
	public Color lblue = new Color(128, 223, 243);
	public Color lpink = new Color(255, 128, 163);
	public Color lorange = new Color(249, 186, 83);

	public String[] monthNames = { "January", "February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };

	private String[] dayNames = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
	private String[] workdays = { "   ", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };

	private String[] napnevek = { "Su", "Mo", "The", "We", "Thu", "Fri", "Sa" };
	private String[] times = { "07:00 - 08:00", "08:00 - 09:00", "09:00 - 10:00", "10:00 - 11:00", "11:00 - 12:00",
			"12:00 - 13:00", "13:00 - 14:00", "14:00 - 15:00", "15:00 - 16:00", "16:00 - 17:00", "17:00 - 18:00" };

	public String[] timesint() {
		return times;
	}

	public String[] daynames() {
		return dayNames;
	}

	public String[] wdaynames() {
		return workdays;
	}

	public String[] napnevek() {
		return napnevek;
	}

	public String[] monthnames() {
		return monthNames;
	}

	public int mynow(String which) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		if (which == "year") {
			int iyear = cal.get(Calendar.YEAR);
			return iyear;
		} else if (which == "month") {
			int imonth = cal.get(Calendar.MONTH);
			return imonth;
		} else if (which == "day") {
			int iday = cal.get(Calendar.DAY_OF_MONTH);
			return iday;
		} else if (which == "hour") {
			int ihour = cal.get(Calendar.HOUR_OF_DAY);
			return ihour;
		} else if (which == "minute") {
			int iminute = cal.get(Calendar.MINUTE);
			return iminute;
		}
		return 0;
	}

	public int numofmonth(String monthname) {
		int ho = -1;
		switch (monthname) {
		case "January":
			ho = 0;
			break;
		case "February":
			ho = 1;
			break;
		case "March":
			ho = 2;
			break;
		case "Aprilis":
			ho = 3;
			break;
		case "May":
			ho = 4;
			break;
		case "June":
			ho = 5;
			break;
		case "July":
			ho = 6;
			break;
		case "August":
			ho = 7;
			break;
		case "September":
			ho = 8;
			break;
		case "October":
			ho = 9;
			break;
		case "November":
			ho = 10;
			break;
		case "December":
			ho = 11;
			break;
		default:
			ho = -1;
		}
		return ho;
	}

	public String[] weekday(LocalDate ld) {
		String[] arr = new String[6];
		arr[0] = " ";
		LocalDate ldd = ld.with(DayOfWeek.MONDAY);
		arr[1] = ldd.toString();
		ldd = ld.with(DayOfWeek.TUESDAY);
		arr[2] = ldd.toString();
		ldd = ld.with(DayOfWeek.WEDNESDAY);
		arr[3] = ldd.toString();
		ldd = ld.with(DayOfWeek.THURSDAY);
		arr[4] = ldd.toString();
		ldd = ld.with(DayOfWeek.FRIDAY);
		arr[5] = ldd.toString();
		return arr;
	}

	public String daynameofdate(String sdate) {
		String dayname = "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(sdate, formatter);
		DayOfWeek day = date.getDayOfWeek();
		dayname = day.name();
		return dayname;
	}

	public int getDayNumber(String sdate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(sdate, formatter);
		DayOfWeek day = date.getDayOfWeek();
		return day.getValue();
	}

	public Boolean dateisworkday(String sdate) {
		Boolean ret = false;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(sdate, formatter);
		DayOfWeek day = date.getDayOfWeek();
		int daynum = day.getValue();
		if (daynum >= 1 && daynum <= 5) {
			ret = true;
		}
		return ret;
	}

	public String[] daytimes() {
		int n = 0;
		String[] daytimes = new String[44];
		for (int x = 0; x < 11; x++) {
			String ss = hh.itos(x + 7);
			String stime = hh.padLeftZeros(ss, 2) + ":";
			for (int i = 0; i < 4; i++) {
				String sss = stime + hh.padLeftZeros(hh.itos(i * 15), 2);
				daytimes[n] = sss;
				n++;
			}
		}
		return daytimes;
	}

	public ArrayList<String> timefill() {
		int n = 0;
		String[] daytimes = new String[44];
		for (int x = 0; x < 11; x++) {
			String ss = hh.itos(x + 7);
			String stime = hh.padLeftZeros(ss, 2) + ":";
			for (int i = 0; i < 4; i++) {
				String sss = stime + hh.padLeftZeros(hh.itos(i * 15), 2);
				daytimes[n] = sss;
				n++;
			}
		}
		ArrayList<String> times = new ArrayList<>(Arrays.asList(daytimes));
		return times;
	}
}
