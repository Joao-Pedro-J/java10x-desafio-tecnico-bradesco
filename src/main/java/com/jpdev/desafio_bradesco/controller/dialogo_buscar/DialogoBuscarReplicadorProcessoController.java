package com.jpdev.desafio_bradesco.controller.dialogo_buscar;

import com.jpdev.desafio_bradesco.controller.tela_replicador.ReplicadorProcessoController;
import com.jpdev.desafio_bradesco.database.DbConnection;
import com.jpdev.desafio_bradesco.database.dao.ReplicadorProcessoDAO;
import com.jpdev.desafio_bradesco.database.model.TB_REPLICACAO_PROCESSO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DialogoBuscarReplicadorProcessoController {
    private ReplicadorProcessoController mainController;

    @FXML private TableView<TB_REPLICACAO_PROCESSO> tabelaProcessos;
    @FXML private TableColumn<TB_REPLICACAO_PROCESSO, Long> colunaId;
    @FXML private TableColumn<TB_REPLICACAO_PROCESSO, String> colunaProcesso;
    @FXML private TableColumn<TB_REPLICACAO_PROCESSO, String> colunaDescricao;
    @FXML private TableColumn<TB_REPLICACAO_PROCESSO, Boolean> colunaHabilitado;

    @FXML private Button botaoSelecionar;
    @FXML private Button botaoCancelar;

    @FXML public void initialize() throws SQLException {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaProcesso.setCellValueFactory(new PropertyValueFactory<>("processo"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaHabilitado.setCellValueFactory(new PropertyValueFactory<>("habilitado"));

        recuperarProcessos();
    }

    @FXML public void aoClicarBotaoSelecionar() {
        TB_REPLICACAO_PROCESSO processoSelecionado = tabelaProcessos.getSelectionModel().getSelectedItem();

        if (processoSelecionado != null && mainController != null) {
            mainController.receberProcessoSelecionado(processoSelecionado);

            Stage stage = (Stage) tabelaProcessos.getScene().getWindow();
            stage.close();
        }
    }

    @FXML private void aoClicarBotaoCancelar() {
        Stage stage = (Stage) tabelaProcessos.getScene().getWindow();
        stage.close();
    }

    private void recuperarProcessos() {
        try {
            Connection conn = DbConnection.getConnection();
            ReplicadorProcessoDAO dao = new ReplicadorProcessoDAO(conn);

            List<TB_REPLICACAO_PROCESSO> processos = dao.selectAll();

            ObservableList<TB_REPLICACAO_PROCESSO> observableProcessos = FXCollections.observableArrayList(processos);

            tabelaProcessos.setItems(observableProcessos);

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Conexão");
            alert.setHeaderText("Não foi possível conectar ao banco de dados");
            alert.setContentText("Verifique a conexão e tente novamente.");
            alert.showAndWait();
        }
    }

    public void setMainController(ReplicadorProcessoController mainController) {
        this.mainController = mainController;
    }
}
