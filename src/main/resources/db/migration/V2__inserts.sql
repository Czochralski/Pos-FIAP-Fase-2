/* Insert de tipos de usuários, enderecos e usuários já predefinidos*/

INSERT INTO tipo_usuarios (id, nome_tipo)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'CLIENTE'),
    ('22222222-2222-2222-2222-222222222222', 'DONO');

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
    ),
    (
        'cccccccc-cccc-cccc-cccc-cccccccccccc',
        'Rua do Dono2',
        '202',
        'Boa Viagem',
        'Recife',
        'PE',
        '51000-222'
    );

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
    ),
    (
        '55555555-5555-5555-5555-555555555555',
        'Usuario Dono2',
        'dono2@email.com',
        'dono2',
        '123456',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'cccccccc-cccc-cccc-cccc-cccccccccccc',
        '22222222-2222-2222-2222-222222222222'
    );