package com.example.demo.utility;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.example.demo.constant.BatteyEnum;

public class CsvDataReader {

	public static String checkBatteryStatus(float calculateBatteryPrecentage) {
			
			if(calculateBatteryPrecentage>=98)
			{
				return BatteyEnum.FULL.name();
			}else if(calculateBatteryPrecentage>=60 && calculateBatteryPrecentage<98)
			{
				return BatteyEnum.HIGH.name();
			}else if(calculateBatteryPrecentage>=40 && calculateBatteryPrecentage<60)
			{
				return BatteyEnum.MEDIUM.name();
			}
			else if(calculateBatteryPrecentage>=10 && calculateBatteryPrecentage<40)
			{
				return BatteyEnum.LOW.name();
			}
			else
			{
				return BatteyEnum.CRITICAL.name();
			}
		}
	
	
	public static String getCurrentEpochTimeStamp(String timeStamp) throws Exception {
		String FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
		long timeStampMillis = Long.parseLong(timeStamp);
		ZoneId zone = ZoneId.systemDefault();
		DateTimeFormatter df = DateTimeFormatter.ofPattern(FORMAT).withZone(zone);
		String time = df.format(Instant.ofEpochMilli(timeStampMillis));
		return time;
	}
	
	public static String getUtcTimeStamp()
	{
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		return String.valueOf(timeStampMillis);
	}
}
