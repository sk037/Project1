package com;

import org.testng.TestNG;

public class Runner extends CreateUser{
        static TestNG testng;
		public static void main(String[] args) {
			//CreateUser testSuite = new CreateUser();
			//testSuite.setup();
			System.out.println("Working Directory = " + System.getProperty("user.dir"));
			testng=new TestNG();
			testng.setTestClasses(new Class[] {CreateUser.class});
			testng.run();
				
		}
	}
