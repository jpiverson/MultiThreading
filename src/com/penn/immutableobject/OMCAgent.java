package com.penn.immutableobject;

/**
 * Immutable Object模式适用的场景： 
 * 1.被建模对象的状态变化不频繁 
 * 2.同时对一组相关的数据进行写操作，因此需要保证原子性
 * 3.适用某个对象作为安全的HashMap的key
 * 
 * Immutable Object需要注意的问题：
 * 1.被建模对象的状态变更频繁时，会频繁创建新的不可变对象，因此会增加GC的负担和CPU消耗，需要综合考虑被建模对象的规模，代码目标运行环境的JVM内存分配情况，系统对吞吐率和响应性的要求
 * 2.使用等效或者近似的不可变对象 
 * 3.防御性复制
 * 
 * @author penn
 *
 */
public class OMCAgent extends Thread {

	@Override
	public void run() {
		boolean isTableModificationMsg = false;
		String updatedTableName = null;
		while (true) {

			// 省略其他代码
			/*
			 * 从OMC连接的Socket中读取消息并进行解析 解析到数据表更新消息后，重置MMSCRouter实例
			 */

			if (isTableModificationMsg) {
				if ("MMSCInfo".equals(updatedTableName)) {
					/*
					 * 代码会调用MMSCRouter的setInstance方法来替换MMSCRouter的实例为新创建的实例。
					 * 而新创建的MMSCRouter实例通过其构造器会生成多个新的MMSCInfo的实例
					 */
					MMSCRouter.setInstance(new MMSCRouter());
				}
			}
			// 省略其他代码
		}
	}
}