package com.api.GS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GsApplication.class, args);
	}

}
/*
 * -> exigir na hora de alteração de dados email e senha ao invés do id
 * -> exigir email e e senha admin para as trilhas
 * -> Criar nova coluna se é admin no banco
 * -> mecanismo para se cadastrar em uma nova trilha
 * -> resolver erro da table nao encontrada
 * -> fazer readme
  -> testar todos os endpoints

 * */
/*ALTER TABLE usuarios_trilhas DROP CONSTRAINT fk_usuario_trilha_usuario;

ALTER TABLE usuarios_trilhas ADD CONSTRAINT fk_usuario_trilha_usuario
FOREIGN KEY (usuario_id)
REFERENCES usuariosGS (id)
ON DELETE CASCADE;*/