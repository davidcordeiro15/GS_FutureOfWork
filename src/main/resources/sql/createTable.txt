-- SEQUENCE para geração de ID
CREATE SEQUENCE usuarios_seq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

-- Tabela de usuários
CREATE TABLE usuariosGS (
    id NUMBER PRIMARY KEY,
    nome VARCHAR2(150) NOT NULL,
    email VARCHAR2(255) NOT NULL UNIQUE,
    senha VARCHAR2(255) NOT NULL,
    esta_trabalhando NUMBER(1) DEFAULT 0 CHECK (esta_trabalhando IN (0, 1))
);

CREATE TABLE usuarios_trilhas (
    id NUMBER PRIMARY KEY,
    usuario_id NUMBER NOT NULL,
    trilha_id NUMBER NOT NULL,
    data_matricula DATE DEFAULT SYSDATE,

    CONSTRAINT fk_usuario_trilha_usuario FOREIGN KEY (usuario_id)
        REFERENCES usuarios (id)
        ON DELETE CASCADE,

    CONSTRAINT fk_usuario_trilha_trilha FOREIGN KEY (trilha_id)
        REFERENCES trilhas (id)
        ON DELETE CASCADE,

    CONSTRAINT unq_usuario_trilha UNIQUE (usuario_id, trilha_id)
);

ALTER TABLE usuarios_trilhas DROP CONSTRAINT fk_usuario_trilha_usuario;

ALTER TABLE usuarios_trilhas ADD CONSTRAINT fk_usuario_trilha_usuario
FOREIGN KEY (usuario_id)
REFERENCES usuariosGS (id)
ON DELETE CASCADE;

-- SEQUENCE para a tabela TRILHAS
CREATE SEQUENCE trilhas_seq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

-- Tabela principal de trilhas
CREATE TABLE trilhas (
    id NUMBER PRIMARY KEY,
    nome VARCHAR2(255) NOT NULL,
    conteudo CLOB,
    videos CLOB,
    quantidade_de_matriculados NUMBER DEFAULT 0
);


-- Trigger para autoincrementar o ID
CREATE OR REPLACE TRIGGER trg_trilhas_ai
BEFORE INSERT ON trilhas
FOR EACH ROW
WHEN (NEW.id IS NULL)
BEGIN
    SELECT trilhas_seq.NEXTVAL INTO :NEW.id FROM dual;
END;