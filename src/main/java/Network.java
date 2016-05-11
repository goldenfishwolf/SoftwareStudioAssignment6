package main.java;

import java.util.ArrayList;

import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;
import java.util.Iterator;
/** This class is used for the visualization of the network.
 * Depending on your implementation, you might not need to use this class or create a class on your own.
 * I used the class to draw the circle and re-arrange nodes and links.
 * You will need to declare other variables.
 */
public class Network{
	
	private MainApplet parent;
	private ArrayList<Character> characterAdded;
	private int net_num;
	private float circleX, circleY, radius;
	private boolean isDraginto;
	private JSONArray links;
	private boolean isDisplaying; //swap the right of control between display() and other functions
	
	public Network(MainApplet parent ,JSONArray links , float circleX, float circleY, float radius )
	{
		this.parent = parent;
		net_num = 0;
		isDraginto = false;
		this.links = links;
		characterAdded = new ArrayList<Character>();
		this.circleX = circleX;
		this.circleY = circleY;
		this.radius = radius;
		this.isDisplaying = true;
	}
	
	public void display()
	{
		// let the character be on the circle
		if((characterAdded.size() >0) && (isDisplaying) && (this.parent.indexDragged==-1))
		for(Character characher : characterAdded)
		{	
				//when dragging, the mouse has the exclusive right of control
				if(characher.getIsAdded()){
					characher.x = (float)(circleX + Math.cos(Math.PI * 2 * characher.net_index/net_num) * radius);
					characher.y = (float)(circleY + Math.sin(Math.PI * 2 * characher.net_index/net_num) * radius);
				}

		}
		
		float netX, netY, netwidth, netheight, netstart,netend;
		int lineWeight = 0;
		//draw the line between the characters on the circle
		if((characterAdded.size() >0) && (isDisplaying) && (this.parent.indexDragged==-1))
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
//					if(ch.x > characher.x)
//					{
//						netX = ch.x;
//						netwidth = ch.x - characher.x;
//					}
//					else
//					{
//						netX = characher.x;
//						netwidth = characher.x - ch.x;
//					}
//					
//					if(ch.y > characher.y)
//					{
//						netY = ch.y;
//						netheight = ch.y - characher.y;
//					}
//					else
//					{
//						netY = characher.y;
//						netheight = characher.y - ch.y;
//					}
					//using fill(255) to make it hollowed
//					parent.fill(255);
//					parent.arc(netX, netY, netwidth, 
//							netheight, (float)(Math.PI/2), (float)(Math.PI*3/2) );
					parent.noFill();
					parent.stroke(0);
					parent.line(characher.x, characher.y, ch.x, ch.y);
				}
				
			}
		}
		//if player drag the character into the circle, circle become thick
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
		isDisplaying = false;
		ch.net_index = net_num;
		net_num++;
		characterAdded.add(ch);
		isDisplaying = true;
	}
	
	public void deductNetwork(Character ch)
	{ 
		//change the last element's index to the index of the element that is going to be removed
		isDisplaying = false;
//		int ch_order = characterAdded.indexOf(ch);
//		net_num--;
//		characterAdded.get( net_num ).net_index = ch.net_index;
//		characterAdded.set( ch_order, characterAdded.get( net_num ) );
////		characterAdded.remove(characterAdded.get( net_num )); wrong!
//		characterAdded.remove(net_num);
		
		if(characterAdded.indexOf(ch) != -1)
		{
			characterAdded.get( net_num - 1 ).net_index = ch.net_index;
			characterAdded.set(ch.net_index, characterAdded.get( net_num - 1 ) );
			ch.net_index = -1;
			characterAdded.remove(net_num-1);
			net_num--;
		}
		isDisplaying = true;
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
		isDisplaying = false;
		characterAdded.clear();
		isDraginto = false;
		net_num = 0;
		isDisplaying = true;
	}
	
	public void clearall(JSONArray links)
	{
		isDisplaying = false;
		net_num = 0;
		isDraginto = false;
		this.links = links;
		characterAdded.clear();
		isDisplaying = true;
	}
}

