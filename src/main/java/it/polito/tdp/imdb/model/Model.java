package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	private ImdbDAO dao;
	private Graph<Director, DefaultWeightedEdge> grafo;
	private Map<Integer, Director> idMap;
	
	private List<Director> risultato;
	
	public Model() {
		this.dao = new ImdbDAO();
		this.idMap = new HashMap<>();
		
	}
	
	public String creaGrafo(int anno) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.dao.getVertici(anno, idMap);
		
		Graphs.addAllVertices(this.grafo, idMap.values());
		
		for(Adiacenza a : this.dao.getArchi(anno, idMap)) {
			if(!this.grafo.containsEdge(a.getD1(), a.getD2())) {
				Graphs.addEdgeWithVertices(this.grafo, a.getD1(), a.getD2(), a.getPeso());
			}
		}
		return "Grafo creato con: "+this.grafo.vertexSet().size()+" vertici e "+this.grafo.edgeSet().size()+
				" archi.";
	}

	public Set<Director> getVertici(){
		return this.grafo.vertexSet();
	}
	
	public List<DirectorPeso> getAdiacenti(Director director) {
		
		List<Director> adiacenti = Graphs.neighborListOf(this.grafo, director);
		
		List<DirectorPeso> lista = new ArrayList<>();
		for(Director d : adiacenti) {
			lista.add(new DirectorPeso(d, this.grafo.getEdgeWeight(this.grafo.getEdge(director, d))));
		}
		Collections.sort(lista);
		return lista;
	}
	
	public void getCammino(int attoriCondivisi, Director director) {
		
		this.risultato = new ArrayList<>();
		List<Director> parziale = new ArrayList<Director>();
		
		parziale.add(director);
		ricorsione(parziale, attoriCondivisi);
		
		
	}
	
	private void ricorsione(List<Director> parziale, int attoriCondivisi) {
		
		Director inizio = parziale.get(0);
		List<Director> adiacenti = Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1));
		
		if(calcolaPeso(parziale)>calcolaPeso(this.risultato)) {
			this.risultato = new ArrayList(parziale);
		}
		
		for(Director d : adiacenti) {
			if(this.calcolaPeso(parziale)<=attoriCondivisi && !parziale.contains(d)) {
				parziale.add(d);
				
				ricorsione(parziale, attoriCondivisi);
				parziale.remove(parziale.size()-1);
			}				
		}
	}
	
	private double calcolaPeso (List<Director> parziale) {
			
			double peso = 0.0;
			
			for(int i=0; i<parziale.size()-1; i++) {
				
				peso += this.grafo.getEdgeWeight(this.grafo.getEdge(parziale.get(i), parziale.get(i+1)));
				
			}
			return peso;
		}

	public List<Director> getRisultato() {
		risultato.remove(0);
		return risultato;
	}
	
	
	
}
