/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app8puzzle;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

/**
 *
 * @author fndcaique
 */
public class WindowController implements Initializable {

    private String styleN, styleK;
    private Button[][] matrix;
    private int movimentosUser;
    private final int TF = 3, LEFT = 0, RIGHT = 1, DOWN = 2, UP = 3;
    private final int[] dy = {0, 0, 1, -1}, dx = {-1, 1, 0, 0};
    
    // vars de controle/necessidades de threads
    private int px, py, idx, qtdeDesordenados, idx2;
    private final int SLEEP_MOVE = 100;
    private boolean shuffle;

    @FXML
    private GridPane gridPane;
    @FXML
    private Button bt8puzzle;
    @FXML
    private Button btrestore;
    @FXML
    private Label lbmessage;
    @FXML
    private TextField txdigitar;
    @FXML
    private ComboBox<String> cbalgoritmos;
    @FXML
    private Label txInstrucoes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadMatrix();
        movimentosUser = 0;
        styleN = matrix[0][0].getStyle(); // estilo dos buttons numerados
        styleK = matrix[TF - 1][TF - 1].getStyle(); // estilo do button curinga
        
        loadInstrucoes();
    }
    
    private void loadInstrucoes(){
        String txt = "Para jogar basta clicar em alguma peça ao lado da peça curinga\n"
                + "ou  pressionar as teclas direcionais";
        txInstrucoes.setText(txt);
    }

    /**
     * habilita e desabilita componentes que não
     * podem estar ativos durante o embaralhamento
     */
    private void setDisableComponents(boolean v) {
        btrestore.setDisable(v);
        txdigitar.setDisable(v);
        cbalgoritmos.setDisable(v);
    }

    /**
     * pega os buttons do gridpane na ordem em que estão no fxml
     * e inicializa a matrix com eles
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

    private void exibeMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print("[" + i + "," + j + "] = " + matrix[i][j].getText() + ", ");
            }
            System.out.println("");
        }
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
                    isWinner();
                    break;
                }
            }
        }
    }

    private void isWinner() {
        if (isOrdered()) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("8puzzle");
            a.setContentText("Parabéns!!!\nDesafio concluido com " + movimentosUser + " movimentos");
            a.showAndWait();
            movimentosUser = 0;
        }
    }

    /**
     * move o button Curinga somando os valores sy e sx
     * nas sua coordenadas py e px
     */
    private void moveSum(int sy, int sx) {
        swapButtons(matrix[py][px], matrix[py + sy][px + sx]);
        py += sy;
        px += sx;
    }

    /**
     troca o text e o stily dos buttons
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
            shuffle = true;
            setDisableComponents(true);
            new Thread(() -> {
                Platform.runLater(() -> {
                    bt8puzzle.setText("Parar embaralhamento");
                    lbmessage.setText("Embaralhando...");
                });
                shuffle(10, 100, 8, 0.5);
                Platform.runLater(() -> {
                    bt8puzzle.setText("Embaralhar");
                    lbmessage.setText("");
                    setDisableComponents(false);
                });
            }).start();
        } else {
            shuffle = false;
            setDisableComponents(false);
        }
    }

    /**
     * retorna a quantidade de peças desordenadas
     */
    private int desordenados() {
        int qtd = 0;
        for (int i = 0, k = 1; i < TF; ++i) {
            for (int j = 0; j < TF; ++j, ++k) {
                if (!matrix[i][j].getText().trim().isEmpty()
                        && k != Integer.parseInt(matrix[i][j].getText())) {
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
                if (!matrix[i][j].getText().trim().isEmpty()
                        && Integer.parseInt(matrix[i][j].getText()) == x) {
                    ++x;
                } else {
                    f = false;
                }
            }
        }
        return x == 9;
    }

    /**
     * embaralha o tabuleiro
     */
    private void shuffle(int minmove, int maxmove, int mindesord, double probcontinuar) {
        int ant = - 1, moves = 0;
        do {
            shuffling();
            qtdeDesordenados = desordenados();
            System.out.println("desordenadas = " + qtdeDesordenados);
        } while (shuffle && moves < maxmove
                && (++moves < minmove || qtdeDesordenados < mindesord
                || Math.random() <= probcontinuar));
    }

    private void shuffling() {
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
    }

    /**
     * movimenta o button curinga através do teclado
     * 
     */
    private void moveKeyPress(KeyCode code) {
        if (null != code) {
            new Thread(() -> {
                System.out.println("code = " + code.toString());
                switch (code) {
                    case LEFT:
                        idx2 = this.LEFT;
                        break;
                    case DOWN:
                        idx2 = this.DOWN;
                        break;
                    case RIGHT:
                        idx2 = this.RIGHT;
                        break;
                    case UP:
                        idx2 = this.UP;
                        break;
                    default:
                        idx2 = -1;
                        break;
                }
                if (idx2 > -1 && inGrid(dy[idx2] + py, dx[idx2] + px)) {
                    ++movimentosUser;
                    Platform.runLater(() -> {
                        moveSum(dy[idx2], dx[idx2]);
                        isWinner();
                    });
                }

            }).start();
        }
    }
    
    /**
     * é necessário que o txdigitar esteja habilitado
     * para que o evento ocorra
     */
    @FXML
    private void onKeyPressedTxDigitar(KeyEvent event) {
        moveKeyPress(event.getCode());
    }


    /**
     * restaura tabuleiro
     */
    @FXML
    private void clkRestore(ActionEvent event) {
        for (int i = 0, k = 1; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j].setText("" + (k++));
                matrix[i][j].setStyle(styleN);
            }
        }
        matrix[TF - 1][TF - 1].setText("  ");
        matrix[TF - 1][TF - 1].setStyle(styleK);
        px = py = 2;
    }


}
