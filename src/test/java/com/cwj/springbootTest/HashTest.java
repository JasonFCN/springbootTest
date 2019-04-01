package com.cwj.springbootTest;

import com.cwj.springbootTest.domain.entity.UserInfo;

public class HashTest {
	public static void main(String[] args) {
		UserInfo userInfo = new UserInfo();
		int hashCode = userInfo.hashCode();
		System.out.println("hashCode:"+Integer.toHexString(hashCode)+"add:"+userInfo);
		//userInfo.setName("l");
		System.out.println("hashCode:"+Integer.toHexString(hashCode)+"add:"+userInfo);
	}
}
