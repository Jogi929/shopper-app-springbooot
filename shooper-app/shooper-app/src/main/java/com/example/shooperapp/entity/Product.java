package com.example.shooperapp.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int product_id;
	private String name;
	private double price;
	private String category;
	
	@ManyToOne
	@JoinColumn
	@Schema(hidden=true)
	private Users user;
	
	@JsonIgnore
	@OneToMany(mappedBy="product",cascade = CascadeType.ALL)
	private List<Review> reviews=new ArrayList<Review>();
	
	@JsonIgnore
	@ManyToMany(mappedBy = "products")
	private List<Orders> orders=new ArrayList<Orders>();
}
