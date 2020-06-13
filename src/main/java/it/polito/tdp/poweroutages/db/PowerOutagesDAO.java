package it.polito.tdp.poweroutages.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polito.tdp.poweroutages.model.Nerc;

public class PowerOutagesDAO {
	
	public void loadAllNercs(Map<Integer,Nerc> idMap) {

		String sql = "SELECT id, value FROM nerc";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				idMap.put(n.getId(), n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	public List<Nerc> getNeighbors(Nerc nerc, Map<Integer,Nerc> idMap) {
		String sql = "SELECT DISTINCT(nerc_one) as n FROM NercRelations WHERE nerc_two = ?";
		List<Nerc> neighbors = new ArrayList<Nerc>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, nerc.getId());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {				
				Nerc neighbor = idMap.get(rs.getInt("n"));
				if(neighbor == null){
					System.out.println("Errore in getNeighbors!!");
				}else{
					neighbors.add(neighbor);
				}
				
			}

			conn.close();
			return neighbors;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	
	public int getPeso(Nerc partenza, Nerc arrivo) {
		String sql="SELECT DISTINCT(YEAR(p1.date_event_began)), MONTH(p1.date_event_began) " + 
				"FROM poweroutages AS p1, poweroutages AS p2 " + 
				"WHERE p1.nerc_id=? AND p2.nerc_id=? " + 
				"AND  YEAR(p1.date_event_began)=YEAR(p2.date_event_began) AND MONTH(p1.date_event_began)=MONTH(p2.date_event_began)";
		int count = 0;

		try{
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, partenza.getId());
			st.setInt(2, arrivo.getId());
			
			ResultSet rs = st.executeQuery();
			while (rs.next()) {				
				count++;
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		return count;
	}
}
