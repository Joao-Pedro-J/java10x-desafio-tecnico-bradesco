package com.jpdev.desafio_bradesco.database.dao;

import com.jpdev.desafio_bradesco.database.model.TB_REPLICACAO_PROCESSO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReplicadorProcessoDAO implements ReplicadorDAO<TB_REPLICACAO_PROCESSO>{

    private final Connection conn;

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM TB_REPLICACAO_PROCESSO;";

    private static final String SQL_SELECT_BT_ID =
            "SELECT * FROM TB_REPLICACAO_PROCESSO WHERE ID = ?;";

    private static final String SQL_INSERT =
            "INSERT INTO TB_REPLICACAO_PROCESSO(PROCESSO, DESCRICAO, HABILITADO) VALUES(?, ?, ?);";

    private static final String SQL_UPDATE =
            "UPDATE TB_REPLICACAO_PROCESSO SET PROCESSO = ?, DESCRICAO = ?, HABILITADO = ? WHERE ID = ?;";

    private static final String SQL_DELETE =
            "DELETE FROM TB_REPLICACAO_PROCESSO WHERE ID = ?;";

    private final PreparedStatement pstSelectAll;
    private final PreparedStatement pstSelectById;
    private final PreparedStatement pstInsert;
    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstDelete;

    public ReplicadorProcessoDAO(Connection conn) throws SQLException {
        this.conn = conn;
        pstSelectAll = conn.prepareStatement(SQL_SELECT_ALL);
        pstSelectById = conn.prepareStatement(SQL_SELECT_BT_ID);
        pstInsert = conn.prepareStatement(SQL_INSERT);
        pstUpdate = conn.prepareStatement(SQL_UPDATE);
        pstDelete = conn.prepareStatement(SQL_DELETE);
    }

    public ArrayList<TB_REPLICACAO_PROCESSO> selectAll() throws SQLException {
        ArrayList<TB_REPLICACAO_PROCESSO> lista = new ArrayList<>();

        try (ResultSet rs = pstSelectAll.executeQuery()){

            while (rs.next()) {
                lista.add(map(rs));
            }
        }

        return lista;
    }

    public TB_REPLICACAO_PROCESSO selectById(Long id) throws SQLException {
        pstSelectById.setLong(1, id);

        try (ResultSet rs = pstSelectById.executeQuery()){
           return (rs.next()) ? map(rs) : null;
        }
    }

    public void insert(TB_REPLICACAO_PROCESSO tb) throws SQLException {
        pstInsert.setString(1, tb.getProcesso());
        pstInsert.setString(2, tb.getDescricao());
        pstInsert.setBoolean(3, tb.getHabilitado());

        pstInsert.executeUpdate();
    }

    public void update(Long id, TB_REPLICACAO_PROCESSO tb) throws SQLException {
        pstUpdate.setString(1, tb.getProcesso());
        pstUpdate.setString(2, tb.getDescricao());
        pstUpdate.setBoolean(3, tb.getHabilitado());
        pstUpdate.setLong(4, id);

        pstUpdate.executeUpdate();
    }

    public void delete(Long id) throws SQLException {
        pstDelete.setLong(1, id);

        pstDelete.executeUpdate();
    }

    private TB_REPLICACAO_PROCESSO map(ResultSet rs) throws SQLException{
        TB_REPLICACAO_PROCESSO tb = new TB_REPLICACAO_PROCESSO();

        tb.setId(rs.getLong("ID"));
        tb.setProcesso(rs.getString("PROCESSO"));
        tb.setDescricao(rs.getString("DESCRICAO"));
        tb.setHabilitado(rs.getBoolean("HABILITADO"));

        return tb;
    }
}
