package com.devglan.model;

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

public class TopicParentChild implements Serializable {
	private long id;
	private long parentCount;
	private long childCount;
	private String name;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getParentCount() {
		return parentCount;
	}
	public void setParentCount(long parentCount) {
		this.parentCount = parentCount;
	}
	public long getChildCount() {
		return childCount;
	}
	public void setChildCount(long childCount) {
		this.childCount = childCount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
