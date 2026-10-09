-- ============================================================
-- TIPOS DE USUÁRIO
-- ============================================================

CREATE TABLE tipo_usuarios (
                               id UUID PRIMARY KEY,
                               nome_tipo VARCHAR(50) NOT NULL UNIQUE
);


-- ============================================================
-- ENDEREÇOS
-- Mantida porque usuarios.endereco_id possui FK para esta tabela
-- ============================================================

CREATE TABLE enderecos (
                           id UUID PRIMARY KEY,
                           rua VARCHAR(150) NOT NULL,
                           numero VARCHAR(10) NOT NULL,
                           bairro VARCHAR(100) NOT NULL,
                           cidade VARCHAR(100) NOT NULL,
                           estado CHAR(2) NOT NULL,
                           cep VARCHAR(9) NOT NULL
);


-- ============================================================
-- USUÁRIOS
-- Tabela de apoio para demonstrar a associação
-- usuario -> tipo_usuario
-- ============================================================

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


-- ============================================================
-- TIPOS DE USUÁRIO
-- ============================================================

INSERT INTO tipo_usuarios (id, nome_tipo)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'CLIENTE'),
    ('22222222-2222-2222-2222-222222222222', 'DONO');


-- ============================================================
-- ENDEREÇOS DOS USUÁRIOS
-- ============================================================

INSERT INTO enderecos (
    id,
    rua,
    numero,
    bairro,
    cidade,
    estado,
    cep
)
VALUES
    (
        'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
        'Rua do Cliente',
        '100',
        'Centro',
        'Recife',
        'PE',
        '50000-000'
    ),
    (
        'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
        'Rua do Dono',
        '200',
        'Boa Viagem',
        'Recife',
        'PE',
        '51000-000'
    );


-- ============================================================
-- USUÁRIOS
-- ============================================================

INSERT INTO usuarios (
    id,
    nome,
    email,
    login,
    senha,
    data_cadastro,
    data_atualizacao,
    endereco_id,
    tipo_usuario_id
)
VALUES
    (
        '33333333-3333-3333-3333-333333333333',
        'Usuario Cliente',
        'cliente@email.com',
        'cliente',
        '123456',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
        '11111111-1111-1111-1111-111111111111'
    ),
    (
        '44444444-4444-4444-4444-444444444444',
        'Usuario Dono',
        'dono@email.com',
        'dono',
        '123456',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
        '22222222-2222-2222-2222-222222222222'
    );