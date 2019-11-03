import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;

/**
 * 
 * This program is a text analyzer that reads an HTML URL that the user inputs
 * and outputs the word frequencies of all words in the file, sorted by the most
 * frequently used word. The output is displayed in a JavaFX UI table with each
 * pair containing a word and how many times it occurred in the file.
 * 
 * @author Juan Martinez
 *
 */

public class WordFrequencyUI extends Application {

	Stage window;
	Button button;
	TableView<Word> table;
	String url = "";
	public static LinkedHashMap<String, Integer> importedList = new LinkedHashMap<>();

	/**
	 * The main method in which we launch the UI and thus the rest of the processes
	 * go into action.
	 * 
	 * @param args an array of command-line arguments for the application
	 * @throws IOException if there is a problem in the input of the file
	 */

	public static void main(String[] args) throws IOException {
		launch(args);

	}

	/**
	 * Method that is called by the main method to create the UI for the program.
	 * Here we set the elements of the UI including labels, texts, text boxes, and
	 * tables.
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Word Frequencies");

		Label instructionsLabel = new Label("Input the URL of the HTML file you want the word frequencies for.");

		Label urlLabel = new Label("URL:");

		TextField urlInput = new TextField();
		urlInput.setPrefWidth(600);

		button = new Button();
		button.setText("Obtain word frequencies");
		button.setOnAction(e -> {
			try {
				url = urlInput.getText();
				if (url.isEmpty()) {
					AlertBox.display("Invalid URL", "Please input a valid HTML URL");
				} else {
					table.setItems(getWord(url));
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				AlertBox.display("Invalid URL", "Please input a valid HTML URL");
			}
		});

		// Order column
		TableColumn<Word, Integer> orderColumn = new TableColumn<>("Order");
		orderColumn.setMinWidth(200);
		orderColumn.setCellValueFactory(new PropertyValueFactory<>("order"));

		// Word column
		TableColumn<Word, String> wordColumn = new TableColumn<>("Word");
		wordColumn.setMinWidth(300);
		wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));

		// Frequency column
		TableColumn<Word, Integer> frequencyColumn = new TableColumn<>("Frequency");
		frequencyColumn.setMinWidth(200);
		frequencyColumn.setCellValueFactory(new PropertyValueFactory<>("frequency"));

		table = new TableView<>();
		table.getColumns().addAll(orderColumn, wordColumn, frequencyColumn);
		table.prefHeightProperty().bind(window.heightProperty());
		table.prefWidthProperty().bind(window.widthProperty());

		HBox hBox = new HBox();
		hBox.getChildren().addAll(urlLabel, urlInput);
		hBox.setSpacing(20);
		hBox.setAlignment(Pos.CENTER);

		VBox vBox = new VBox();
		vBox.getChildren().addAll(instructionsLabel, hBox, button, table);
		vBox.setPadding(new Insets(10, 50, 50, 50));
		vBox.setSpacing(10);
		vBox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vBox);
		window.setScene(scene);
		window.setHeight(850);
		window.setWidth(815);
		window.show();
	}

	/**
	 * This method gets the list of words from the WordFrequencyFinder class and
	 * adds it to the observable list that will be displayed in the UI.
	 * 
	 * @param urlLink HTML URL inputed by the user which the program will analyze.
	 * @return words Observable list that will be displayed in the UI
	 * @throws IOException if there is a problem with the URL
	 */
	public static ObservableList<Word> getWord(String urlLink) throws IOException {
		ObservableList<Word> words = FXCollections.observableArrayList();
		WordFrequencyFinder finder1 = new WordFrequencyFinder(urlLink);
		importedList = finder1.getWordList();
		int count = 1;
		for (Map.Entry<String, Integer> en : importedList.entrySet()) {
			words.add(new Word(count, en.getKey(), en.getValue()));
			count++;
		}

		return words;
	}

}
