package com.penn.immutableobject;

/**
 * MMSCInfo是一个严格意义上的不可变对象，虽然MMSCRouter对外提供了setInstance方法用于改变其静态字段instance的值，但它任然可被视作一个等效的不可变对象。
 * 这是因为setInstance方法仅仅改变instance变量指向的对象，而instance变量采用volatile修饰保证了其在多线程之间的内存可见性，所以这意味着setInstance对instance变量的改变无须加锁也能保证线程安全。
 * 而其他代码在调用MMSCRouter的相关方法获取路由信息时也无须加锁
 * 
 * @author penn
 *
 */
public final class MMSCInfo {

	private final String deviceID;
	private final String url;
	private final int maxAttachementSizeInBytes;

	public MMSCInfo(String deviceID, String url, int maxAttachementSizeInBytes) {
		this.deviceID = deviceID;
		this.url = url;
		this.maxAttachementSizeInBytes = maxAttachementSizeInBytes;
	}

	public MMSCInfo(MMSCInfo prototype) {
		this.deviceID = prototype.deviceID;
		this.url = prototype.url;
		this.maxAttachementSizeInBytes = prototype.maxAttachementSizeInBytes;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public String getUrl() {
		return url;
	}

	public int getMaxAttachementSizeInBytes() {
		return maxAttachementSizeInBytes;
	}

}
