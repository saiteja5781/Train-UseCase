package com.vm.session.bean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TravelDate {

		static DateTimeFormatter dtf1= DateTimeFormatter.ofPattern("dd/MM/yyyy");
		public static boolean travelDate(String bookingDate)
		{
			LocalDate today = LocalDate.now();
			LocalDate bkdate = LocalDate.parse(bookingDate,dtf1);
			if(bkdate.isAfter(today))
			{
				
				return true;
			}
			else
				return false;
		}
	
}
