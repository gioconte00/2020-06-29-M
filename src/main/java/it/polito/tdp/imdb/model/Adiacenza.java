package it.polito.tdp.imdb.model;

public class Adiacenza {
	
	private Director d1;
	private Director d2;
	private double peso;
	
	
	public Adiacenza(Director d1, Director d2, double peso) {
		super();
		this.d1 = d1;
		this.d2 = d2;
		this.peso = peso;
	}


	public Director getD1() {
		return d1;
	}


	public Director getD2() {
		return d2;
	}


	public double getPeso() {
		return peso;
	}
	
	
	

	
}
