package com.company.async.example.springboot.async.dal.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sites")
public class Site implements Serializable
{

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private Boolean isActive;

	public Site(){
		// NOTE: Need for Serialization / Deserialization
	}

	public Site(boolean isActive, String name){
		this.isActive = isActive;
		this.name = name;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId()
	{
		return id;
	}

	public void setId( Long id )
	{
		this.id = id;
	}

	@Column(name = "name")
	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	@Column(name = "is_active")
	public Boolean getIsActive()
	{
		return isActive;
	}

	public void setIsActive( Boolean isActive )
	{
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "Site{" +
				"id=" + id +
				", name='" + name + '\'' +
				", isActive=" + isActive +
				'}';
	}
}
