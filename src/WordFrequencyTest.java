import java.io.*;
import java.net.URL;
import java.util.*;

import org.jsoup.Jsoup;

/**
 * 
 * This program is a text analyzer that reads a text file and outputs the word
 * frequencies of all words in the file, sorted by the most frequently used
 * word. The output is a set of pairs, each pair containing a word and how many
 * times it occurred in the file.
 * 
 * @author Juan Martinez
 *
 */

public class WordFrequencyTest {

	/**
	 * This method extracts the text from a locally saved HTML file or from an URL
	 * based HTML file and returns the text from the file without any HTML tags.
	 * 
	 * @param file name and direction of the file or URL
	 * @return extracted text of the file
	 * @throws IOException if there is a problem in the input of the file.
	 */

	public static String extractText(String file) throws IOException {
		/**
		 * If you want to read from a file directly instead of a URL uncomment the
		 * FileReader line and comment out the URL line. Also switch the BufferedReader
		 * line for the one that is commented out and uncomment the br.close() line.
		 **/
		// FileReader reader = new FileReader(file);
		URL reader = new URL(file);
		StringBuilder sb = new StringBuilder();
		// BufferedReader br = new BufferedReader(reader);
		BufferedReader br = new BufferedReader(new InputStreamReader(reader.openStream()));
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		String text = Jsoup.parse(sb.toString()).text();
		// br.close();
		return text;
	}

	/**
	 * This method receives a String and a Map collection and gets the words of the
	 * String and puts them in the collection word per word with the value of 1. If
	 * a word that is obtained from the document is already in the collection, then
	 * we sum 1 to the value of that word.
	 * 
	 * @param str   String whose words are counted and put in the collection.
	 * @param words collection
	 * @throws FileNotFoundException if the text file is not found.
	 */

	public static void wordFrequency(String str, Map<String, Integer> words) throws FileNotFoundException {

		String arr[] = str.replaceAll("[^a-zA-Z '-]", "").toUpperCase().split("\\s+");
		for (String s : arr) {
			int count = 0;
			if (words.containsKey(s)) {
				count = words.get(s);
			}
			words.put(s, count + 1);
		}

	}

	/**
	 * This method sorts the values in the collection from lowest to highest entry
	 * by comparing each value and then putting them in a new collection.
	 * 
	 * @param words collection
	 * @return newList collection that will be passed into a variable in the main
	 *         method.
	 */

	public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> words) {
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(words.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		HashMap<String, Integer> newList = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> aa : list) {
			newList.put(aa.getKey(), aa.getValue());
		}
		return newList;
	}

	/**
	 * Main method in which we create the first collection and specify the document
	 * or URL where the text will be that is going to be analyzed. We call all three
	 * methods and create reverseSortedMap which is a LinkedHashMap that is filled
	 * with the documents words but now with the highest values displayed first.
	 * 
	 * @param args an array of command-line arguments for the application.
	 * @throws IOException if there is a problem in the input of the file.
	 */

	public static void main(String[] args) throws IOException {
		HashMap<String, Integer> words = new HashMap<String, Integer>();
		String document = "http://shakespeare.mit.edu/macbeth/full.html";

		String play = WordFrequencyTest.extractText(document);

		wordFrequency(play, words);

		Map<String, Integer> sortedList = sortByValue(words);

		LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
		sortedList.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

		// Print to text document
		int count = 1;
		PrintStream o = new PrintStream(new File("Output.txt"));
		PrintStream console = System.out;
		System.setOut(o);
		for (Map.Entry<String, Integer> en : reverseSortedMap.entrySet()) {
			System.out.println(count + ". (" + en.getKey() + ", " + en.getValue() + ")");
			count++;
		}

		// Print to console
		count = 1;
		System.setOut(console);
		for (Map.Entry<String, Integer> en : reverseSortedMap.entrySet()) {
			System.out.println(count + ". (" + en.getKey() + ", " + en.getValue() + ")");
			count++;
		}
	}

}
