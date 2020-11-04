package util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Util {
	public static long getTime() {
		return Timestamp.valueOf(LocalDateTime.now()).getTime();
	}
}