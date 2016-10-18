package org.ctbto.hr.hcm.utils;

import java.util.Comparator;
import java.util.Date;

public class OutcomeAscComparator implements Comparator<Date>{
	public int compare(Date left, Date right) {
        return left.compareTo(right);
    }
}
