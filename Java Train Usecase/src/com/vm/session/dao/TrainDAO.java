package com.vm.session.dao;

import java.sql.Connection;
import java.time.LocalDate;

import com.vm.session.bean.Ticket;
public class TrainDAO implements ITicketDao{

	Ticket ticket;

	public TrainDAO(LocalDate bookingDate) {
		super();
		this.ticket = new Ticket(bookingDate);
	}

	@Override
	public void addPassenger(String passengerName, int passengerAge, char passengerGender, int trainNumber) {
		try 
		{
			ticket.addPassenger(passengerName, passengerAge, passengerGender, trainNumber);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void issueTicket(String bookingDateString, int trainNumber) throws Exception {
		ticket.writeTicket(bookingDateString, trainNumber);

	}

}
