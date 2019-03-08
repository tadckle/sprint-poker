package org.zhxie.sprintpoker.entity;

public class Player {

	private String name;

	private boolean isHost;

	private String fibonacciNum = "??";

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

	public String getFibonacciNum() {
		return fibonacciNum;
	}

	public void setFibonacciNum(String fibonacciNum) {
		this.fibonacciNum = fibonacciNum;
	}

	public  Player(String name) {
	  this.name = name;
  }

  public Player() {

  }
}
