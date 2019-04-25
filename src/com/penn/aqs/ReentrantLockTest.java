package com.penn.aqs;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest extends Thread {

	public static ReentrantLock lock = new ReentrantLock();
	public static int i = 0;

	public ReentrantLockTest(String name) {
		super.setName(name);

	}

	@Override
	public void run() {
		for (int j = 0; j < 100000; j++) {
			lock.lock(); // 如果不加锁，则最后结果小于预期
			try {
				System.out.println(super.getName() + " " + i);
				i++;
			} finally {
				lock.unlock();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ReentrantLockTest test1 = new ReentrantLockTest("thread1");
		ReentrantLockTest test2 = new ReentrantLockTest("thread2");

		test1.start();
		test2.start();
		test1.join();
		test2.join();
		System.out.println(i);
	}
}
