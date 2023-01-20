public class Libro extends ElementoBiblioteca {

	private String autore;
	private String genere;
	
	public Libro(long codiceIsbn, String titolo, int annoPubblicazione, int nPagine, String autore, String genere) {
		super(codiceIsbn, titolo, annoPubblicazione, nPagine);
		this.autore = autore;
		this.genere = genere;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	@Override
	public String toString() {
		return "Libro codice Isbn = " + getCodiceIsbn() + " autore = " + autore + ", titolo = " + getTitolo() + ", anno pubblicazione = " + getAnnoPubblicazione()
		+ ", genere = " + genere + ", numero pagine = " + getnPagine();
	}
	
	
	
}
