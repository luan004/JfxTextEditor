package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

	int fontSize = 10;

	public static void main(String[] args) throws IOException {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		Menu file = new Menu("Arquivo");

		MenuItem FileItem1 = new MenuItem("Nova aba");
		MenuItem FileItem2 = new MenuItem("Salvar");
		MenuItem FileItem3 = new MenuItem("Sair");

		file.getItems().addAll(FileItem1, FileItem2, FileItem3);

		FileItem3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.exit(0);
			}
		});

		Menu config = new Menu("Editar");

		MenuItem ConfigItem1 = new MenuItem("Zoom+");
		MenuItem ConfigItem2 = new MenuItem("Zoom-");

		config.getItems().addAll(ConfigItem1, ConfigItem2);

		Menu help = new Menu("Ajuda");

		MenuItem HelpItem1 = new MenuItem("Sobre");

		help.getItems().addAll(HelpItem1);

		HelpItem1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("Pressionado!");

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Sobre");

				Image image = new Image("/IconCapi32x.png");
				ImageView imageView = new ImageView(image);
				alert.setGraphic(imageView);
				alert.setHeaderText("Capi 1.0");
				String s = "Criado por: Luan Gabriel\nGithub: luan004";
				alert.setContentText(s);
				alert.show();
			}
		});

		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(file, config, help);

		TabPane tabPane = new TabPane();

		KeyCombination novoKey = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
		FileItem1.setAccelerator(novoKey);
		FileItem1.setOnAction(event -> {

			criarAba(tabPane, ConfigItem1, ConfigItem2, FileItem2, stage);

		});

		FileItem1.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent t) {

				criarAba(tabPane, ConfigItem1, ConfigItem2, FileItem2, stage);
			}
		});

		final Tab newtab = new Tab();
		newtab.setText(" + ");
		newtab.setClosable(false);
		tabPane.getTabs().addAll(newtab);

		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldSelectedTab, Tab newSelectedTab) {
				if (newSelectedTab == newtab) {
					criarAba(tabPane, ConfigItem1, ConfigItem2, FileItem2, stage);
				}
			}
		});

		VBox vBox = new VBox(menuBar, tabPane);
		Scene scene = new Scene(vBox, 600, 400);

		criarAba(tabPane, ConfigItem1, ConfigItem2, FileItem2, stage);

		stage.setScene(scene);
		stage.setTitle("Capi");
		stage.getIcons().add(new Image("/IconCapi32x.png"));
		stage.show();

	}

	public void criarAba(TabPane tabPane, MenuItem ConfigItem1, MenuItem ConfigItem2, MenuItem FileItem2, Stage stage) {

		Tab tab = new Tab();
		tab.setText(" Nova Aba ");

		TextArea texto = new TextArea();
		texto.setWrapText(true);
		texto.setPrefSize(1, 10000);
		texto.setStyle("-fx-font-size: 12");

		tab.setContent(texto);

		final ObservableList<Tab> tabs = tabPane.getTabs();
		tab.closableProperty().bind(Bindings.size(tabs).greaterThan(2));
		tabs.add(tabs.size() - 1, tab);
		tabPane.getSelectionModel().select(tab);

		KeyCombination zoomKey = new KeyCodeCombination(KeyCode.PLUS, KeyCombination.CONTROL_DOWN);
		ConfigItem1.setAccelerator(zoomKey);
		ConfigItem1.setOnAction(event -> {

			int i = Integer.parseInt(texto.getStyle().replace("-fx-font-size: ", ""));

			if (i < 100) {
				texto.setStyle("-fx-font-size: " + (i + 2));
				System.out.println("FontSize = " + (i + 2));
			}

		});

		ConfigItem1.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent t) {

				int i = Integer.parseInt(texto.getStyle().replace("-fx-font-size: ", ""));

				if (i < 100) {
					texto.setStyle("-fx-font-size: " + (i + 2));
					System.out.println("FontSize = " + (i + 2));
				}
			}
		});

		KeyCombination zoom2Key = new KeyCodeCombination(KeyCode.MINUS, KeyCombination.CONTROL_DOWN);
		ConfigItem2.setAccelerator(zoom2Key);
		ConfigItem2.setOnAction(event -> {

			int i = Integer.parseInt(texto.getStyle().replace("-fx-font-size: ", ""));

			if (i > 0) {
				texto.setStyle("-fx-font-size: " + (i - 2));
				System.out.println("FontSize = " + (i - 2));
			}

		});

		ConfigItem2.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent t) {

				int i = Integer.parseInt(texto.getStyle().replace("-fx-font-size: ", ""));

				if (i > 0) {
					texto.setStyle("-fx-font-size: " + (i - 2));
					System.out.println("FontSize = " + (i - 2));
				}
			}
		});

		KeyCombination salvarKey = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
		FileItem2.setAccelerator(salvarKey);
		FileItem2.setOnAction(event -> {

			System.out.println("Salvando...");

			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

			File localSelecionado = fileChooser.showSaveDialog(stage);
			System.out.println("Local: " + localSelecionado);

			try {
				OutputStream os = new FileOutputStream(localSelecionado);
				Writer wr = new OutputStreamWriter(os);
				BufferedWriter br = new BufferedWriter(wr);
				br.write(texto.getText());
				br.close();
			}

			catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
			}

		});

		FileItem2.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent t) {

				System.out.println("Salvando...");

				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

				File localSelecionado = fileChooser.showSaveDialog(stage);
				System.out.println("Local: " + localSelecionado);

				try {
					OutputStream os = new FileOutputStream(localSelecionado);
					Writer wr = new OutputStreamWriter(os);
					BufferedWriter br = new BufferedWriter(wr);
					br.write(texto.getText());
					br.close();
				}

				catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
				}
			}
		});

	}
}