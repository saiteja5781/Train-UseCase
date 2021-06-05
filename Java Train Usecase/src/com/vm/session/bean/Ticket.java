import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.vm.session.util.DBConnection;

public class Ticket {

	private static int counter=99;
	private String pnr ;
	private LocalDate bookingDate;

	private TreeMap<Passenger,Double> passengers;
	public Ticket(LocalDate bookingDate) {
		super();
		this.bookingDate = bookingDate;
		this.passengers= new TreeMap<Passenger,Double>();
		Ticket.counter++;

	}

	private String generatePNR(String bookingDateString,int trainNumber) throws Exception
	{
		Connection con = DBConnection.getConnection();
		PreparedStatement ps= con.prepareStatement("select * from trains");
		ResultSet rs=ps.executeQuery();
		String PNR="";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		bookingDateString = String.valueOf(bookingDate.format(dtf));
		int counter = 100;
		String counterString=Integer.toString(counter);
		while(rs.next())
		{
			if(trainNumber==rs.getInt(1))
			{
				PNR+=(String.valueOf(rs.getString(3).charAt(0))+String.valueOf(rs.getString(4).charAt(0))+"_"+bookingDateString+"_"+String.valueOf(counter));			
			}
		}
		return PNR;
	}

	private double calcPassengerFare(Passenger passenger,int trainNumber) throws Exception
	{
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("select * from trains");
		ResultSet rs=ps.executeQuery();
		double trainfare=0;
		while(rs.next())
		{
			if(rs.getInt(1)==trainNumber)
			{
				trainfare= rs.getInt(5);
				break;
			}
		}

		double fare=0;

		if(passenger.getAge()>=60)
		{
			fare = trainfare*0.4;
		}
		else if(passenger.getAge()<12)
		{
			fare=trainfare*0.5;
		}
		else if(passenger.getGender()=='F')
		{
			fare = trainfare*0.75;
		}
		else 
			fare = trainfare;
		return fare;
	}
	public void addPassenger(String name,int age,char gender,int trainNumber) throws Exception
	{
		Passenger passenger = new Passenger(name,age,gender);
		passengers.put(passenger,calcPassengerFare(passenger,trainNumber));
	}
	private double calculateTotalTicketPrice()
	{
		double totalFare=0.0;
		for(Double eachPassengerFare:passengers.values())
		{
			totalFare+=eachPassengerFare;
		}
		return totalFare;
	}
	private StringBuilder generateTicket(int trainNumber) throws Exception
	{
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("select * from trains");
		ResultSet rs=ps.executeQuery();
		String trainName="";
		String trainSource="";
		String trainDestination="";
		while(rs.next())
		{
			if(rs.getInt(1)==trainNumber)
			{
				trainName=rs.getString(2);
				trainSource=rs.getString(3);
				trainDestination=rs.getString(4);
				break;
			}
		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String bookingDateString = String.valueOf(bookingDate.format(dtf));
		String[] ticketDefaultTemplate = new String[9];

		ticketDefaultTemplate[0] = "PNR\t\t:"+generatePNR(bookingDateString,trainNumber)+"\n";
		ticketDefaultTemplate[1] = "Train no\t:"+String.valueOf(trainNumber)+"\n";
		ticketDefaultTemplate[2] = "Train Name\t:"+String.valueOf(trainName)+"\n";
		ticketDefaultTemplate[3] = "From\t\t:"+String.valueOf(trainSource)+"\n";
		ticketDefaultTemplate[4] = "To\t\t:"+String.valueOf(trainDestination)+"\n";
		ticketDefaultTemplate[5] = "Travel Date\t:"+bookingDate+"\n";
		ticketDefaultTemplate[6] = "\nPassengers:\n";
		ticketDefaultTemplate[7] = "Name\t\tAge\t\tGender\t\tFare\n";
		String[] ticketPrinter = new String[passengers.size()];

		Set<Entry<Passenger, Double>> set = passengers.entrySet();
		Iterator<Entry<Passenger, Double>> it = set.iterator();
		int i=0;
		while(it.hasNext()) {
			Map.Entry<Passenger,Double> passenger = (Entry<Passenger, Double>)it.next();
			ticketPrinter[i]=passenger.getKey().getName()+"\t\t"+passenger.getKey().getAge()+"\t\t"+passenger.getKey().getGender()+"\t\t"+passenger.getValue()+"\n";
			i++;    
		}
		ticketDefaultTemplate[8] = "Total Price\t\t:"+String.valueOf(calculateTotalTicketPrice());

		StringBuilder sb = new StringBuilder("");
		for(int j=0;j<ticketDefaultTemplate.length-1;j++)
			sb.append(ticketDefaultTemplate[j]);
		for(int k=0;k<ticketPrinter.length;k++)
			sb.append(ticketPrinter[k]);
		sb.append(ticketDefaultTemplate[ticketDefaultTemplate.length-1]);
		return sb;
	}
	public void writeTicket(String bookingDateString,int trainNumber) throws Exception
	{
		File f = new File(generatePNR(bookingDateString,trainNumber)+".txt");
		FileWriter fr = new FileWriter(f,true);
		BufferedWriter br = new BufferedWriter(fr);
		StringBuilder sb = generateTicket(trainNumber);
		br.write(sb.toString());
		br.flush();
		fr.flush();
		br.close();
		fr.close();
		System.out.println("Ticket Booked with PNR : "+generatePNR(bookingDateString,trainNumber)+".txt");

	}

	public static int getCounter() {
		return counter;
	}

	public static void setCounter(int counter) {
		Ticket.counter = counter;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public TreeMap<Passenger, Double> getPassengers() {
		return passengers;
	}

	public void setPassengers(TreeMap<Passenger, Double> passengers) {
		this.passengers = passengers;
	}
	
}