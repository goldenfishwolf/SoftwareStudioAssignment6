package main.java;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

import de.looksgood.ani.Ani;
import processing.core.PApplet;

/**
* This class is used to store states of the characters in the program.
* You will need to declare other variables depending on your implementation.
*/
public class Character {

	public float x, y, radius;
	public int index; //the index of character in the JSONArray
	public int net_index; //the order of the character on the circle
	private float orgX, orgY;
	private String name;
	private MainApplet parent; //can't using PApplet as type!
	private ArrayList<Character> targets;
	private boolean isAdded; //whether it's added in the circle
	private boolean isFocused; //whether user's mouse stop on it
	private boolean isDragging; //whether user drag this object
	private boolean aniIsEnabled; 
	//only enable change of this.x, this.y when go into display()'s isAdded{} for the first time
	private int colour; //the colour in json file
	//about the big circle
	private float circleX, circleY; 
	private float circleRadius;
	//the position(represented by rad) on the big circle
//	private float circleRad;
	
	/*
	 * Store these variables when instance created.
	 */
	public Character(MainApplet parent, String name, int colour, float x, float y, float circleX, float circleY, float circleRadius, int index)
	{
		this.parent = parent;
		this.name = name;
		this.colour = colour;
		this.x = x;
		this.y = y;
		//it's original position(on the left side of the big circle) 
		this.orgX = x;
		this.orgY = y;
		this.radius = 20;
		targets = new ArrayList<Character>();
		this.isFocused = false;
		this.isAdded = false;
		this.circleX = circleX;
		this.circleY = circleY;
		this.circleRadius = circleRadius;
		net_index = -1;
		this.index = index;
		this.isDragging = false;
		this.aniIsEnabled = false;
	}
	
	/*
	 * Use display() to draw the character on the sketch.
	 */
	public void display()
	{

		this.parent.fill(colour);
		
		
		if(!isFocused)
		{
			this.radius = 20;
		}else
		{ 
			//when get focused, the circle get bigger and show its name
			Ani.to(this, (float)0.5, "radius", 30);
		}
		if(this.parent.getIndexDragged() == -1)
		{
			if(!isAdded)
			{
				resetorg();
			}
		}

		this.parent.ellipse(this.x, this.y, this.radius, this.radius);
	}
	
	/*
	 * Add the target to the array list when loading file.
	 */
	public void addTarget(Character target)
	{
		this.targets.add(target);
	}
	
	public ArrayList<Character> getTargets()
	{
		return this.targets;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public boolean getIsFocused()
	{
		return this.isFocused;
	}
	
	//called whenever mouse moves
	//MOUSE_MOVED
	public void setIsFocused(float x, float y)
	{ //mouse's position
		if(Math.pow(this.x-x,2) + Math.pow(this.y-y,2)<=Math.pow(this.radius, 2))
			this.isFocused = true;
		else
			this.isFocused = false;
	}
	
	public int getColour()
	{
		return this.colour;
	}
	
	public void setColour(int colour)
	{
		this.colour = colour;
	}
	
	public boolean getIsAdded()
	{
		return this.isAdded;
	}
	
	
	//move the determination outside so it's easier
	public void setIsAdded(boolean isAdded)
	{
		this.isAdded = isAdded;
	}
	
	//called when the mouse is dragging
	//MOUSE_DRAGGED
	public void setDragPosition(float x, float y)
	{
//		if(this.isDragging){
		if(this.parent.getIndexDragged() == this.index)
		{
			this.x = x;
			this.y = y;
		}
	}
	
	public void resetorg()
	{
		Ani.to(this, (float)0.5, "x", orgX);
		Ani.to(this, (float)0.5, "y", orgY);
	}
	
}