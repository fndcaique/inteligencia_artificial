/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmpl.view;

import appmpl.model.Arquitetura;
import appmpl.model.DataMLP;
import appmpl.model.MLP;
import appmpl.model.Resultado;
import appmpl.util.Grafico;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author fnd
 */
public class TelaPrincipalController implements Initializable {


    @FXML
    private JFXTextField txcamentrada;
    @FXML
    private JFXTextField txcamsaida;
    @FXML
    private JFXTextField txcamoculta;
    @FXML
    private JFXTextField txerro;
    @FXML
    private JFXTextField txmaxepocas;
    @FXML
    private JFXTextField txtaxaaprendizado;
    @FXML
    private Label lbfilename;
    @FXML
    private TableView<String[]> tbvdados;
    @FXML
    private JFXButton bttreinar;
    @FXML
    private JFXButton btteste;
    @FXML
    private JFXCheckBox ckcabecalho;
    @FXML
    private JFXTextField txseparador;

    private DataMLP data;
    @FXML
    private BorderPane bpgrafico;
    private Grafico grafico;
    @FXML
    private JFXComboBox<String> cbfuncao;

    private final double percent_treino = 0.9;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = null;
        grafico = new Grafico();
        bpgrafico.setCenter(grafico);
        cbfuncao.getItems().addAll("Linear", "Logística", "Hiperbólica");
        txcamentrada.setText("0");
        txcamoculta.setText("0");
        txcamsaida.setText("0");
    }

    @FXML
    private void selectFuncaoTransferencia(ActionEvent event) {
    }

    @FXML
    private void clkAbrirArquivo(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("MLP");
        fc.setSelectedExtensionFilter(new ExtensionFilter("arquivos CSV", "*.csv"));
        File f = fc.showOpenDialog(null);
        if (f != null) {
            new Thread(() -> {
                Platform.runLater(() -> {
                    try {
                        tbvdados.getItems().clear();
                        tbvdados.getColumns().clear();
                        data = new DataMLP(f.getPath(), txseparador.getText(), ckcabecalho.isSelected());
                        String header[] = data.getHeader();
                        if (header != null) {
                            for (int i = 0; i < header.length; ++i) {
                                addColTbv(header[i], i);
                            }
                        } else {
                            for (int i = 0; i < data.getColsdata(); ++i) {
                                addColTbv("Col" + (i + 1), i);
                            }
                            //idx = 0;
                        }
                        System.out.println("Dados:");
                        tbvdados.setItems(FXCollections.observableArrayList(data.getDados2()));
                        for (String[] tbvda : tbvdados.getItems()) {
                            for (String str : tbvda) {
                                System.out.print(str + ", ");
                            }
                            System.out.println("");
                        }
                        txcamentrada.setText("" + (data.getColsdata() - 1));
                        txcamsaida.setText("" + data.getClasses().size());
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }).start();

        }
    }

    private void addColTbv(String colname, int i) {
        TableColumn<String[], String> col = new TableColumn<>(colname);
        col.setCellValueFactory(c -> new SimpleStringProperty(c.getValue()[i]));
        tbvdados.getColumns().add(col);
    }

    private void treino() {
        if (data != null) {
            // método DataMLP.getClasses retorna rotulos
            double taxa, limiar;
            int n_oculta, funcao, max_epocas;
            n_oculta = Integer.parseInt(txcamoculta.getText());
            funcao = cbfuncao.getSelectionModel().getSelectedIndex();
            max_epocas = Integer.parseInt(txmaxepocas.getText());
            taxa = Double.parseDouble(txtaxaaprendizado.getText());
            limiar = Double.parseDouble(txerro.getText());

            Arquitetura arq = new Arquitetura(data.getColsdata() - 1,
                    n_oculta,
                    data.getClasses().size(), funcao);

            MLP mlp = new MLP();
            mlp.register(grafico);
            Resultado res;
            int maior, cont = 0;
            //Resultado res = 

            double[][] teste = {{}};
            ArrayList<double[][]> testes = new ArrayList();
            int index;

            double[][] dados = data.getTableData(), treino;
            treino = new double[100][];
            double[] t;
            for (int i = 0; i < treino.length; i++) {
                index = (int) (Math.random() * dados.length);
                t = new double[dados[index].length];
                for (int j = 0; j < dados[index].length; j++) {
                    t[j] = dados[index][j];
                }
                treino[i] = t;
            }

            ArrayList al = mlp.backpropagation(arq, dados, taxa, limiar, data.getClasses(), max_epocas);

            for (int i = 0; i < 4; i++) {
                index = i;//(int)(Math.random()*dados.length);
                teste = new double[dados[index].length - 1][1];
                for (int j = 0; j < teste.length; j++) {
                    teste[j][0] = dados[index][j];
                }
                testes.add(teste);
            }

            //double [][] teste3 = {{1},{0}};
            //double [][] teste4 = {{1},{1}};
            for (double[][] tes : testes) {
                res = mlp.propagation(arq, tes);
                maior = 0;
                System.out.print("TESTE " + cont + " : ");
                for (double[] te : tes) {
                    System.out.print(te[0]);
                }
                System.out.println("");
                for (int i = 0; i < res.i_oculta_saida.length; i++) {
                    System.out.println("NEURÔNIO DE SAIDA " + i + " : " + res.i_oculta_saida[i][0]);
                    if (res.i_oculta_saida[maior][0] < res.i_oculta_saida[i][0]) {
                        maior = i;
                    }
                }
                System.out.println("CLASSE : " + data.getClasses().get(maior));
                cont++;
            }
            System.out.println("");
        }
    }

    @FXML
    private void clkTreinar(ActionEvent event) {

        new Thread(() -> treino()).start();
    }

    @FXML
    private void clkTestar(ActionEvent event) {
    }

}
