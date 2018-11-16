package org.zhxie.sprinpoker.domain;

public class Player {
	private String name;

	private boolean isHost;

	private int fibonacciNum = -1;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isHost() {
		return isHost;
	}

	public void setHost(boolean isHost) {
		this.isHost = isHost;
	}

	public int getFibonacciNum() {
		return fibonacciNum;
	}

	public void setFibonacciNum(int fibonacciNum) {
		this.fibonacciNum = fibonacciNum;
	}


}
