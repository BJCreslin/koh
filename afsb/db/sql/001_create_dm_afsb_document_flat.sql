CREATE TABLE dm_afsb_document_flat (
    id               NUMBER(19)            NOT NULL,
    afsb_id          NUMBER(19),
    bank_code        VARCHAR2(32 CHAR),
    load_date        DATE,
    edit_date        DATE,
    load_username    VARCHAR2(256 CHAR),
    edit_username    VARCHAR2(256 CHAR),
    document_status  VARCHAR2(128 CHAR),
    is_visible       NUMBER(1),
    is_exported      NUMBER(1),
    is_bg_member     NUMBER(1),
    file_name        VARCHAR2(1024 CHAR),
    document_name    VARCHAR2(2000 CHAR),
    document_type    VARCHAR2(1000 CHAR),
    document_number  VARCHAR2(512 CHAR),
    document_date    DATE,
    ko_section       VARCHAR2(2000 CHAR),
    ko_sections      VARCHAR2(4000 CHAR),
    ko_section_text  VARCHAR2(4000 CHAR),
    bg_section       VARCHAR2(2000 CHAR),
    bg_sections      VARCHAR2(4000 CHAR),
    bg_section_text  VARCHAR2(4000 CHAR),
    document_note    VARCHAR2(4000 CHAR),
    pm_address       VARCHAR2(1000 CHAR),
    bg               VARCHAR2(1000 CHAR),
    CONSTRAINT pk_dm_afsb_document_flat PRIMARY KEY (id)
);

CREATE INDEX ix_dm_afsb_document_flat_doc_date
    ON dm_afsb_document_flat (document_date);

CREATE INDEX ix_dm_afsb_document_flat_afsb_id
    ON dm_afsb_document_flat (afsb_id);

CREATE INDEX ix_dm_afsb_document_flat_bank_code
    ON dm_afsb_document_flat (bank_code);

CREATE INDEX ix_dm_afsb_document_flat_bg_member
    ON dm_afsb_document_flat (is_bg_member);
