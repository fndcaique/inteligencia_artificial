/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app8puzzle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 *
 * @author fndcaique
 */
public class WindowController implements Initializable {

    private ArrayList<Pair<Integer, Integer>> passos;
    private Tabuleiro tabuleiro;
    private String styleN, styleK, tempo, movimentos, iteracoes, profundidade;
    private Button[][] matrix;
    private int movimentosUser, idxpass;
    private final int TF = 3, LEFT = 0, RIGHT = 1, DOWN = 2, UP = 3;
    private final int[] dy = {0, 0, 1, -1}, dx = {-1, 1, 0, 0};
    private final int[][] end = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

    // vars de controle/necessidades de threads
    private int px, py, idx, idx2;
    private final int SLEEP_MOVE = 100;
    private boolean shuffle;

    @FXML
    private GridPane gridPane;
    @FXML
    private Button bt8puzzle;
    @FXML
    private Button btrestore;
    @FXML
    private ComboBox<String> cbalgoritmos;
    @FXML
    private Button btfindsolution;
    @FXML
    private Button btnextpass;
    @FXML
    private Button btstopfindsolution;
    @FXML
    private Label txiteracoes;
    @FXML
    private Label txtempo;
    @FXML
    private Label txmovimentos;
    @FXML
    private Label txmsg;
    @FXML
    private BorderPane borderpane;
    @FXML
    private TextField txDigit;
    @FXML
    private CheckBox ckcuringarandom;
    @FXML
    private Label txdepth;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadMatrix();
        movimentosUser = 0;
        styleN = matrix[0][0].getStyle(); // estilo dos buttons numerados
        styleK = matrix[TF - 1][TF - 1].getStyle(); // estilo do button curinga

        loadAlgoritmos();
        habilitarComponents("all");
        inicializaTabuleiro();
    }

    /**
     * habilita e desabilita componentes que não podem estar ativos durante o
     * embaralhamento
     */
    private void habilitarComponents(String h) {
        bt8puzzle.setDisable(h.matches("find"));
        btrestore.setDisable(h.matches("shuffle|find"));
        txDigit.setDisable(h.matches("shuffle|find"));
        cbalgoritmos.setDisable(h.matches("shuffle|find"));
        btfindsolution.setDisable(h.matches("shuffle|find")
                || cbalgoritmos.getSelectionModel().getSelectedIndex() <= 0);
        btnextpass.setDisable(!h.matches("solution"));
        btstopfindsolution.setDisable(!h.matches("find"));

        shuffle = h.matches("shuffle");

        txiteracoes.setText("Iterações: ");
        txmovimentos.setText("Movimentos: ");
        txtempo.setText("Tempo: ");
        if (!h.matches("solution|nsolution")) {
            txmsg.setText("Mensagem...");
        }
        txDigit.setDisable(false);
        txDigit.requestFocus();
    }

    /**
     * pega os buttons do gridpane na ordem em que estão no fxml e inicializa a
     * matrix com eles
     */
    private void loadMatrix() {
        Object[] arr = gridPane.getChildren().toArray();
        matrix = new Button[TF][TF];
        for (int i = 0, k = 0; i < TF; ++i) {
            for (int j = 0; j < TF; ++j) {
                matrix[i][j] = (Button) arr[k++];
                matrix[i][j].setOnAction(clkButton(i, j));
            }
        }
        py = px = 2;
    }

    /**
     * evento para mover os buttons quando forem clicados
     */
    private EventHandler<ActionEvent> clkButton(int i, int j) {
        return (ActionEvent event) -> {
            if (!shuffle) {
                mover(i, j);
            }
        };
    }

    /**
     * verifica se a coordenada passada é valida
     */
    private boolean inGrid(int l, int c) {
        return l >= 0 && l < TF && c >= 0 && c < TF;
    }

    /**
     * move o button clicado pelo usuário se for permitido
     */
    private void mover(int lin, int col) {
        if (!matrix[lin][col].getText().isEmpty()) {
            for (int k = 0; k < 4; ++k) {
                if (py + dy[k] == lin && px + dx[k] == col) {
                    swapButtons(matrix[lin][col], matrix[py][px]);
                    py = lin;
                    px = col;
                    ++movimentosUser;
                    Winner();
                    break;
                }
            }
        }
    }

    private boolean Winner() {
        if (isOrdered()) {
            exibeInformacao("Parabéns!!!\nDesafio concluido com " + movimentosUser + " movimentos");
            movimentosUser = 0;
            return true;
        }
        return false;
    }

    /**
     * move o button Curinga somando os valores sy e sx nas sua coordenadas py e
     * px
     */
    private void moveSum(int sy, int sx) {
        swapButtons(matrix[py][px], matrix[py + sy][px + sx]);
        py += sy;
        px += sx;
    }

    /**
     * troca o text e o stily dos buttons
     */
    private void swapButtons(Button a, Button b) {
        String stla = a.getStyle();
        String stlb = b.getStyle();
        String str = a.getText();
        a.setText(b.getText());
        b.setText(str);
        a.setStyle(stlb);
        b.setStyle(stla);
    }

    @FXML
    private void clk8Puzzle(ActionEvent event) {
        if (bt8puzzle.getText().equalsIgnoreCase("embaralhar")) {
            movimentosUser = 0;
            habilitarComponents("shuffle");
            new Thread(() -> {
                Platform.runLater(() -> {
                    bt8puzzle.setText("Parar embaralhamento");
                });
                shuffle(15, 100, 3, 0.5);
                Platform.runLater(() -> {
                    bt8puzzle.setText("Embaralhar");
                    habilitarComponents("all");
                });
            }).start();
        } else {
            habilitarComponents("all");
        }
    }

    /**
     * retorna a quantidade de peças desordenadas
     */
    private int desordenados() {
        int qtd = 0;
        for (int i = 0, k = 1; i < TF; ++i) {
            for (int j = 0; j < TF; ++j, ++k) {
                if (k != Integer.parseInt(matrix[i][j].getText())) {
                    ++qtd;
                }
            }
        }
        return qtd;
    }

    private boolean isOrdered() {
        boolean f = true;
        int x = 1;
        for (int i = 0; f && i < TF; i++) {
            for (int j = 0; f && j < TF; j++) {
                if (Integer.parseInt(matrix[i][j].getText()) == x) {
                    ++x;
                } else {
                    f = false;
                }
            }
        }
        return f;
    }

    /**
     * embaralha o tabuleiro
     */
    private void shuffle(int minmove, int maxmove, int mindesord, double probcontinuar) {
        int ant = - 1, moves = 0;
        do {
            if (Math.random() >= 0.5 && inGrid(dy[RIGHT] + py, dx[RIGHT] + px)) {
                idx = RIGHT;
            } else if (inGrid(dy[LEFT] + py, dx[LEFT] + px)) {
                idx = LEFT;
            } else {
                idx = RIGHT;
            }
            Platform.runLater(() -> {
                moveSum(dy[idx], dx[idx]);
            });
            try {
                Thread.sleep(SLEEP_MOVE);
            } catch (InterruptedException ex) {
            }
            if (Math.random() >= 0.5 && inGrid(dy[idx] + py, dx[idx] + px)) {
                Platform.runLater(() -> {
                    moveSum(dy[idx], dx[idx]);

                });
                try {
                    Thread.sleep(SLEEP_MOVE);
                } catch (InterruptedException ex) {
                }
            }
            if (Math.random() >= 0.5 && inGrid(dy[UP] + py, dx[UP] + px)) {
                idx2 = UP;
            } else if (inGrid(dy[DOWN] + py, dx[DOWN] + px)) {
                idx2 = DOWN;
            } else {
                idx2 = UP;
            }

            Platform.runLater(() -> {
                moveSum(dy[idx2], dx[idx2]);

            });
            try {
                Thread.sleep(SLEEP_MOVE);
            } catch (InterruptedException ex) {
            }
            if (Math.random() >= 0.5 && inGrid(dy[idx] + py, dx[idx] + px)) {
                Platform.runLater(() -> {
                    moveSum(dy[idx], dx[idx]);

                });
                try {
                    Thread.sleep(SLEEP_MOVE);
                } catch (InterruptedException ex) {
                }
            }
        } while (shuffle && moves < maxmove
                && (++moves < minmove || desordenados() < mindesord
                || Math.random() <= probcontinuar));
    }

    /**
     * movimenta o button curinga através do teclado
     *
     */
    private void moveKeyPress(KeyCode code) {
        if (null != code) {
            new Thread(() -> {
                switch (code) {
                    case LEFT:
                    case A:
                        idx2 = this.LEFT;
                        break;
                    case DOWN:
                    case S:
                        idx2 = this.DOWN;
                        break;
                    case RIGHT:
                    case D:
                        idx2 = this.RIGHT;
                        break;
                    case UP:
                    case W:
                        idx2 = this.UP;
                        break;
                    default:
                        idx2 = -1;
                        break;
                }
                if (idx2 > -1 && inGrid(dy[idx2] + py, dx[idx2] + px)) {
                    ++movimentosUser;
                    Platform.runLater(() -> {
                        habilitarComponents("all");
                        moveSum(dy[idx2], dx[idx2]);
                        Winner();
                    });
                }

            }).start();
        }
    }

    private void inicializaTabuleiro() {
        for (int i = 0, k = 1; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j].setText("" + (k++));
                matrix[i][j].setStyle(styleN);
            }
        }
        if (ckcuringarandom.isSelected()) {
            sortearPosicaoCuringa();
        } else {
            setPosicaoCuringa(2, 2);
        }
    }

    private void sortearPosicaoCuringa() {
        px = ((int) (Math.random() * 10)) % 3;
        py = ((int) (Math.random() * 10)) % 3;
        matrix[py][px].setStyle(styleK);
    }

    /**
     * restaura tabuleiro
     */
    @FXML
    private void clkRestore(ActionEvent event) {
        inicializaTabuleiro();
    }

    private void exibeInformacao(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText("8puzzle");
        a.setContentText(msg);
        a.showAndWait();
    }

    private void loadAlgoritmos() {
        cbalgoritmos.getItems().setAll("Selecione 1 Algoritmo",
                "Força Bruta: BFS",
                "Heurística: A* - Qtde Fora",
                "Heurística: A* - Dist. Manhattan",
                "Heurística: A* - Dist. Manhattan + Qtde Fora");
    }

    @FXML
    private void clkFindSolution(ActionEvent event) {

        String select = cbalgoritmos.getSelectionModel().getSelectedItem();

        new Thread(() -> {
            Platform.runLater(() -> {
                habilitarComponents("find");
            });
            Platform.runLater(() -> txmsg.setText("Buscando Solução..."));

            int[][] begin = new int[3][3];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    begin[i][j] = Integer.parseInt(matrix[i][j].getText());
                }
            }
            tabuleiro = new Tabuleiro(begin, end);
            Nodo no;
            //Alert a = new Alert(Alert.AlertType.INFORMATION);
            long ini, fim;
            if (select.contains("BFS")) {
                ini = System.currentTimeMillis();
                no = tabuleiro.solveBFS(px, py);
                fim = System.currentTimeMillis();

            } else if (select.endsWith("A* - Dist. Manhattan")) {
                ini = System.currentTimeMillis();
                no = tabuleiro.solveA_DistManhattan(px, py);
                fim = System.currentTimeMillis();

            } else if (select.endsWith("A* - Qtde Fora")) {
                ini = System.currentTimeMillis();
                no = tabuleiro.solveA_QtdeFora(px, py);
                fim = System.currentTimeMillis();

            } else { // manhattan + qtde fora
                ini = System.currentTimeMillis();
                no = tabuleiro.solveA_Manhattan_QtdeFora(px, py);
                fim = System.currentTimeMillis();
            }
            tempo = "";
            movimentos = "";
            iteracoes = "";
            profundidade = "";

            if (Utils.arrayToString(no.getMatrix()).equals(Utils.arrayToString(end))) {
                passos = no.getCaminho();
                movimentos = (passos.size() - 1) + "";
                Platform.runLater(() -> {
                    txmsg.setText("Solução ENCONTRADA!!!");
                    habilitarComponents("solution");
                });
            } else {
                Platform.runLater(() -> {
                    txmsg.setText("Solução NÃO encontrada!!!");
                    habilitarComponents("nsolution");
                });
                movimentos = "-";

            }

            profundidade = no.getProfundidade() + "";
            tempo = (fim - ini) + "";
            iteracoes = tabuleiro.getIteracoes() + "";
            movimentosUser = idxpass = 0;
            Platform.runLater(() -> {
                txiteracoes.setText("Iterações: " + iteracoes);
                txmovimentos.setText("Movimentos: " + movimentos);
                txtempo.setText("Tempo: " + tempo + " milisegundos");
                txdepth.setText("Profundidade: " + profundidade);
            });
        }).start();

    }

    @FXML
    private void clkNextPass(ActionEvent event) {
        ++movimentosUser;
        if (idxpass + 1 < passos.size()) {
            swapButtons(matrix[passos.get(idxpass).getValue()][passos.get(idxpass).getKey()],
                    matrix[py = passos.get(idxpass + 1).getValue()][px = passos.get(idxpass + 1).getKey()]);
            ++idxpass;
            if (idxpass + 1 == passos.size()) {
                btnextpass.setDisable(Winner());
            }
        }
    }

    @FXML
    private void selectAlgoritmo(ActionEvent event) {
        btfindsolution.setDisable(cbalgoritmos.getSelectionModel().getSelectedIndex() <= 0);
        txDigit.requestFocus();
    }

    @FXML
    private void clkStopFindSolution(ActionEvent event) {
        tabuleiro.stopSolve();
    }

    @FXML
    private void onKeyPressed(KeyEvent event) {
        if (!shuffle) {
            if (event.getCode() == KeyCode.ENTER && !btnextpass.isDisable()) {
                clkNextPass(null);
            } else {
                moveKeyPress(event.getCode());
            }

        }
    }

    @FXML
    private void clkCase1(ActionEvent event) {
        /*setValues("713269548");
        setPosicaoCuringa(2, 1);*/
        setValues("243715986");
        setPosicaoCuringa(0, 2);
        ckcuringarandom.setSelected(false);
    }

    @FXML
    private void clkCase2(ActionEvent event) {
        /*setValues("423691758");
        setPosicaoCuringa(1, 1);
         */
        setValues("492156378");
        setPosicaoCuringa(2, 0);
        ckcuringarandom.setSelected(false);
    }

    @FXML
    private void clkCase3(ActionEvent event) {
        /*setValues("237548961");
        setPosicaoCuringa(0, 2);
         */
        setValues("589463712");
        setPosicaoCuringa(0, 2);
        ckcuringarandom.setSelected(false);
    }

    private void setValues(String v) {
        for (int i = 0, k = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j].setText(v.charAt(k++) + "");
                matrix[i][j].setStyle(styleN);
            }
        }
    }

    private void setPosicaoCuringa(int x, int y) {
        px = x;
        py = y;
        matrix[py][px].setStyle(styleK);
    }

}
