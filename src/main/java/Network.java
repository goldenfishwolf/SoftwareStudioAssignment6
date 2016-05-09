package main.java;

import java.util.ArrayList;
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
	private boolean isDraginto;
	private JSONArray links;
	
	public Network(PApplet parent ,JSONArray links , float circleX, float circleY, float radius )
	{
		this.parent = parent;
		net_num = 0;
		isDraginto = false;
		this.links = links;
		characterAdded = new ArrayList<Character>();
	}
	
	public void display()
	{
		// let the character be on the circle
		for(Character characher : characterAdded)
		{
			characher.x = (float)(Math.cos(Math.PI / characher.net_index) * radius);
			characher.y = (float)(Math.sin(Math.PI / characher.net_index) * radius);
		}
		
		
		float netX, netY, netwidth, netheight;
		int lineWeight = 0;
		//draw the line between the characters on the circle
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
					
					parent.strokeWeight(lineWeight);
					if(ch.x > characher.x)
					{
						netX = ch.x;
						netwidth = ch.x - characher.x;
					}
					else
					{
						netX = characher.x;
						netwidth = characher.x - ch.x;
					}
					
					if(ch.y > characher.y)
					{
						netY = ch.y;
						netheight = ch.y - characher.y;
					}
					else
					{
						netY = characher.y;
						netheight = characher.y - ch.y;
					}
					
					parent.arc(netX, netY, netwidth, 
							netheight, (float)(Math.PI/2), (float)(Math.PI*3/2) );
				}
				
			}
		}
		//if player drag the character into the circle, circle become thick
		if(isDraginto)
		{
			parent.strokeWeight(4);
		}
		else
		{
			parent.strokeWeight(1);
		}
		parent.ellipse(circleX,circleY,radius,radius);
		
	}
	
	public void isDragintoCircle(Character ch) 
	{
		if(Math.pow( this.circleX - ch.x, 2) + Math.pow( this.circleY - ch.y, 2)
										<= Math.pow( this.radius, 2))
		{
			isDraginto = true;
		}
	}
	
	public void addNetwork(Character ch)
	{
		characterAdded.add(ch);
		ch.net_index = net_num;
		net_num++;
	}
	
	public void clearall()
	{
		for(Character ch : characterAdded)
		{
			ch.resetorg();
			characterAdded.remove(ch);
		}
	}
}
