import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Predicate;

import org.apache.commons.io.FileUtils;

public class Archivio {
	
	private static final String ENCODING = "utf-8";
	static ArrayList<Libro> libri = new ArrayList<>();
	static ArrayList<Rivista> riviste = new ArrayList<>();
	static Scanner scan1 = new Scanner(System.in);
	String fileName = "archivio.txt";
	static File fileInfo = new File("archivio.txt");
	
	public static void main(String[] args) throws IOException {
				
		int selezione = 0;
		boolean continua = true;

		do {
			System.out.println("-------------------");
			System.out.println("ARCHIVIO BIBLIOTECA");
			System.out.println("-------------------");
			System.out.println("Cosa vuoi fare? Premi il numero corrispondente all'azione desiderata");
			System.out.println("0 - Esci");
			System.out.println("1 - Aggiungi elemento");
			System.out.println("2 - Rimuovi elemento tramite codice ISBN");
			System.out.println("3 - Ricerca elemento per codice ISBN");
			System.out.println("4 - Ricerca elemento per anno pubblicazione");
			System.out.println("5 - Ricerca elemento per autore");
			System.out.println("6 - Mostra archivio");
			
			try {
				selezione = Integer.parseInt(scan1.nextLine());
				
				switch(selezione) {
					case(0):
						System.out.println("Archivio chiuso");
						System.exit(0);
					case(1):
						aggiungiElemento();
						break;
					case(2):
						rimuoviElemento();
						break;
					case(3):
						ricercaPerIsbn();
						break;
					case(4):
						ricercaPerAnno();
						break;
					case(5):
						ricercaPerAutore();
						break;
					case(6):
						readFromFile(fileInfo);
						break;
					default:
						System.out.println("Valore Errato");
				}
				
				System.out.println("Vuoi ricominciare? Digita 'si' per continuare o qualunque carattere per uscire");
				String input = scan1.nextLine();
				continua = input.equalsIgnoreCase("si");
			} catch(NumberFormatException e) {
				System.out.println("Errore, formato non valido");
			}
			
		} while(continua);

	}
	
	public static void aggiungiElemento() {
		Scanner scan2 = new Scanner(System.in);
		System.out.println("Inserisci il tipo premendo 1 per Libro o 2 per Rivista");
		
		int tipo = Integer.parseInt(scan2.nextLine());
		
		if(tipo == 1) {
			System.out.println("Inserisci titolo:");
			String titolo = scan2.nextLine();
			System.out.println("Inserisci anno di pubblicazione");
			int anno = Integer.parseInt(scan2.nextLine());
			System.out.println("Inserisci numero di pagine");
			int pagine = Integer.parseInt(scan2.nextLine());
			System.out.println("Inserisci nome autore");
			String nome = scan2.nextLine();
			System.out.println("Inserisci genere del libro");
			String genere = scan2.nextLine();
			
			Libro libro = new Libro(generatoreISBN(), titolo, anno, pagine, nome, genere);
			libri.add(libro);
			
			try {
				writeOnFile(fileInfo, libro.toString() + System.lineSeparator(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("Libro " + libro.getTitolo() + " aggiunto");
			
		} else if (tipo == 2) {
			System.out.println("Inserisci titolo:");
			String titolo = scan2.nextLine();
			System.out.println("Inserisci anno di pubblicazione");
			int anno = Integer.parseInt(scan2.nextLine());
			System.out.println("Inserisci numero di pagine");
			int pagine = Integer.parseInt(scan2.nextLine());
			System.out.println("Inserisci periodicita premendo 1 per settimanale, 2 per mensile, 3 per semestrale");
			int numPeriodicita = scan2.nextInt();
			
			Periodicita periodicita = null;
			
			switch(numPeriodicita) {
				case(1):
					periodicita = Periodicita.SETTIMANALE;
					break;
				case(2):
					periodicita = Periodicita.MENSILE;
					break;
				case(3):
					periodicita = Periodicita.SEMESTRALE;
					break;
				default:
					System.out.println("Valore errato");
					break;
			}
			
			Rivista rivista = new Rivista(generatoreISBN(), titolo, anno, pagine, periodicita);
			riviste.add(rivista);
			
			try {
				writeOnFile(fileInfo, rivista.toString() + System.lineSeparator(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("Rivista " + rivista.getTitolo() + " aggiunta");
		}
	}
	
	// Generatore dei codici ISBN univoci
	public static long generatoreISBN() {
		HashSet<Long> isbnUsati = new HashSet<>();
		Random random = new Random();
		long start = 9000000000000L;
		long end = 9999999999999L;
		
        long newIsbn = 0;
        
        do {
        	newIsbn = start + (long)(random.nextDouble()*(end - start));
        } while (isbnUsati.contains(newIsbn));
        
        isbnUsati.add(newIsbn);
        return newIsbn;
	}

	public static void rimuoviElemento() {
		System.out.println("Inserisci codice ISBN dell'elemento da rimuovere:");
		long isbnRimuovi = Long.parseLong(scan1.nextLine());

		try {
			ArrayList<String> catalogo = new ArrayList<>(FileUtils.readLines(fileInfo, ENCODING));
			catalogo.removeIf(line -> line.contains(Long.toString(isbnRimuovi)));
			FileUtils.writeLines(fileInfo, catalogo);
			System.out.println("Elemento eliminato");
		} catch (IOException e) {
			System.out.println("Non è stato possibile eliminare l'elemento, codice non trovato");
			e.printStackTrace();
		}
	}
	
	public static void writeOnFile(File f, String s, boolean append) throws IOException {
        FileUtils.writeStringToFile(f, s, ENCODING, append);
    }
	
	public static void readFromFile(File f) throws IOException {
		System.out.printf("Ecco il contenuto del file %s: %n", f.getName());
		System.out.println(FileUtils.readFileToString(f, ENCODING));
	}
	
	public static void ricercaPerIsbn() throws IOException {
		ArrayList<String> catalogo = new ArrayList<>(FileUtils.readLines(fileInfo, ENCODING));
	    System.out.println("Inserisci il codice ISBN dell'elemento da ricercare:");
	    String isbnRicerca = scan1.nextLine();
	    Predicate<String> isbnRicercato = r -> r.matches(isbnRicerca);
	    System.out.println(catalogo.stream().anyMatch(isbnRicercato));
	}
	
	public static void ricercaPerAnno() throws IOException {
		ArrayList<String> catalogo = new ArrayList<>(FileUtils.readLines(fileInfo, ENCODING));
		System.out.println("Inserisci l'anno di pubblicazione degli elementi da ricercare:");
		int annoRicerca = Integer.parseInt(scan1.nextLine());
		catalogo.stream().filter((e) -> e.contains(Integer.toString(annoRicerca))).forEach((e) -> System.out.println(e));
	}
	
	public static void ricercaPerAutore() throws IOException {
		ArrayList<String> catalogo = new ArrayList<>(FileUtils.readLines(fileInfo, ENCODING));
		System.out.println("Inserisci l'anno di pubblicazione degli elementi da ricercare:");
		String autore = scan1.nextLine();
		catalogo.stream().filter((e) -> e.contains(autore)).forEach((e) -> System.out.println(e));
	}

}
