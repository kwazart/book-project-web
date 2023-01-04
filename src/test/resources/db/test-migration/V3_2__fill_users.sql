INSERT INTO USERS (EMAIL, FIRST_NAME, LAST_NAME, PASSWORD, ROLE, STATUS)
VALUES ('admin@mail.com', 'Admin', 'Adminov', '$2a$12$oddeDBax7EGhFqZyLnPaj.yFGSpnXxQDgKgNALRE2IpHOStfkxl2.', 'ADMIN',
        'ACTIVE'),
       ('user@mail.com', 'User', 'Userov', '$2a$12$dJFSB7H7KRfSwlGKuW4OReaXbcK6dXetjZQlgYoXK/jkMPu5dBPZG', 'USER',
        'BANNED'),
       ('client@mail.com', 'Client', 'Clientod', '$2a$12$EDmreVZjS8Qs0qUkWx5cg.pzBqUpmv3vlnJpX7knPKmJcz6KAyqGW',
        'CLIENT', 'ACTIVE');