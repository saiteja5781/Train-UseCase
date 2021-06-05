package com.vm.session.service;

import java.time.LocalDate;

import com.vm.session.dao.ITicketDao;
import com.vm.session.dao.TrainDAO;
public class TicketService implements ITicketService{
	ITicketDao ticketDao;
	public TicketService(LocalDate bookingDate) {
		super();
		this.ticketDao =  new TrainDAO(bookingDate);
	}

	public void addPassenger(String passengerName, int passengerAge, char passengerGender, int trainNumber) {
		// TODO Auto-generated method stub
		ticketDao.addPassenger(passengerName, passengerAge, passengerGender, trainNumber);
	}

	public void issueTicket(String bookingDateString, int trainNumber) throws Exception {
		ticketDao.issueTicket(bookingDateString, trainNumber);

	}

}
