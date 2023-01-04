INSERT INTO acl_sid (id, principal, sid)
VALUES (1, false, 'ADMIN'),
       (2, false, 'USER'),
       (3, false, 'CLIENT');

INSERT INTO acl_class (id, class)
VALUES (1, 'com.polozov.bookprojectweb.domain.Book');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
VALUES (1, 1, '1', NULL, 3, false),
       (2, 1, '2', NULL, 3, false),
       (3, 1, '3', NULL, 3, false);

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask,
                       granting, audit_success, audit_failure)
VALUES (1, 1, 1, 1, 1, true, false, false),
       (2, 1, 2, 1, 2, true, false, false),
       (3, 1, 3, 2, 1, true, false, false),
       (4, 2, 1, 2, 1, true, false, false),
       (5, 2, 2, 3, 1, true, false, false),
       (6, 3, 1, 3, 1, true, false, false),
       (7, 3, 2, 3, 2, true, false, false);