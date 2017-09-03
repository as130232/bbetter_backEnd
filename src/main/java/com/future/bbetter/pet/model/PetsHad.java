package com.future.bbetter.pet.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class PetsHad {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "pets_had_id")
	private Integer petsHadId;
	@Column(name = "member_id")
	private Long memberId;
	@Column(name = "nickname")
	private String nickname;
	@Column(name = "pet_id")
	private Integer petId;
	@Column(name = "is_current")
	private Integer isCurrent;
	@Column(name = "height")
	private Integer height;
	@Column(name = "weight")
	private Integer weight;
	@Column(name = "vision")
	private Integer vision;
	@Column(name = "hp")
	private Integer hp;
	@Column(name = "mp")
	private Integer mp;
	@Column(name = "mentality")
	private Integer mentality;
	@Column(name = "strength")
	private Integer strength;
	@Column(name = "intelligence")
	private Integer intelligence;
	@Column(name = "dexterity")
	private Integer dexterity;
	@Column(name = "vitality")
	private Integer vitality;
	@Column(name = "is_death")
	private Integer isDeath;
	
	public PetsHad() {
	}

	public Integer getPetsHadId() {
		return petsHadId;
	}

	public void setPetsHadId(Integer petsHadId) {
		this.petsHadId = petsHadId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(Integer isCurrent) {
		this.isCurrent = isCurrent;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getVision() {
		return vision;
	}

	public void setVision(Integer vision) {
		this.vision = vision;
	}

	public Integer getHp() {
		return hp;
	}

	public void setHp(Integer hp) {
		this.hp = hp;
	}

	public Integer getMp() {
		return mp;
	}

	public void setMp(Integer mp) {
		this.mp = mp;
	}

	public Integer getMentality() {
		return mentality;
	}

	public void setMentality(Integer mentality) {
		this.mentality = mentality;
	}

	public Integer getStrength() {
		return strength;
	}

	public void setStrength(Integer strength) {
		this.strength = strength;
	}

	public Integer getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(Integer intelligence) {
		this.intelligence = intelligence;
	}

	public Integer getDexterity() {
		return dexterity;
	}

	public void setDexterity(Integer dexterity) {
		this.dexterity = dexterity;
	}

	public Integer getVitality() {
		return vitality;
	}

	public void setVitality(Integer vitality) {
		this.vitality = vitality;
	}

	public Integer getIsDeath() {
		return isDeath;
	}

	public void setIsDeath(Integer isDeath) {
		this.isDeath = isDeath;
	}

	@Override
	public String toString() {
		return "PetsHad [petsHadId=" + petsHadId + ", memberId=" + memberId + ", petId=" + petId + ", nickname="
				+ nickname + ", isCurrent=" + isCurrent + ", height=" + height + ", weight=" + weight + ", vision="
				+ vision + ", hp=" + hp + ", mp=" + mp + ", mentality=" + mentality + ", strength=" + strength
				+ ", intelligence=" + intelligence + ", dexterity=" + dexterity + ", vitality=" + vitality
				+ ", isDeath=" + isDeath + "]";
	}
	
	
}
