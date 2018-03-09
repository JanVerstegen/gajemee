package com.qien.gajemee.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;


import org.springframework.stereotype.Component;

@Component
public class LeeftijdBerekenaar {
	
	public LocalDate getLocalDate (Date verjaardag) {
		LocalDate birthday = Instant.ofEpochMilli(verjaardag.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		return birthday;
	}
	
	public int berekenLeeftijd(Date leeftijd) {
		LocalDate birthday = getLocalDate(leeftijd);
        if (birthday != null) {
            return Period.between(birthday, LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }

}
