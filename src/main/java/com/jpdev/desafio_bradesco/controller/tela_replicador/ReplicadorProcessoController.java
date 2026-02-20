package com.jpdev.desafio_bradesco.controller.tela_replicador;

import com.jpdev.desafio_bradesco.controller.dialogo_buscar.DialogoBuscarReplicadorProcessoController;
import com.jpdev.desafio_bradesco.database.DbConnection;
import com.jpdev.desafio_bradesco.database.dao.ReplicadorProcessoDAO;
import com.jpdev.desafio_bradesco.database.model.TB_REPLICACAO_PROCESSO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ReplicadorProcessoController {

    private Connection conn;
    private ReplicadorProcessoDAO dao;

    private enum TIPO_ACAO {NENHUMA, INSERT, UPDATE}
    private TIPO_ACAO tipoAcaoAtual = TIPO_ACAO.NENHUMA;

    @FXML private Button botao_adicionar;
    @FXML private Button botao_salvar;
    @FXML private Button botao_buscar;
    @FXML private Button botao_excluir;

    @FXML private Button botao_limpar_processo;
    @FXML private Button botao_limpar_descricao;

    @FXML private TextField txf_id;
    @FXML private TextField txf_processo;
    @FXML private TextField txf_descricao;
    @FXML private CheckBox chbx_habilitado;

    @FXML public void initialize() {
        try {
            conn = DbConnection.getConnection();
            dao = new ReplicadorProcessoDAO(conn);

            txf_id.setDisable(true);
            txf_processo.setDisable(true);
            txf_descricao.setDisable(true);
            chbx_habilitado.setDisable(true);

            botao_limpar_processo.setDisable(true);
            botao_limpar_descricao.setDisable(true);

            botao_salvar.setDisable(true);
            botao_excluir.setDisable(true);
        } catch (SQLException ex) {
            ex.printStackTrace();

            mostrarAlertaErro(
                    "Conexão com o banco de dados falhou",
                    "Erro ao conectar com o banco de dados",
                    "Não foi possível estabelecer uma conexão com o banco de dados. Verifique as configurações e tente novamente.\n\nErro: " + ex.getMessage()
            );
        }
    }

    @FXML private void aoClicarBotaoAdicionar() {
        tipoAcaoAtual = TIPO_ACAO.INSERT;

        txf_processo.setDisable(false);
        txf_descricao.setDisable(false);
        chbx_habilitado.setDisable(false);

        botao_limpar_processo.setDisable(false);
        botao_limpar_descricao.setDisable(false);

        botao_salvar.setDisable(false);
        botao_excluir.setDisable(true);

        txf_id.setText("");
        txf_processo.setText("");
        txf_descricao.setText("");
        chbx_habilitado.setSelected(true);
    }

    @FXML private void aoClicarBotaoSalvar() {
        try {
            if (txf_processo.getText().trim().isEmpty()) {
                mostrarAlertaErro(
                        "Erro ao salvar processo",
                        "Campo 'Processo' vazio",
                        "O campo 'Processo' é obrigatório e não pode estar vazio."
                );
                return;
            }

            TB_REPLICACAO_PROCESSO tb = new TB_REPLICACAO_PROCESSO();
            tb.setProcesso(txf_processo.getText().trim());
            tb.setDescricao(txf_descricao.getText().trim());
            tb.setHabilitado(chbx_habilitado.isSelected());

            if (tipoAcaoAtual.equals(TIPO_ACAO.INSERT)) {
                dao.insert(tb);

                mostrarAlertaSucesso(
                        "Processo adicionado com sucesso!",
                        "O processo '" + tb.getProcesso() + "' foi adicionado."
                );

            } else if (tipoAcaoAtual.equals(TIPO_ACAO.UPDATE)) {
                Long id = Long.parseLong(txf_id.getText());
                dao.update(id, tb);

                mostrarAlertaSucesso(
                        "Processo atualizado com sucesso!",
                        "O processo com ID " + id + " foi atualizado."
                );

            }

            tipoAcaoAtual = TIPO_ACAO.NENHUMA;

            txf_processo.setDisable(true);
            txf_descricao.setDisable(true);
            chbx_habilitado.setDisable(true);
            botao_limpar_processo.setDisable(true);
            botao_limpar_descricao.setDisable(true);
            botao_salvar.setDisable(true);
            botao_excluir.setDisable(true);

            txf_id.setText("");
            txf_processo.setText("");
            txf_descricao.setText("");
            chbx_habilitado.setSelected(true);

        } catch (Exception ex) {
            ex.printStackTrace();

            mostrarAlertaErro(
                    "Erro ao salvar processo",
                    "Ocorreu um erro ao salvar o processo",
                    "Erro: " + ex.getMessage()
            );
        }
    }

    @FXML private void aoClicarBotaoBuscar() {
        criarJanelaDialogoBuscar();

        tipoAcaoAtual = TIPO_ACAO.UPDATE;
    }

    @FXML private void aoClicarBotaoExcluir() {
        new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja excluir o processo com ID " + txf_id.getText() + "?", ButtonType.YES, ButtonType.NO).showAndWait()
                .filter(response -> response == ButtonType.YES)
                .ifPresent(response -> {
                    try {
                        long id = Long.parseLong(txf_id.getText());

                        if (dao.delete(id)) {

                            tipoAcaoAtual = TIPO_ACAO.NENHUMA;

                            txf_processo.setDisable(true);
                            txf_descricao.setDisable(true);
                            chbx_habilitado.setDisable(true);

                            botao_limpar_processo.setDisable(true);
                            botao_limpar_descricao.setDisable(true);

                            botao_salvar.setDisable(true);
                            botao_excluir.setDisable(true);


                            txf_id.setText("");
                            txf_processo.setText("");
                            txf_descricao.setText("");
                            chbx_habilitado.setSelected(true);

                            mostrarAlertaSucesso(
                                "Processo excluído com sucesso!",
                                "O processo com ID " + id + " foi excluído."
                            );
                        }

                        else {
                            mostrarAlertaErro(
                                "Erro ao excluir processo",
                                "Nenhum processo encontrado com ID " + id + ".",
                                "Verifique se o ID está correto e tente novamente."
                            );
                        }

                    } catch (SQLException sqle) {
                        mostrarAlertaErro(
                                "Erro ao excluir processo",
                                "Ocorreu um erro ao tentar excluir o processo.",
                                "Erro: " + sqle.getMessage()
                        );
                    }
                });
    }

    @FXML private void criarJanelaDialogoBuscar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dialogo-buscar/DialogoBuscarReplicadorProcessoView.fxml"));
            Parent root = loader.load();

            DialogoBuscarReplicadorProcessoController dialogoController = loader.getController();
            dialogoController.setMainController(this);

            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.setTitle("Buscar Processo");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao Abrir Diálogo");
            alert.setHeaderText("Não foi possível abrir a janela de busca");
            alert.setContentText("Erro: " + ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML private void aoClicarBotaoLimpar(ActionEvent event) {
        Button botaoClicado = (Button) event.getSource();

        switch (botaoClicado.getId()) {
            case "botao_limpar_processo" -> txf_processo.setText("");
            case "botao_limpar_descricao" -> txf_descricao.setText("");
        }

    }

    public void receberProcessoSelecionado(TB_REPLICACAO_PROCESSO processo) {
            txf_id.setText(processo.getId().toString());
            txf_processo.setText(processo.getProcesso());
            txf_descricao.setText(processo.getDescricao());
            chbx_habilitado.setSelected(processo.getHabilitado());

            txf_processo.setDisable(false);
            txf_descricao.setDisable(false);
            chbx_habilitado.setDisable(false);

            botao_limpar_processo.setDisable(false);
            botao_limpar_descricao.setDisable(false);

            botao_salvar.setDisable(false);
            botao_excluir.setDisable(false);
    }

    private void mostrarAlertaSucesso(String header, String conteudo) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(header);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }

    private void mostrarAlertaErro(String titulo, String header, String conteudo) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }
}
