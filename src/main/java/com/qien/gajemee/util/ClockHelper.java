package com.qien.gajemee.util;

import java.time.Instant;

import org.springframework.stereotype.Component;

@Component
public interface ClockHelper {
	
	public static long now() {
		return Instant.now().toEpochMilli();
	}

}
