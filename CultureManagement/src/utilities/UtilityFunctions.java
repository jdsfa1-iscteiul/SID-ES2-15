package utilities;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.validator.routines.EmailValidator;

import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;

public class UtilityFunctions {
	
	public static Timestamp formatAsTimestamp(DatePicker date, int hour, int minute, int second) {
		String time = date.getValue().toString() + " ";
		if(hour < 10)
			time+="0"+hour+":";
		else
			time+=hour+":";
		if(minute < 10)
			time+="0"+minute+":";
		else 
			time+=minute+":";
		if(second < 10)
			time+="0"+second+".0";
		else
			time+=second+".0";
		return Timestamp.valueOf(time);
	}

	public static String getCurrentDate() {
		 	Date now = new Date();
	        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEE, d MMM ''yy");
	        String s = simpleDateformat.format(now);
	        return s;
	}
	
	public static void generateAlertError(String headerText, String contentText) {
		Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setHeaderText(headerText);
		errorAlert.setContentText(contentText);
		errorAlert.showAndWait();
	}
	
	public static boolean isEmail(String email) {
		return EmailValidator.getInstance().isValid(email);
	}

}
