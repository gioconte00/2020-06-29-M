package it.polito.tdp.imdb.model;

public class DirectorPeso implements Comparable<DirectorPeso>{
	
	private Director director;
	private Double peso;
	
	
	public DirectorPeso(Director director, double peso) {
		super();
		this.director = director;
		this.peso = peso;
	}


	public Director getDirector() {
		return director;
	}


	public double getPeso() {
		return peso;
	}


	@Override
	public int compareTo(DirectorPeso o) {
		
		return -(this.peso.compareTo(o.peso));
	}


	@Override
	public String toString() {
		return director + " ( " + peso + " )";
	}


	
	
	

}
