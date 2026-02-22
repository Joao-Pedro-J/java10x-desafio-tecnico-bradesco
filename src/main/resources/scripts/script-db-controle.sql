CREATE TABLE tb_replicacao_processo(
	id BIGSERIAL PRIMARY KEY,
	processo VARCHAR(100) NOT NULL,
	descricao VARCHAR(300),
	habilitado BOOLEAN DEFAULT TRUE
);

CREATE TABLE tb_replicacao_processo_tabela(
	id BIGSERIAL PRIMARY KEY,
	processo_id BIGINT NOT NULL,
	tabela_origem VARCHAR(150) NOT NULL,
	tabela_destino VARCHAR(150) NOT NULL,
	ordem INTEGER NOT NULL,
	ativo BOOLEAN DEFAULT TRUE,
	ds_where VARCHAR(500) NOT NULL,

	CONSTRAINT tb_replicacao_processo_tabela_fk
	FOREIGN KEY (processo_id)
	REFERENCES tb_replicacao_processo(id)
);

CREATE TABLE tb_replicacao_direcao(
	id BIGSERIAL PRIMARY KEY,
	direcao_origem VARCHAR(150) NOT NULL,
	direcao_destino VARCHAR(150) NOT NULL,
	usuario_origem VARCHAR(50) NOT NULL,
	usuario_destino VARCHAR(50) NOT NULL,
	senha_origem VARCHAR(50) NOT NULL,
	senha_destino VARCHAR(50) NOT NULL,
	habilitado BOOLEAN DEFAULT TRUE,
	processo_id BIGINT NOT NULL,

	CONSTRAINT tb_replicacao_direcao_fk
	FOREIGN KEY (processo_id)
	REFERENCES tb_replicacao_processo(id)
);
