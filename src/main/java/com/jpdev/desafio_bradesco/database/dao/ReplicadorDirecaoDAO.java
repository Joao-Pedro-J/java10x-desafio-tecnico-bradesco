package com.jpdev.desafio_bradesco.database.dao;

import com.jpdev.desafio_bradesco.database.model.TB_REPLICACAO_DIRECAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReplicadorDirecaoDAO implements ReplicadorDAO<TB_REPLICACAO_DIRECAO> {

    private final Connection conn;

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM TB_REPLICACAO_DIRECAO;";

    private static final String SQL_SELECT_BT_ID =
            "SELECT * FROM TB_REPLICACAO_DIRECAO WHERE ID = ?;";

    private static final String SQL_INSERT =
            "INSERT INTO TB_REPLICACAO_DIRECAO(DIRECAO_ORIGEM, DIRECAO_DESTINO, USUARIO_ORIGEM, USUARIO_DESTINO, SENHA_ORIGEM, SENHA_DESTINO, HABILITADO, PROCESSO_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String SQL_UPDATE =
            "UPDATE TB_REPLICACAO_DIRECAO SET DIRECAO_ORIGEM = ?, DIRECAO_DESTINO = ?, USUARIO_ORIGEM = ?, USUARIO_DESTINO = ?, SENHA_ORIGEM = ?, SENHA_DESTINO = ?, HABILITADO = ?, PROCESSO_ID = ? WHERE ID = ?;";

    private static final String SQL_DELETE =
            "DELETE FROM TB_REPLICACAO_DIRECAO WHERE ID = ?;";

    private final PreparedStatement pstSelectAll;
    private final PreparedStatement pstSelectById;
    private final PreparedStatement pstInsert;
    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstDelete;

    public ReplicadorDirecaoDAO(Connection conn) throws SQLException {
        this.conn = conn;
        pstSelectAll = conn.prepareStatement(SQL_SELECT_ALL);
        pstSelectById = conn.prepareStatement(SQL_SELECT_BT_ID);
        pstInsert = conn.prepareStatement(SQL_INSERT);
        pstUpdate = conn.prepareStatement(SQL_UPDATE);
        pstDelete = conn.prepareStatement(SQL_DELETE);
    }

    @Override
    public ArrayList<TB_REPLICACAO_DIRECAO> selectAll() throws SQLException {
        ArrayList<TB_REPLICACAO_DIRECAO> lista = new ArrayList<>();

        try (ResultSet rs = pstSelectAll.executeQuery()){

            while (rs.next()) {
                lista.add(map(rs));
            }
        }

        return lista;
    }

    @Override
    public TB_REPLICACAO_DIRECAO selectById(Long id) throws SQLException {
        pstSelectById.setLong(1, id);

        try (ResultSet rs = pstSelectById.executeQuery()){
              return (rs.next()) ? map(rs) : null;
        }
    }

    @Override
    public void insert(TB_REPLICACAO_DIRECAO tb) throws SQLException {
        pstInsert.setString(1, tb.getDirecao_origem());
        pstInsert.setString(2, tb.getDirecao_destino());
        pstInsert.setString(3, tb.getUsuario_origem());
        pstInsert.setString(4, tb.getUsuario_destino());
        pstInsert.setString(5, tb.getSenha_origem());
        pstInsert.setString(6, tb.getSenha_destino());
        pstInsert.setBoolean(7, tb.getHabilitado());
        pstInsert.setLong(8, tb.getProcesso_id());

        pstInsert.executeUpdate();
    }

    @Override
    public void update(Long id, TB_REPLICACAO_DIRECAO tb) throws SQLException {
        pstUpdate.setString(1, tb.getDirecao_origem());
        pstUpdate.setString(2, tb.getDirecao_destino());
        pstUpdate.setString(3, tb.getUsuario_origem());
        pstUpdate.setString(4, tb.getUsuario_destino());
        pstUpdate.setString(5, tb.getSenha_origem());
        pstUpdate.setString(6, tb.getSenha_destino());
        pstUpdate.setBoolean(7, tb.getHabilitado());
        pstUpdate.setLong(8, tb.getProcesso_id());
        pstUpdate.setLong(9, id);

        pstUpdate.executeUpdate();
    }

    @Override
    public void delete(Long id) throws SQLException {
        pstDelete.setLong(1, id);

        pstDelete.executeUpdate();
    }

    private TB_REPLICACAO_DIRECAO map(ResultSet rs) throws SQLException {
        TB_REPLICACAO_DIRECAO tb = new TB_REPLICACAO_DIRECAO();

        tb.setId(rs.getLong("ID"));
        tb.setDirecao_origem(rs.getString("DIRECAO_ORIGEM"));
        tb.setDirecao_destino(rs.getString("DIRECAO_DESTINO"));
        tb.setUsuario_origem(rs.getString("USUARIO_ORIGEM"));
        tb.setUsuario_destino(rs.getString("USUARIO_DESTINO"));
        tb.setSenha_origem(rs.getString("SENHA_ORIGEM"));
        tb.setSenha_destino(rs.getString("SENHA_DESTINO"));
        tb.setHabilitado(rs.getBoolean("HABILITADO"));
        tb.setProcesso_id(rs.getLong("PROCESSO_ID"));

        return tb;
    }
}
