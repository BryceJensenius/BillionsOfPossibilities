package theGoodCode;

public class Player {
	private String name;
	private int winTime;
	private int xGuess;
	private int yGuess;
	private int distance;
	private int closestDistance = 1000;
	
	public Player(String name, int x, int y) {
		this.name = name;
		this.xGuess = x;
		this.yGuess = y;
	}
	
	public void setTime(int time) {
		this.winTime = time;
	}

	public String getName() {
		return name;
	}

	public int getWinTime() {
		return winTime;
	}

	public int getxGuess() {
		return xGuess;
	}

	public int getyGuess() {
		return yGuess;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getClosestDistance() {
		return closestDistance;
	}

	public void setClosestDistance(int closestDistance) {
		this.closestDistance = closestDistance;
	}
}
