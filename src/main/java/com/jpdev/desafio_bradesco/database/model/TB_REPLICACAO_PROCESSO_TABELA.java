package com.jpdev.desafio_bradesco.database.model;

public class TB_REPLICACAO_PROCESSO_TABELA {
    private Long id;
    private Long processo_id;
    private String tabela_origem;
    private String tabela_destino;
    private Integer ordem;
    private Boolean ativo;
    private String ds_where;

    public TB_REPLICACAO_PROCESSO_TABELA() {
    }

    public TB_REPLICACAO_PROCESSO_TABELA(Long processo_id, String tabela_origem, String tabela_destino, Integer ordem, Boolean ativo, String ds_where) {
        this.processo_id = processo_id;
        this.tabela_origem = tabela_origem;
        this.tabela_destino = tabela_destino;
        this.ordem = ordem;
        this.ativo = ativo;
        this.ds_where = ds_where;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProcessoId() {
        return processo_id;
    }

    public void setProcessoId(Long processo_id) {
        this.processo_id = processo_id;
    }

    public String getTabelaOrigem() {
        return tabela_origem;
    }

    public void setTabelaOrigem(String tabela_origem) {
        this.tabela_origem = tabela_origem;
    }

    public String getTabelaDestino() {
        return tabela_destino;
    }

    public void setTabelaDestino(String tabela_destino) {
        this.tabela_destino = tabela_destino;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getDsWhere() {
        return ds_where;
    }

    public void setDsWhere(String ds_where) {
        this.ds_where = ds_where;
    }

    @Override
    public String toString() {
        return "TB_REPLICAO_PROCESSO_TABELA{" +
                "id=" + id +
                ", processo_id=" + processo_id +
                ", tabela_origem='" + tabela_origem + '\'' +
                ", tabela_destino='" + tabela_destino + '\'' +
                ", ordem=" + ordem +
                ", ativo=" + ativo +
                ", ds_where='" + ds_where + '\'' +
                '}';
    }
}
