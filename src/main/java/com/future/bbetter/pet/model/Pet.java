package com.future.bbetter.pet.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Pet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer petId;
	private String name;
	private Integer initHeight;
	private Integer initWeight;
	private Integer initVision;
	private Integer initHp;
	private Integer initMp;
	private Integer initMentality;
	private Integer initStrength;
	private Integer initIntelligence;
	private Integer initDexterity;
	private Integer initVitality;
	
	public Pet() {
	}
	
	public Integer getPetId() {
		return petId;
	}
	public void setPetId(Integer petId) {
		this.petId = petId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getInitHeight() {
		return initHeight;
	}
	public void setInitHeight(Integer initHeight) {
		this.initHeight = initHeight;
	}
	public Integer getInitWeight() {
		return initWeight;
	}
	public void setInitWeight(Integer initWeight) {
		this.initWeight = initWeight;
	}
	public Integer getInitVision() {
		return initVision;
	}
	public void setInitVision(Integer initVision) {
		this.initVision = initVision;
	}
	public Integer getInitHp() {
		return initHp;
	}
	public void setInitHp(Integer initHp) {
		this.initHp = initHp;
	}
	public Integer getInitMp() {
		return initMp;
	}
	public void setInitMp(Integer initMp) {
		this.initMp = initMp;
	}
	public Integer getInitMentality() {
		return initMentality;
	}
	public void setInitMentality(Integer initMentality) {
		this.initMentality = initMentality;
	}
	public Integer getInitStrength() {
		return initStrength;
	}
	public void setInitStrength(Integer initStrength) {
		this.initStrength = initStrength;
	}
	public Integer getInitIntelligence() {
		return initIntelligence;
	}
	public void setInitIntelligence(Integer initIntelligence) {
		this.initIntelligence = initIntelligence;
	}
	public Integer getInitDexterity() {
		return initDexterity;
	}
	public void setInitDexterity(Integer initDexterity) {
		this.initDexterity = initDexterity;
	}
	public Integer getInitVitality() {
		return initVitality;
	}
	public void setInitVitality(Integer initVitality) {
		this.initVitality = initVitality;
	}

	@Override
	public String toString() {
		return "Pet [petId=" + petId + ", name=" + name + ", initHeight=" + initHeight + ", initWeight=" + initWeight
				+ ", initVision=" + initVision + ", initHp=" + initHp + ", initMp=" + initMp + ", initMentality="
				+ initMentality + ", initStrength=" + initStrength + ", initIntelligence=" + initIntelligence
				+ ", initDexterity=" + initDexterity + ", initVitality=" + initVitality + "]";
	}
	
	
}
