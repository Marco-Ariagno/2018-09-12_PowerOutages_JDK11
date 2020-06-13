package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.poweroutages.db.PowerOutagesDAO;

public class Model {
	
	private PowerOutagesDAO dao;
	private SimpleWeightedGraph<Nerc,DefaultWeightedEdge> grafo;
	private Map<Integer, Nerc> idMap;
	
	public Model() {
		dao=new PowerOutagesDAO();
	}
	
	public void creaGrafo() {
		idMap=new HashMap<>();
		grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		dao.loadAllNercs(idMap);
		List<Nerc> vertici=new ArrayList<>(idMap.values());
		Graphs.addAllVertices(grafo, vertici);
		for(Nerc partenza:idMap.values()) {
			for(Nerc arrivo: dao.getNeighbors(partenza, idMap)) {
				if(grafo.getEdge(partenza, arrivo)==null) {
					Graphs.addEdgeWithVertices(grafo, partenza, arrivo, dao.getPeso(partenza, arrivo));
				}
			}
		}
	}
	
	public List<Vicino> getVicini (Nerc partenza){
		List<Nerc> successori=new ArrayList<>(Graphs.neighborListOf(grafo, partenza));
		List<Vicino> result=new ArrayList<>();
		for(Nerc n:successori) {
			result.add(new Vicino(n,(int)grafo.getEdgeWeight(grafo.getEdge(partenza, n))));
		}
		Collections.sort(result);
		return result;
	}

	public SimpleWeightedGraph<Nerc, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
	
	
	

}
