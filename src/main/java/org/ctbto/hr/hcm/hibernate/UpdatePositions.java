package org.ctbto.hr.hcm.hibernate;

public class UpdatePositions {
	
	public Integer 	positionId;
	public String 	possitionOffered;
	public String	staInFTPosition;
	public String 	notAdvertized;
	
	
	public UpdatePositions (){
		
	}
	
	public UpdatePositions (Integer positionId, String possitionOffered, String staInFTPosition, String notAdvertized){
		this.positionId = positionId;
		this.possitionOffered = possitionOffered;
		this.staInFTPosition =staInFTPosition;
		this.notAdvertized = notAdvertized;
		
	}
	public Integer getPositionId() {
		return positionId;
	}
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}
	public String getPossitionOffered() {
		return possitionOffered;
	}
	public void setPossitionOffered(String possitionOffered) {
		this.possitionOffered = possitionOffered;
	}
	public String getStaInFTPosition() {
		return staInFTPosition;
	}
	public void setStaInFTPosition(String staInFTPosition) {
		this.staInFTPosition = staInFTPosition;
	}
	public String getNotAdvertized() {
		return notAdvertized;
	}
	public void setNotAdvertized(String notAdvertized) {
		this.notAdvertized = notAdvertized;
	}
	
	
	
}
