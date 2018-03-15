package com.penn.guardedsuspension;

import java.util.concurrent.Callable;

public interface Blocker {

	<V> V callWithGuard(GuardedAction<V> guardedAction) throws Exception;

	void signalAfter(Callable<Boolean> stateOperation) throws Exception;

	void signle() throws InterruptedException;

	void broadcastAfter(Callable<Boolean> stateOperation) throws Exception;

}