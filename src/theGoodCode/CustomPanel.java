package theGoodCode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

class CustomPanel extends JPanel {
    private List<Line> lines = new ArrayList<>();
    private Random random;
    private int width;
    private int height;
    private Player[] ps;
    private Timer time;
    private int runTime;
    private boolean win = false;
    
    //Color codes
    private int r;
    private int g;
    private int b;
    
    //toggle ensures color is more likely to move around
    //start at 2 otherwise it might never enter a high/low value
    //therefore getting stuck at 0;
    private int toggleR = 2;
    private int toggleG = 2;
    private int toggleB = 2;

    public CustomPanel(int width, int height, Player[] ps) {
    	runTime = (int) (System.nanoTime()/1000000000000.0);
    	this.ps = ps;
    	this.width = width;
    	this.height = height;
    	int seed = 0;
    	
    	for(Player p: ps) {
    		seed += p.getxGuess() + p.getyGuess();
    	}
    	
    	random = new Random(width + height + seed);//Insanely random value
    	
    	//set initial random color
    	//200 - 255 to start brighter
    	r = random.nextInt()%56 + 200;
    	g = random.nextInt()%56 + 200;
    	b = random.nextInt()%56 + 200;
    }
    
    public void startGame() {
		int x2 = random.nextInt() % (width - 190);//adjust for buffer for text
		int y2 = random.nextInt() % height;
		
		x2 = Math.max(0, x2);
		y2 = Math.max(0, y2);
		
        // Set the initial line starting at (100, 100) and ending at (200, 200)
        lines.add(new Line(x2 - 5, y2 - 5, x2, y2, getRandomColor()));
        runTime = (int) (System.nanoTime()/1000000000);
        // Timer to add a new random line every 500 milliseconds
        time = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRandomLine();
            }
        });
        time.start();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        this.width = getWidth();
        this.height = getHeight();
    }

    private void addRandomLine() {
        Line lastLine = lines.get(lines.size() - 1);
        int x2 = lastLine.x2;
        int y2 = lastLine.y2;

        // Generate a random end point for the new line
        int newX = x2 + random.nextInt(101) - 50;
        int newY = y2 + random.nextInt(101) - 50;
        
        // Ensure they are in window range
        newX = setIntoRangeX(newX);
        newY = setIntoRangeY(newY);
        
        //set all the player distances
        for(Player p : ps) {
        	p.setDistance(getDistance(newX, newY, p));
        	
        	//current distance is closest so set to closest
        	if(p.getDistance() < p.getClosestDistance()) {
        		p.setClosestDistance(p.getDistance());
        	}
        	if(p.getClosestDistance() == 0) {
        		p.setTime((int)(System.nanoTime()/1000000000 - runTime));
        		win = true;
        		time.stop();
        	}
        }

        // Add the new line to the list with a random color
        lines.add(new Line(x2, y2, newX, newY, getRandomColor()));

        // Repaint the panel
        repaint();
    }
    
    private int getDistance(int x, int y, Player p) {
    	return (int)Math.sqrt(Math.pow(x - p.getxGuess(), 2) + Math.pow(y - p.getyGuess(), 2));//distance away from their guess
    }

    private int setIntoRangeX(int x) {
        x = Math.max(0, x);
        x = Math.min(x, width - 190);
        return x;
    }

    private int setIntoRangeY(int y) {
        y = Math.max(0, y);
        y = Math.min(y, height);
        return y;
    }

    private Color getRandomColor() {
    	//toggle shifts to ensure colors cycle
        r = nextColorRand(r+toggleR);
        g = nextColorRand(g+toggleG);
        b = nextColorRand(b+toggleB);
        
        setToggles();
        
        return new Color(r, g, b);
    }
    
    /*
     * if less that 50, start increasing by extra 2
     * if greater than 220, start decreasing by extra 2
     */
    private void setToggles() {
    	if(r < 100) {
        	toggleR = 3;
        }else if(r > 250) {
        	toggleR = -1;
        }
    	if(g < 100) {
        	toggleG = 3;
        }else if(g > 250) {
        	toggleG = -1;
        }
    	if(b < 100) {
        	toggleB = 3;
        }else if(b > 250) {
        	toggleB = -1;
        }
    }
    
    /*
     * randomizes color slightly up or down and maps to 0-255 range
     */
    private int nextColorRand(int n) {
    	//more likely to go up if it is smaller
    	/*n += (random.nextInt()%5 - 2) + Math.min(7,(255.0/n));
    	n = Math.min(255, Math.max(0, n));
    	System.out.println(n);
    	return n;
    	*/
    	
    	n += random.nextInt()%5 - 2;
    	n = Math.min(255, Math.max(0, n));
    	return n;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if(win) {
        	super.paintComponent(g);
        	//time = null;//remove action timer to stop drawing lines
        	Player winner = null;
        	
        	//find the winner
        	for(Player p : ps) {
        		if(p.getClosestDistance() == 0) {
        			winner = p;
        		}else {
                    String closestString = p.getName() + "- Closest Distance: " + Integer.toString(p.getClosestDistance());
                    int stringWidth = g.getFontMetrics().stringWidth(closestString);
                    int x = getWidth() - stringWidth - 10; // Adjust for padding
                    int y = 10; // Adjust for vertical position
                    g.drawString(closestString, x, y);
        		}
        	}
        	
            String winText = winner.getName() + " is the Winner!";
            String winText2 = "They Won in " + winner.getWinTime() + " Seconds";
            //int stringWidth = Math.max(g.getFontMetrics().stringWidth(winText), g.getFontMetrics().stringWidth(winText2));
            int x = getWidth() - 180;//put in 190 pixel gap on right of screen
            int y = 10; // Adjust for vertical position
            g.drawString(winText, x, y);
            g.drawString(winText2, x, y + 10);
        }else {
        	// Draw all lines
            for (Line line : lines) {
                g.setColor(line.color);
                g.drawLine(line.x1, line.y1, line.x2, line.y2);
            }
            
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 10));
            
            String timeString = Long.toString((System.nanoTime()/1000000000) - runTime) + " seconds";
            int x = getWidth() - 180;//put in 190 pixel gap on right of screen
            g.drawString(timeString, x, 10);
            
            int i = 2;//start past the timer
            //draw every players distance and closest distance
            for(Player p : ps) {
            	String numberString =  p.getName() + " " + Integer.toString(p.getDistance());
                //set width to the biggest needed between the two lines
                String closestString = "Closest Distance: " + Integer.toString(p.getClosestDistance());
                //int stringWidth = Math.max(Math.max(g.getFontMetrics().stringWidth(numberString), g.getFontMetrics().stringWidth(closestString)), 100);
                //int x = getWidth() - stringWidth - 10; // Adjust for padding
                int y = 10 * i; // Adjust for vertical position
                i += 3;//adjust 2 down for closest and current for next player
                g.drawString(numberString, x, y);
                g.drawString(closestString, x, y + 10);
            }
        }
    }
}
