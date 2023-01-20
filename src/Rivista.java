public class Rivista extends ElementoBiblioteca {
	
	private Periodicita periodicita;

	public Rivista(long codiceIsbn, String titolo, int annoPubblicazione, int nPagine, Periodicita periodicita) {
		super(codiceIsbn, titolo, annoPubblicazione, nPagine);
		this.periodicita = periodicita;
	}

	public Periodicita getPeriodicita() {
		return periodicita;
	}

	public void setPeriodicita(Periodicita periodicita) {
		this.periodicita = periodicita;
	}

	@Override
	public String toString() {
		return "Rivista codice ISBN = " + getCodiceIsbn() + " titolo = " + getTitolo() + ", anno di pubblicazione = " + getAnnoPubblicazione() + " periodicita = " + periodicita +   ", numero pagine = " + getnPagine();
	}
	
}
