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
	public int indexDragged; //the index of character being dragged
	
	public void setup() 
	{

		size(width, getHeight());
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
//		isDraggingOne = false;
		indexDragged = -1;
	}

	public void draw() 
	{
		background(255);
		fill(255);
		stroke(64, 128, 128);
		//if player drag the character into the circle, circle become thick
		for(Character ch : characters){
			if((Math.pow(circleX - ch.x,2) + Math.pow(circleY - ch.y,2)
								<= Math.pow(circleRadius, 2)) && indexDragged == ch.index)
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
	
//	public void keyPressed()
//	{
//		characters.clear();
//		if((keyCode == LEFT) && (episode > 1))
//			episode--;
//		else if((keyCode == RIGHT) && (episode < 7))
//			episode++;
//		file = "starwars-episode-" + episode + "-interactions.json";
//		loadData();
//		network.clearall(links);
//	}
	
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

	private void loadData()
	{
		data = loadJSONObject(this.path + this.file);
		nodes = data.getJSONArray("nodes");
		links = data.getJSONArray("links");
		
		
//		int j = 0;
		for(int i = 0; i< nodes.size();i++)
		{
			JSONObject node = nodes.getJSONObject(i);
			
			String name = node.getString("name");
			int colour = unhex(node.getString("colour").substring(1));
			Character ch = new Character(this, name, colour, 50+(i/10)*60, 
					50+(i%10)*60, circleX, circleY, circleRadius, i); //i: the order in JSON array
//			j = (j+1)%3;
			characters.add(ch);
		}
		
		for(int i = 0; i< links.size();i++)
		{
			JSONObject link = links.getJSONObject(i);
			
			int source = link.getInt("source");
			int target = link.getInt("target");
//			int value = link.getInt("value");
			characters.get(source).getTargets().add(characters.get(target));
		}
	}
	
	public void mouseMoved(){
		super.mouseMoved();
		if(characters.size()>0)
			for(Character ch : characters)
			{
				ch.setIsFocused(mouseX, mouseY);
			}
	}
	
	public void mousePressed(MouseEvent e){
		super.mousePressed();
		if(isClickAddAll(e.getX(), e.getY()))
		{
				clear(); //first clear
				addAll();
		}else if(isClickClear(e.getX(), e.getY()))
		{
				clear();
		}else if(characters.size()>0)
			for(Character ch : characters)
			{	
				if((Math.pow(ch.x-e.getX(),2) + Math.pow(ch.y-e.getY(),2))<=
					Math.pow(ch.radius, 2))
//				System.out.println(ch.index + " " + isDraggingOne);
//				if(!isDraggingOne)
				{
					if(indexDragged == -1)
					{ //no one is being dragged
//						ch.setIsDragging(e.getX(), e.getY());
						indexDragged = ch.index;
//						System.out.println("indexDragged: " + indexDragged);
//						System.out.println();
					}
//					System.out.println();
//					ch.setIsDragging(e.getX(), e.getY());
//					if(Math.pow(ch.x-e.getX(),2) + Math.pow(ch.y-e.getX(),2)<=
//						Math.pow(ch.radius, 2))
//					{
//						isDraggingOne = true;
//						System.out.println(ch.index + " " + isDraggingOne);
//					}
				}
				
			}
	}
	
	public void mouseDragged()
	{
		super.mouseDragged();
		if(characters.size()>0)
			for(Character ch : characters)
			{
				if(indexDragged == ch.index)
					ch.setDragPosition(mouseX, mouseY);
			}
	}
	
	public void mouseReleased(MouseEvent e)
	{
		super.mouseReleased();
//		isDraggingOne = false;
		
		if((!isClickAddAll(e.getX(), e.getY())) && !isClickClear(e.getX(), e.getY()))
		{
			if(characters.size()>0)
			for(Character ch : characters)
			{
//				if(ch.getIsDragging()){
				if(indexDragged == ch.index)
				{
//					ch.resetIsDragging();
					if(Math.pow(circleX-ch.x,2) + Math.pow(circleY-ch.y,2)
							<= Math.pow(circleRadius, 2) )
					{
//						ch.setIsAdded(e.getX(), e.getY(), false, false);
						if(!ch.getIsAdded())
						{
							ch.setIsAdded(true);
							network.isDragintoCircle(ch);
							network.addNetwork(ch);
						}
						
					}
					else
					{ //drop the character out
//						ch.setIsAdded(1, 1, false, true);
						if(ch.getIsAdded())
						{
							network.deductNetwork(ch);
							ch.setIsAdded(false);
						}
					}
//					System.out.println(ch.net_index + " " + isDraggingOne);
					indexDragged = -1; 
				}
			}

		}
	}
	
	//mouseRealsed and mousePressed cannot work together with this method??
	public void addAll()
	{
		
//		Random random = new Random(getHeight());
		for(Character ch : characters)
		{
//			ch.setIsAdded(random.nextFloat(), random.nextFloat(), true, false);
			ch.setIsAdded(true);
			network.addNetwork(ch);
			network.isDragintoCircle(ch);
		}
	}
	public void clear()
	{
		network.clearall();
		for(Character ch : characters)
		{
//			ch.setIsAdded(1, 1, false, true);
			ch.setIsAdded(false);
		}
		
	}
	
	public boolean isClickAddAll(float x, float y)
	{
		return (x>=1000) && (x <=1000 + 100) && (y >=200) && (y<=200+50);
	}
	
	public boolean isClickClear(float x, float y)
	{
		return (x>=1000) && (x <=1000 + 100) && (y>=350) && (y<=350+50);
	}
	
	public int getIndexDragged()
	{
		return this.indexDragged;
	}

	public int getHeight() 
	{
		return height;
	}

}