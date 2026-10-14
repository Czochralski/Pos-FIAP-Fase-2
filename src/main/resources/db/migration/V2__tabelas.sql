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