package it.polito.tdp.poweroutages.model;

public class Vicino implements Comparable<Vicino>{

	private Nerc vicino;
	private int peso;

	/**
	 * @param vicino
	 * @param peso
	 */
	public Vicino(Nerc vicino, int peso) {
		super();
		this.vicino = vicino;
		this.peso = peso;
	}

	public Nerc getVicino() {
		return vicino;
	}

	public void setVicino(Nerc vicino) {
		this.vicino = vicino;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "Vicino [vicino=" + vicino + ", peso=" + peso + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vicino == null) ? 0 : vicino.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vicino other = (Vicino) obj;
		if (vicino == null) {
			if (other.vicino != null)
				return false;
		} else if (!vicino.equals(other.vicino))
			return false;
		return true;
	}

	public int compareTo(Vicino other) {
		if (this.getPeso() < other.getPeso())
			return 1;
		else if (this.getPeso() > other.getPeso())
			return -1;
		else
			return 0;
	}

}
