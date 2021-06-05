package com.vm.session.dao;

public interface ITicketDao {
	public void addPassenger(String passengerName,int passengerAge,char passengerGender,int trainNumber);
	public void issueTicket(String bookingDateString,int trainNumber) throws Exception;
}
