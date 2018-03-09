package com.qien.gajemee.util;

import java.time.Instant;

import org.springframework.stereotype.Component;

@Component
public class Clock implements ClockHelper {
	
	
	public long getLongTime() {
		return ClockHelper.now();

}
}
