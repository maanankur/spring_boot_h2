package com.topic.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Topic")
public class TopicDetails {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String name;
	
	// what entities does this entity reference?
	@ManyToMany
    private Set<TopicDetails> referencesTo;
    // what entities is this entity referenced from?
    @ManyToMany(mappedBy="referencesTo")
    private Set<TopicDetails> referencesFrom;
    
    public TopicDetails() {
    	
    }
    
    public TopicDetails(String name) {
    	this.name = name;
    }
    
    public TopicDetails init() {
        referencesTo = new HashSet<>();
        return this;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<TopicDetails> getReferencesTo() {
		return referencesTo;
	}

	public void setReferencesTo(Set<TopicDetails> referencesTo) {
		this.referencesTo = referencesTo;
	}

	public Set<TopicDetails> getReferencesFrom() {
		return referencesFrom;
	}

	public void setReferencesFrom(Set<TopicDetails> referencesFrom) {
		this.referencesFrom = referencesFrom;
	}
	
}
