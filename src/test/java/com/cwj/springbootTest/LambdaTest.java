package com.cwj.springbootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;



public class LambdaTest {
	public static void main(String args[]){
//		List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
//		Stream<String> stream = strings.stream();
//		List<String> filtered = stream.filter(string -> !string.isEmpty()).collect(Collectors.toList());
//		filtered.stream().forEach(System.out::println);
//		System.out.println(strings.size());
		
		//新的时间日期API
		LocalDateTime now = LocalDateTime.now();
		System.out.println(now);
		System.out.println(""+now.getYear()+now.getMonth()+now.getDayOfMonth());
		System.out.println(now.withMonth(12).withDayOfMonth(12));
		
		LocalDate localDate = LocalDate.of(2019, 3, 03);
		System.out.println(localDate);
		
		LocalTime localTime = LocalTime.of(23, 12);
		System.out.println(localTime);
		
		LocalDate parse = LocalDate.parse("2018-02-12");
		System.out.println(parse);
		
		LocalTime parse2 = LocalTime.parse("12:12:23");
		System.out.println(parse2);
		
		LocalDateTime parse3 = LocalDateTime.parse("2018-02-12T12:12:23");
		System.out.println(parse3);
		
	}
}
