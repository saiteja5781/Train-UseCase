package com.vm.session.service;

public interface ITicketService {

	public void addPassenger(String passengerName,int passengerAge,char passengerGender,int trainNumber);
	public void issueTicket(String bookingDateString,int trainNumber) throws Exception;
}
