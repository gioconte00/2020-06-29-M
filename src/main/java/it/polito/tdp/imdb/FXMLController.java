/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.DirectorPeso;
import it.polito.tdp.imdb.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaAffini"
    private Button btnCercaAffini; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxRegista"
    private ComboBox<Director> boxRegista; // Value injected by FXMLLoader

    @FXML // fx:id="txtAttoriCondivisi"
    private TextField txtAttoriCondivisi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {

    	this.txtResult.clear();
    	Integer anno = this.boxAnno.getValue();
    	
    	if(anno==null) {
    		this.txtResult.appendText("Seleziona un anno!");
    		return;
    	}
    	else {
    		this.txtResult.appendText(this.model.creaGrafo(anno)+"\n");
    	}
    	
    	this.boxRegista.getItems().clear();
    	this.boxRegista.getItems().addAll(this.model.getVertici());
    	this.btnCercaAffini.setDisable(false);
    	this.btnAdiacenti.setDisable(false);
    }

    @FXML
    void doRegistiAdiacenti(ActionEvent event) {

    	this.txtResult.clear();
    	Director director = this.boxRegista.getValue();
    	
    	if(director==null) {
    		this.txtResult.appendText("Seleziona un regista!");
    		return;
    	}
    	else {
    		this.txtResult.appendText("Registi adiacenti a "+director+" (attori in comune): \n");
    		for(DirectorPeso d : this.model.getAdiacenti(director)) {
    			this.txtResult.appendText(d+"\n");
    		}
    	}
    }

    @FXML
    void doRicorsione(ActionEvent event) {
    	
    	this.txtResult.clear();
    	Director director = this.boxRegista.getValue();
    	
    	try {
    		Integer attoriCondivisi = Integer.parseInt(this.txtAttoriCondivisi.getText());
    		
    		this.model.getCammino(attoriCondivisi, director);
    		
    		this.txtResult.appendText("Cammino trovato:\n");
    		for(Director d : this.model.getRisultato()) {
    			this.txtResult.appendText(d+"\n");
    		}
    		
    	} catch (NumberFormatException e) {
    		this.txtResult.setText("Inserisci un formato valido!");
    		return;
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaAffini != null : "fx:id=\"btnCercaAffini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxRegista != null : "fx:id=\"boxRegista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAttoriCondivisi != null : "fx:id=\"txtAttoriCondivisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

        this.btnCercaAffini.setDisable(true);
        this.btnAdiacenti.setDisable(true);
    }
    
   public void setModel(Model model) {
    	
    	this.model = model;
    	
    	this.boxAnno.getItems().clear();
    	for(int i=2004; i<2007; i++) {
    		this.boxAnno.getItems().add(i);
    	}
    	
    }
    
}
