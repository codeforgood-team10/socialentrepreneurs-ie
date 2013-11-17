package uk.seicfg.util;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public final class SEIDateUtils {
	
	public static SimpleDateFormat formatter;
	
	static{
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+0"));
	}

}
