package com.penn.cas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CASCounter {

	private SimulateCAS simulateCAS;

	public CASCounter() {
		this.simulateCAS = new SimulateCAS();
	}

	public int getCount() {
		return simulateCAS.getValue();
	}

	public int incrementAndGet() {
		int value;
		int newValue;
		do {
			value = simulateCAS.getValue();
			newValue = value + 1;

		} while (!simulateCAS.compareAndSet(value, newValue));
		return newValue;
	}

	public static void main(String[] args) throws InterruptedException {

		ExecutorService executorService = Executors.newFixedThreadPool(30);
		CASCounter casCounter = new CASCounter();
		for (int i = 0; i < 100000; i++) {
			executorService.submit(() -> {
				casCounter.incrementAndGet();
			});
		}
		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.MINUTES);
		System.out.println(casCounter.getCount());
	}
}
