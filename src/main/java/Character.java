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
	private PApplet parent;
	private ArrayList<Character> targets;
	private boolean isAdded; //whether it's added in the circle
	private boolean isFocused; //whether user's mouse stop on it
	private boolean isDragging; //whether user drag this object
	private boolean isAddedIsEnabled; //disable isAdded when dragging, enable it after that 
	//only enable change of this.x, this.y when go into display()'s isAdded{} for the first time
	private int colour; //the colour in json file
	//about the big circle
	private float circleX, circleY; 
	private float circleRadius;
	
	/*
	 * Store these variables when instance created.
	 */
	public Character(PApplet parent, String name, int colour, float x, float y, float circleX, float circleY, float circleRadius, int index)
	{
		this.parent = parent;
		this.name = name;
		this.colour = colour;
		this.x = x;
		this.y = y;
		//it's original position(on the left side of the big circle) 
		this.orgX = x;
		this.orgY = y;
		this.radius = 30;
		targets = new ArrayList<Character>();
		this.isFocused = false;
		this.isAdded = false;
		this.circleX = circleX;
		this.circleY = circleY;
		this.circleRadius = circleRadius;
		net_index = -1;
		this.index = index;
		this.isAddedIsEnabled = true;
	}
	
	/*
	 * Use display() to draw the character on the sketch.
	 */
	public void display(){

		
		this.parent.noStroke();
		this.parent.fill(colour);
		this.parent.ellipse(this.x, this.y, this.radius, this.radius);
		if(isAddedIsEnabled && !isDragging){
			if(!isAdded){
				resetorg();
			}
		}

	}
	
	/*
	 * Add the target to the array list when loading file.
	 */
	public void addTarget(Character target){
		this.targets.add(target);
	}
	
	public ArrayList<Character> getTargets(){
		return this.targets;
	}
	
	public String getName(){
		return this.name;
	}
	
	public boolean getIsFocused(){
		return this.isFocused;
	}
	
	//called whenever mouse moves
	//MOUSE_MOVED
	public void setIsFocused(float x, float y){ //mouse's position
		if(Math.pow(this.x-x,2) + Math.pow(this.y-y,2)<=Math.pow(this.radius, 2))
			this.isFocused = true;
		else
			this.isFocused = false;
	}
	
	public int getColour(){
		return this.colour;
	}
	
	public void setColour(int colour){
		this.colour = colour;
	}
	
	public boolean getIsAdded(){
		return this.isAdded;
	}
	
	//called after the mouse drop the object
	//MOUSE_RELEASED
	public void setIsAdded(float x, float y){ //mouse's position
		if((Math.pow(this.circleX-x,2) + Math.pow(this.circleY-y,2)<=Math.pow(this.circleRadius, 2))){
				this.x = (float) (Math.random()*this.parent.height);
				this.y = (float) (Math.random()*this.parent.height);
			
			this.isAdded = true; //the object falls in the big circle
		}else{
			this.isAdded = false; //the object falls out the big circle
			this.resetorg();
		}
	}
	
	public boolean getIsDragging(){
		return this.isDragging;
	}
	
	//called when the mouse press
	//MOUSE_PRESSED
	public void setIsDragging(float x, float y){
		if(Math.pow(this.x-x,2) + Math.pow(this.y-y,2)<=Math.pow(this.radius, 2)){
			this.isAddedIsEnabled = false;
			this.isDragging = true;
		}
	}
	
	//called when the mouse release
	//MOUSE_RELEASED
	public void resetIsDragging(){
		this.isDragging = false;
		this.isAddedIsEnabled = true;
	}
	
	//called when the mouse is dragging
	//MOUSE_DRAGGED
	public void setDragPosition(float x, float y){
		if(this.isDragging){
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