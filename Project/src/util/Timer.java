package util;

public class Timer extends Thread{
	private int seconds;
	
	@Override
	public void run() {
		try {
			while(true) {
				passTime();
				sleep(1000);
			}
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Timer() {
		setSeconds(120);
	}
	
	public void passTime() {
		this.seconds -= 1;
	}

	public String getSeconds() {
		return Integer.toString(seconds);
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
}