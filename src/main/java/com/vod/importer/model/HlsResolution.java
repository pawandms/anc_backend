package com.vod.importer.model;

public class HlsResolution {

	  private int width;
	  private int height;
	
	  
	  public HlsResolution(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	@Override
	public String toString() {
		return "Resolution [width=" + width + ", height=" + height + "]";
	}
	  
	  
	  
	  

}
