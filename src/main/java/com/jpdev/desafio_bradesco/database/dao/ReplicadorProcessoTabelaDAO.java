package com.jpdev.desafio_bradesco.database.dao;

import com.jpdev.desafio_bradesco.database.model.TB_REPLICACAO_PROCESSO_TABELA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReplicadorProcessoTabelaDAO implements ReplicadorDAO<TB_REPLICACAO_PROCESSO_TABELA> {

    private final Connection conn;

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM TB_REPLICACAO_PROCESSO_TABELA;";

    private static final String SQL_SELECT_BT_ID =
            "SELECT * FROM TB_REPLICACAO_PROCESSO_TABELA WHERE ID = ?;";

    private static final String SQL_INSERT =
            "INSERT INTO TB_REPLICACAO_PROCESSO_TABELA(PROCESSO_ID, TABELA_ORIGEM, TABELA_DESTINO, ORDEM, ATIVO, DS_WHERE) VALUES(?, ?, ?, ?, ?, ?);";

    private static final String SQL_UPDATE =
            "UPDATE TB_REPLICACAO_PROCESSO_TABELA SET PROCESSO_ID = ?, TABELA_ORIGEM = ?, TABELA_DESTINO = ?, ORDEM = ?, ATIVO = ?, DS_WHERE = ? WHERE ID = ?;";

    private static final String SQL_DELETE =
            "DELETE FROM TB_REPLICACAO_PROCESSO_TABELA WHERE ID = ?;";

    private final PreparedStatement pstSelectAll;
    private final PreparedStatement pstSelectById;
    private final PreparedStatement pstInsert;
    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstDelete;

    public ReplicadorProcessoTabelaDAO(Connection conn) throws SQLException {
        this.conn = conn;
        pstSelectAll = conn.prepareStatement(SQL_SELECT_ALL);
        pstSelectById = conn.prepareStatement(SQL_SELECT_BT_ID);
        pstInsert = conn.prepareStatement(SQL_INSERT);
        pstUpdate = conn.prepareStatement(SQL_UPDATE);
        pstDelete = conn.prepareStatement(SQL_DELETE);
    }

    @Override
    public ArrayList<TB_REPLICACAO_PROCESSO_TABELA> selectAll() throws SQLException {
        ArrayList<TB_REPLICACAO_PROCESSO_TABELA> lista = new ArrayList<>();

        try (ResultSet rs = pstSelectAll.executeQuery()){

            while (rs.next()) {
                lista.add(map(rs));
            }
        }

        return lista;
    }

    @Override
    public TB_REPLICACAO_PROCESSO_TABELA selectById(Long id) throws SQLException {
        pstSelectById.setLong(1, id);

        try (ResultSet rs = pstSelectById.executeQuery()){
            return rs.next() ? map(rs) : null;
        }
    }

    @Override
    public void insert(TB_REPLICACAO_PROCESSO_TABELA tb) throws SQLException {
        pstInsert.setLong(1, tb.getProcessoId());
        pstInsert.setString(2, tb.getTabelaOrigem());
        pstInsert.setString(3, tb.getTabelaDestino());
        pstInsert.setInt(4, tb.getOrdem());
        pstInsert.setBoolean(5, tb.getAtivo());
        pstInsert.setString(6, tb.getDsWhere());

        pstInsert.executeUpdate();
    }

    @Override
    public void update(Long id, TB_REPLICACAO_PROCESSO_TABELA tb) throws SQLException {
        pstUpdate.setLong(1, tb.getProcessoId());
        pstUpdate.setString(2, tb.getTabelaOrigem());
        pstUpdate.setString(3, tb.getTabelaDestino());
        pstUpdate.setInt(4, tb.getOrdem());
        pstUpdate.setBoolean(5, tb.getAtivo());
        pstUpdate.setString(6, tb.getDsWhere());
        pstUpdate.setLong(7, id);
    }

    @Override
    public void delete(Long id) throws SQLException {
        pstDelete.setLong(1, id);

        pstDelete.executeUpdate();
    }

    private TB_REPLICACAO_PROCESSO_TABELA map(ResultSet rs) throws SQLException {
        TB_REPLICACAO_PROCESSO_TABELA tb = new TB_REPLICACAO_PROCESSO_TABELA();

        tb.setId(rs.getLong("ID"));
        tb.setProcessoId(rs.getLong("PROCESSO_ID"));
        tb.setTabelaOrigem(rs.getString("TABELA_ORIGEM"));
        tb.setTabelaDestino(rs.getString("TABELA_DESTINO"));
        tb.setOrdem(rs.getInt("ORDEM"));
        tb.setAtivo(rs.getBoolean("ATIVO"));
        tb.setDsWhere(rs.getString("DS_WHERE"));

        return tb;
    }
}
