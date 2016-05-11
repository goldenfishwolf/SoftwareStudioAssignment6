package main.java;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import de.looksgood.ani.Ani;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

/**
* This class is for sketching outcome using Processing
* You can do major UI control and some visualization in this class.  
*/
@SuppressWarnings("serial")
public class MainApplet extends PApplet{
	private String path = "main/resources/";
	private String file = "starwars-episode-1-interactions.json";
	private JSONObject data;
	private JSONArray nodes, links;
	private ArrayList<Character> characters;
	private final static int circleX = 600, circleY = 320, circleRadius = 250;
	
	private final static int width = 1200, height = 650;
	private ControlP5 cp5;
	private int episode;
	public Network network;
	public boolean isDraggingOne;
	
	public void setup() {

		size(width, height);
		characters = new ArrayList<Character>();
		smooth();
		loadData();
		cp5 = new ControlP5(this);
		cp5.addButton("addAll").setLabel("ADD ALL").setPosition(1000, 200).setSize(100,50);
		cp5.addButton("clear").setLabel("CLEAR").setPosition(1000, 350).setSize(100,50);
		Ani.init(this);
		episode = 1;
		file = "starwars-episode-" + episode + "-interactions.json";
		network = new Network(this, links, circleX, circleY, circleRadius);
		isDraggingOne = false;
	}

	public void draw() {
		background(255);
		fill(255);
		stroke(64, 128, 128);
		//if player drag the character into the circle, circle become thick
		for(Character ch : characters){
			if((Math.pow(circleX - ch.x,2) + Math.pow(circleY - ch.y,2)
								<= Math.pow(circleRadius, 2)) && ch.getIsDragging())
			{
				strokeWeight(3);
				break;
			}
			else
			{
				strokeWeight(1);
			}
		}
		//radius*2 since ellipse accept diameter
		ellipse(circleX, circleY, circleRadius*2, circleRadius*2);
		
		network.display();
		
		fill(139, 69, 19);//brown
		textSize(40);
		text("Star Wars" + episode, circleX-95, 45);
		fill(0);
		for(Character ch : characters){
			ch.display();
			if(ch.getIsFocused())
			{
				//when get focused, the circle get bigger and show its name
				Ani.to(ch, (float)0.3, "radius", 40);
//				this.parent.textSize(20);
//				this.parent.text(this.name, this.x-this.radius, this.y+this.radius);
				
				fill(64, 128, 128);
				rect(mouseX, mouseY-10, ch.getName().length()*16 + 10, 40, 12, 12, 12, 12);
				
				textSize(20);
				fill(255);
				text(ch.getName(), mouseX+15, mouseY+15);
			}
			else
			{ 
				Ani.to(ch, (float)0.3, "radius", 30);
			}
		}
		
	}
	
	public void keyPressed(){
		characters.clear();
		switch(key)
		{
		case '1':
			episode = 1;
			break;
		case '2':
			episode = 2;
			break;
		case '3':
			episode = 3;
			break;
		case '4':
			episode = 4;
			break;
		case '5':
			episode = 5;
			break;
		case '6':
			episode = 6;
			break;
		case '7':
			episode = 7;
			break;
		}
		file = "starwars-episode-" + episode + "-interactions.json";
		loadData();
		network.clearall(links);
	}

	private void loadData(){
		data = loadJSONObject(this.path + this.file);
		nodes = data.getJSONArray("nodes");
		links = data.getJSONArray("links");
		
		
//		int j = 0;
		for(int i = 0; i< nodes.size();i++){
			JSONObject node = nodes.getJSONObject(i);
			
			String name = node.getString("name");
			int colour = unhex(node.getString("colour").substring(1));
			Character ch = new Character(this, name, colour, 50+(i/10)*60, 
					50+(i%10)*60, circleX, circleY, circleRadius, i); //i: the order in JSON array
			//j = (j+1)%3;
			characters.add(ch);
		}
		
		for(int i = 0; i< links.size();i++){
			JSONObject link = links.getJSONObject(i);
			
			int source = link.getInt("source");
			int target = link.getInt("target");
//			int value = link.getInt("value");
			characters.get(source).getTargets().add(characters.get(target));
		}
	}

	public void mouseMoved()
	{
		for(Character ch : characters)
		{
			ch.setIsFocused(mouseX,mouseY);
		}
	}
	
	public void mouseReleased()
	{
		for(Character ch : characters)
		{
			if(ch.getIsDragging())
			{
				ch.setIsAdded(mouseX,mouseY);
				ch.resetIsDragging();
				if(ch.getIsAdded())
				{
					network.addNetwork(ch);
				}
				else
				{
					network.deductNetwork(ch);
				}
				break;
			}
		}
	}
	
	public void mouseDragged()
	{
		for(Character ch : characters)
		{
			ch.setDragPosition(mouseX,mouseY);
		}
	}
	
	public void mousePressed()
	{
		for(Character ch : characters)
		{
			ch.setIsDragging(mouseX,mouseY);
		}
	}
	
	public void addAll()
	{
		network.clearall();
		for(Character ch : characters)
		{
			ch.setIsAdded(circleX, circleY);
			network.addNetwork(ch);
		}
	}
	
	public void clear()
	{
		network.clearall();
	}
}