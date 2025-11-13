
INSERT INTO usuariosGS (nome, email, senha, esta_trabalhando) VALUES ('Ana Souza', 'ana.souza@email.com', 'senha123', 1);
INSERT INTO usuariosGS (nome, email, senha, esta_trabalhando) VALUES ('Bruno Lima', 'bruno.lima@email.com', 'bruno456', 0);
INSERT INTO usuariosGS (nome, email, senha, esta_trabalhando) VALUES ('Carla Mendes', 'carla.mendes@email.com', 'carla789', 1);
INSERT INTO usuariosGS (nome, email, senha, esta_trabalhando) VALUES ('Diego Costa', 'diego.costa@email.com', 'diegopass', 1);
INSERT INTO usuariosGS (nome, email, senha, esta_trabalhando) VALUES ('Eduarda Rocha', 'eduarda.rocha@email.com', 'edu@123', 0);
INSERT INTO usuariosGS (nome, email, senha, esta_trabalhando) VALUES ('Felipe Andrade', 'felipe.andrade@email.com', 'felipe456', 1);
INSERT INTO usuariosGS (nome, email, senha, esta_trabalhando) VALUES ('Gabriela Nunes', 'gabriela.nunes@email.com', 'gabriela789', 1);
INSERT INTO usuariosGS (nome, email, senha, esta_trabalhando) VALUES ('Henrique Silva', 'henrique.silva@email.com', 'henrique123', 0);
INSERT INTO usuariosGS (nome, email, senha, esta_trabalhando) VALUES ('Isabela Torres', 'isabela.torres@email.com', 'isabela456', 1);
INSERT INTO usuariosGS (nome, email, senha, esta_trabalhando) VALUES ('João Pereira', 'joao.pereira@email.com', 'joaopass', 0);


INSERT INTO trilhas (nome, conteudo, videos, quantidade_de_matriculados)
VALUES ('Introdução ao Java', 'Fundamentos da linguagem Java, sintaxe e primeiros programas.', 'Base64_Java', 150);

INSERT INTO trilhas (nome, conteudo, videos, quantidade_de_matriculados)
VALUES ('Spring Boot Essencial', 'Aprenda a criar APIs REST com Spring Boot.', 'Base64_Spring', 120);

INSERT INTO trilhas (nome, conteudo, videos, quantidade_de_matriculados)
VALUES ('Banco de Dados com PostgreSQL', 'Modelagem, queries e boas práticas de SQL.', 'Base64_Postgres', 95);

INSERT INTO trilhas (nome, conteudo, videos, quantidade_de_matriculados)
VALUES ('HTML e CSS Moderno', 'Criação de páginas web responsivas.', 'Base64_HTMLCSS', 180);

INSERT INTO trilhas (nome, conteudo, videos, quantidade_de_matriculados)
VALUES ('JavaScript Intermediário', 'Manipulação do DOM e introdução ao ES6.', 'Base64_JS', 210);

INSERT INTO trilhas (nome, conteudo, videos, quantidade_de_matriculados)
VALUES ('React para Iniciantes', 'Componentes, props, estado e hooks.', 'Base64_React', 160);

INSERT INTO trilhas (nome, conteudo, videos, quantidade_de_matriculados)
VALUES ('APIs com Node.js', 'Criação de servidores e integração com bancos.', 'Base64_Node', 130);

INSERT INTO trilhas (nome, conteudo, videos, quantidade_de_matriculados)
VALUES ('Python Básico', 'Introdução à linguagem Python e automação de tarefas.', 'Base64_Python', 175);

INSERT INTO trilhas (nome, conteudo, videos, quantidade_de_matriculados)
VALUES ('Git e Controle de Versão', 'Trabalhando com Git, branches e pull requests.', 'Base64_Git', 200);

INSERT INTO trilhas (nome, conteudo, videos, quantidade_de_matriculados)
VALUES ('DevOps e CI/CD', 'Fundamentos de pipelines, Docker e integração contínua.', 'Base64_DevOps', 140);



INSERT INTO usuarios_trilhas (id, usuario_id, trilha_id, data_matricula)
VALUES (1, 1, 1, SYSDATE);

INSERT INTO usuarios_trilhas (id, usuario_id, trilha_id, data_matricula)
VALUES (2, 2, 2, SYSDATE - 5);

INSERT INTO usuarios_trilhas (id, usuario_id, trilha_id, data_matricula)
VALUES (3, 3, 3, SYSDATE - 10);

INSERT INTO usuarios_trilhas (id, usuario_id, trilha_id, data_matricula)
VALUES (4, 4, 4, SYSDATE - 15);

INSERT INTO usuarios_trilhas (id, usuario_id, trilha_id, data_matricula)
VALUES (5, 5, 5, SYSDATE - 20);

INSERT INTO usuarios_trilhas (id, usuario_id, trilha_id, data_matricula)
VALUES (6, 6, 6, SYSDATE - 25);

INSERT INTO usuarios_trilhas (id, usuario_id, trilha_id, data_matricula)
VALUES (7, 7, 7, SYSDATE - 30);

INSERT INTO usuarios_trilhas (id, usuario_id, trilha_id, data_matricula)
VALUES (8, 8, 8, SYSDATE - 35);

INSERT INTO usuarios_trilhas (id, usuario_id, trilha_id, data_matricula)
VALUES (9, 9, 9, SYSDATE - 40);

INSERT INTO usuarios_trilhas (id, usuario_id, trilha_id, data_matricula)
VALUES (10, 10, 10, SYSDATE - 45);