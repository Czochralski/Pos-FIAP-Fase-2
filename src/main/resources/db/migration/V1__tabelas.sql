CREATE TABLE tipo_usuarios (
                               id UUID PRIMARY KEY,
                               nome_tipo VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE enderecos (
                           id UUID PRIMARY KEY,
                           rua VARCHAR(150) NOT NULL,
                           numero VARCHAR(10) NOT NULL,
                           bairro VARCHAR(100) NOT NULL,
                           cidade VARCHAR(100) NOT NULL,
                           estado CHAR(2) NOT NULL,
                           cep VARCHAR(9) NOT NULL
);

CREATE TABLE usuarios (
                          id UUID PRIMARY KEY,
                          nome VARCHAR(100) NOT NULL,
                          email VARCHAR(150) NOT NULL UNIQUE,
                          login VARCHAR(50) NOT NULL UNIQUE,
                          senha VARCHAR(255) NOT NULL,
                          data_cadastro TIMESTAMP NOT NULL,
                          data_atualizacao TIMESTAMP NOT NULL,
                          endereco_id UUID NOT NULL UNIQUE,
                          tipo_usuario_id UUID NOT NULL,

                          CONSTRAINT fk_usuario_endereco
                              FOREIGN KEY (endereco_id)
                                  REFERENCES enderecos(id),

                          CONSTRAINT fk_usuario_tipo
                              FOREIGN KEY (tipo_usuario_id)
                                  REFERENCES tipo_usuarios(id)
);

CREATE TABLE restaurantes (
                              id UUID PRIMARY KEY,
                              nome VARCHAR(150) NOT NULL,
                              tipo_cozinha VARCHAR(50) NOT NULL,
                              horario_funcionamento VARCHAR(20) NOT NULL,
                              endereco_id UUID NOT NULL UNIQUE,
                              dono_id UUID NOT NULL,

                              CONSTRAINT fk_restaurante_endereco
                                  FOREIGN KEY (endereco_id)
                                      REFERENCES enderecos(id),

                              CONSTRAINT fk_restaurante_dono
                                  FOREIGN KEY (dono_id)
                                      REFERENCES usuarios(id)
);

CREATE TABLE itens_cardapio (
                                id UUID PRIMARY KEY,
                                nome VARCHAR(150) NOT NULL,
                                descricao VARCHAR(1000) NOT NULL,
                                preco NUMERIC(10, 2) NOT NULL CHECK (preco > 0),
                                apenas_no_restaurante BOOLEAN NOT NULL,
                                foto VARCHAR(500) NOT NULL,
                                restaurante_id UUID NOT NULL,
                                CONSTRAINT fk_item_cardapio_restaurante
                                    FOREIGN KEY (restaurante_id) REFERENCES restaurantes(id)
);


