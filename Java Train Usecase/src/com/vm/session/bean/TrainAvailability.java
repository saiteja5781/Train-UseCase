package com.vm.session.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainAvailability 
{
	public static boolean  trainPresent(Connection con,int trainNumber) throws Exception
	{
		PreparedStatement ps = con.prepareStatement("select * from trains");
		ResultSet rs=ps.executeQuery();
		int confirm=0;
		while(rs.next())
		{
			if(rs.getInt(1)==trainNumber)
			{
				confirm=1;
				break;
			}
		}
		if(confirm == 1)
			return true;
		else 
			return false;
	}
}