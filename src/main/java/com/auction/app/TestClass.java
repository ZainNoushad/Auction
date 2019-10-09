package com.auction.app;

import java.io.File;

public class TestClass {
	public static void main(String[] args) {
		Thread th=new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					System.out.println(i);
				}
			}
		});
		
		Thread th2=new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					System.out.println(i);
				}
			}
		});
		
		th.start();
		th2.start();
	}
	
}
