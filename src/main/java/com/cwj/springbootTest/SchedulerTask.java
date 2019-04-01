package com.cwj.springbootTest;

import java.util.Date;

import org.springframework.stereotype.Component;

//@Component
public class SchedulerTask {
	private int count ;
	
	//@Scheduled(cron="*/6 * * * * ?")
	//@Scheduled(fixedRate = 6000) //：上一次开始执行时间点之后6秒再执行
	//@Scheduled(fixedDelay = 6000) //：上一次执行完毕时间点之后6秒再执行
	//@Scheduled(initialDelay=1000, fixedRate=6000) //：第一次延迟1秒后执行，之后按fixedRate的规则每6秒执行一次
    /*private void process(){
		System.out.println(new Date());
        System.out.println("this is scheduler task runing  "+(count++));
    }*/
}
