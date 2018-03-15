package com.penn.guardedsuspension;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

public class AlarmAgent {

	// 是否已连接服务器
	private volatile boolean connectedToServer = false;

	// 模式角色：GuardedSuspension.Predicate
	private final Predicate agnetConnected = new Predicate() {
		@Override
		public boolean evaluate() {
			return connectedToServer;
		}
	};

	// 模式角色：GuardedSuspension.Block
	private final Blocker blocker = new ConditionVarBlocker();

	// 心跳定时器
	private final Timer heartbeatTimer = new Timer(true);

	/**
	 * 发送告警信息
	 * 
	 * @param alarm
	 * @throws Exception
	 */
	public void sendAlarm(final String alarm) throws Exception {
		// 可能需要等待，知道AlarmAgent连接上服务器（或者连接中断后重新连接上服务器）
		// 模式角色：GuardedSuspension.GuardedAction
		GuardedAction<Void> guardedAction = new GuardedAction<Void>(agnetConnected) {
			public Void call() throws Exception {
				doSendAlarm(alarm);
				return null;
			}
		};
		blocker.callWithGuard(guardedAction);
	}

	// 通过网络连接将告警信息发送给告警服务器
	private void doSendAlarm(String alarm) {
		// 省略其他代码
		System.out.println("sending alarm " + alarm);
		// 模拟发送告警到服务器的耗时
		try {
			Thread.sleep(50);
		} catch (Exception e) {

		}
	}

	public void init() {
		// 省略其他代码
		// 连接线程
		Thread connectingThread = new Thread(new ConnectingTask());
		connectingThread.start();
		heartbeatTimer.schedule(new HeartbeatTask(), 60000, 2000);
	}

	public void disconnect() {
		// 省略其他代码
		System.out.println("disconnected from alarm server.");
		connectedToServer = false;
	}

	protected void onConnected() {
		try {
			blocker.signalAfter(new Callable<Boolean>() {
				@Override
				public Boolean call() {
					connectedToServer = true;
					System.out.println("connected to server");
					return Boolean.TRUE;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void onDisconnected() {
		connectedToServer = false;
	}

	private class ConnectingTask implements Runnable {
		@Override
		public void run() {
			// 省略其他代码
			// 模拟连接操作耗时
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				;
			}
			onConnected();
		}
	}

	private class HeartbeatTask extends TimerTask {
		// 省略其他代码
		@Override
		public void run() {
			// 省略其他代码
			if (!testConnection()) {
				onDisconnected();
				reconnect();

			}
		}
	}

	private boolean testConnection() {
		// 省略其他代码
		return true;
	}

	private void reconnect() {
		ConnectingTask connectingThread = new ConnectingTask();
		connectingThread.run();
	}
}
