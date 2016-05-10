package main.java;

import java.util.ArrayList;

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
	private boolean isDraginto;
	private JSONArray links;
	
	public Network(PApplet parent ,JSONArray links , float circleX, float circleY, float radius )
	{
		this.parent = parent;
		net_num = 0;
		isDraginto = false;
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
		
		
		float netX, netY, netwidth, netheight;
		int lineWeight = 0;
		//draw the line between the characters on the circle
		if(characterAdded.size() >0)
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
					//using fill(255) to make it hollowed
//					parent.fill(255);
//					parent.arc(netX, netY, netwidth, 
//							netheight, (float)(Math.PI/2), (float)(Math.PI*3/2) );
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
//		parent.fill(255);
//		parent.ellipse(circleX, circleY, radius*2, radius*2);
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
		
		ch.net_index = net_num;
		net_num++;
		characterAdded.add(ch);
	}
	
	public void deductNetwork(Character ch){ 
		//change the last element's index to the index of the element that is going to be removed
		characterAdded.get(net_num-1).net_index = ch.net_index;
		characterAdded.remove(ch);
		net_num--;
	}
	
	public void clearall()
	{	
		//use .remove() will lead to ConcurrentModificationError
//		for(Character ch : characterAdded)
//		{
////			ch.resetorg();
////			
//			characterAdded.remove(ch);
//		}
		characterAdded.clear();
		isDraginto = false;
		net_num = 0;
	}
	
	public void clearall(JSONArray links){
		net_num = 0;
		isDraginto = false;
		this.links = links;
		characterAdded.clear();
	}
}
