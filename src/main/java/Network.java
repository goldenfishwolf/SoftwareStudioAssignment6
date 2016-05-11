package main.java;

import java.util.ArrayList;
import java.util.Iterator;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;
/** This class is used for the visualization of the network.
 * Depending on your implementation, you might not need to use this class or create a class on your own.
 * I used the class to draw the circle and re-arrange nodes and links.
 * You will need to declare other variables.
 */
public class Network {
	
	private PApplet parent;
	private ArrayList<Character> characterAdded;
	private int net_num;
	private float circleX, circleY, radius;
	private JSONArray links;
	
	public Network(PApplet parent ,JSONArray links , float circleX, float circleY, float radius )
	{
		this.parent = parent;
		net_num = 0;
		this.links = links;
		characterAdded = new ArrayList<Character>();
		this.circleX = circleX;
		this.circleY = circleY;
		this.radius = radius;
		
	}
	
	public void display()
	{
		// let the character be on the circle
		for(Character characher : characterAdded)
		{	
			if(!characher.getIsDragging())
			{ 
				//when dragging, the mouse has the exclusive right of control
				characher.x = (float)(circleX + Math.cos(Math.PI * 2 * characher.net_index/net_num) * radius);
				characher.y = (float)(circleY + Math.sin(Math.PI * 2 * characher.net_index/net_num) * radius);
			}
		}
		
		
		int lineWeight = 0;
		//draw the line between the characters on the circle
		if(characterAdded.size() > 0)
		{
			for(Character characher : characterAdded)
			{
				for(Character ch : characher.getTargets())
				{
					if(ch.net_index != -1)
					{
						for(int i = 0; i < links.size(); i++)
						{
							JSONObject tem = links.getJSONObject(i);
							if((tem.getInt("source") == characher.index) && 
												(tem.getInt("target") == ch.index))
							{
								lineWeight = tem.getInt("value");
							}
						}
						
						parent.strokeWeight(lineWeight/4 + 1);		
						parent.noFill();
						parent.stroke(0);
						parent.line(characher.x, characher.y, ch.x, ch.y);
					}
					
				}
			}
		}
	}
	
	
	public void addNetwork(Character ch)
	{
		
		ch.net_index = net_num;
		net_num++;
		characterAdded.add(ch);
	}
	
	public void deductNetwork(Character ch)
	{ 
		//change the last element's index to the index of the element that is going to be removed
		if(characterAdded.indexOf(ch) != -1)
		{
			characterAdded.get( net_num - 1 ).net_index = ch.net_index;
			characterAdded.set(ch.net_index, characterAdded.get( net_num - 1 ) );
			ch.net_index = -1;
			characterAdded.remove(net_num-1);
			net_num--;
		}
	}
	
	public void clearall()
	{	
		
		for(Iterator<Character> iter = characterAdded.iterator(); iter.hasNext();) 
		{
			Character ch = iter.next();
			ch.setIsAdded(0,0);
			ch.net_index = -1;
			iter.remove();
		}
		net_num = 0;
	}
	
	public void clearall(JSONArray links){
		for(Iterator<Character> iter = characterAdded.iterator(); iter.hasNext();) 
		{
			Character ch = iter.next();
			ch.setIsAdded(0,0);
			ch.net_index = -1;
		    iter.remove();
		}
		net_num = 0;
		this.links = links;
	}
}
