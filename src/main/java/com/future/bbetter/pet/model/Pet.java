package com.future.bbetter.pet.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Pet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "pet_id")
	private Integer petId;
	@Column(name = "name")
	private String name;
	@Column(name = "init_height")
	private Integer initHeight;
	@Column(name = "init_weight")
	private Integer initWeight;
	@Column(name = "init_vision")
	private Integer initVision;
	@Column(name = "init_hp")
	private Integer initHp;
	@Column(name = "init_mp")
	private Integer initMp;
	@Column(name = "init_mentality")
	private Integer initMentality;
	@Column(name = "init_strength")
	private Integer initStrength;
	@Column(name = "init_intelligence")
	private Integer initIntelligence;
	@Column(name = "init_dexterity")
	private Integer initDexterity;
	@Column(name = "init_vitality")
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
