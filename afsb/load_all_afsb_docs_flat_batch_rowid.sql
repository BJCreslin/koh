CREATE OR REPLACE PROCEDURE load_all_afsb_docs_flat_batch(
    p_batch_size IN NUMBER DEFAULT 10000
)
AS
    v_offset      NUMBER := 0;
    v_total_count NUMBER;
BEGIN
    SELECT COUNT(*)
      INTO v_total_count
      FROM dm_dct_data_ds_elem d;

    WHILE v_offset < v_total_count LOOP

        INSERT INTO AFSB_DOCS_FLAT (
            ID, AFSB_ID, BANK_CODE, LOAD_DATE, EDIT_DATE, LOAD_USERNAME,
            EDIT_USERNAME, DOCUMENT_STATUS, IS_VISIBLE, IS_EXPORTED, IS_BG_MEMBER,
            FILE_NAME, DOCUMENT_NAME, DOCUMENT_TYPE, DOCUMENT_NUMBER, DOCUMENT_DATE,
            KO_SECTION, KO_SECTIONS, KO_SECTION_TEXT, BG_SECTION, BG_SECTIONS,
            BG_SECTION_TEXT, DOCUMENT_NOTE, PM_ADRESS, BG
        )
        WITH base_tbl AS (
            SELECT
                d.id,
                d.id_afsb,
                d.newnum,
                d.date_load,
                d.date_izm,
                d.user_load,
                d.state,
                d.show,
                d.exp_enabled
            FROM dm_dct_data_ds_elem d
            ORDER BY d.ROWID
            OFFSET v_offset ROWS FETCH NEXT p_batch_size ROWS ONLY
        ),
        attr_id_tbl AS (
            SELECT 1 AS atr_id FROM dual UNION ALL
            SELECT 2 AS atr_id FROM dual UNION ALL
            SELECT 3 AS atr_id FROM dual UNION ALL
            SELECT 5 AS atr_id FROM dual UNION ALL
            SELECT 6 AS atr_id FROM dual UNION ALL
            SELECT 7 AS atr_id FROM dual UNION ALL
            SELECT 8 AS atr_id FROM dual UNION ALL
            SELECT 1001 AS atr_id FROM dual UNION ALL
            SELECT 1002 AS atr_id FROM dual UNION ALL
            SELECT 1003 AS atr_id FROM dual UNION ALL
            SELECT 1005 AS atr_id FROM dual UNION ALL
            SELECT 1006 AS atr_id FROM dual UNION ALL
            SELECT 1007 AS atr_id FROM dual UNION ALL
            SELECT 1008 AS atr_id FROM dual UNION ALL
            SELECT 1009 AS atr_id FROM dual
        ),
        attr_latest AS (
            SELECT
                de.id,
                ded.atr_id,
                ded.value_str,
                ded.value_date,
                ded.full_str,
                ded.user_izm,
                ded.date_izm
            FROM base_tbl de
            JOIN (
                SELECT
                    ded.id,
                    ded.atr_id,
                    ded.value_str,
                    ded.value_date,
                    ded.full_str,
                    ded.user_izm,
                    ded.date_izm,
                    ROW_NUMBER() OVER (
                        PARTITION BY ded.id, ded.atr_id
                        ORDER BY ded.date_izm DESC NULLS LAST
                    ) AS rn
                FROM dm_dct_data_ds_elem_data ded
                JOIN attr_id_tbl ai
                  ON ai.atr_id = ded.atr_id
            ) ded
              ON ded.id = de.id
             AND ded.rn = 1
        ),
        doc_attr AS (
            SELECT
                al.id,
                MAX(CASE WHEN al.atr_id IN (3, 1003) THEN al.value_str END) AS document_name,
                MAX(CASE WHEN al.atr_id IN (6, 1006) THEN al.full_str END) AS document_type,
                MAX(CASE WHEN al.atr_id IN (1, 1001) THEN al.value_str END) AS document_number,
                MAX(CASE WHEN al.atr_id IN (2, 1002) THEN al.value_date END) AS document_date,
                MAX(CASE WHEN al.atr_id = 7 THEN al.value_str END) AS ko_section,
                MAX(CASE WHEN al.atr_id = 7 THEN al.full_str END) AS ko_sections,
                MAX(CASE WHEN al.atr_id = 1007 THEN al.value_str END) AS bg_section,
                MAX(CASE WHEN al.atr_id = 1007 THEN al.full_str END) AS bg_sections,
                MAX(CASE WHEN al.atr_id IN (5, 1005) THEN al.value_str END) AS document_note,
                MAX(CASE WHEN al.atr_id IN (8, 1008) THEN al.value_str END) AS pm_address,
                MAX(CASE WHEN al.atr_id = 1009 THEN al.value_str END) AS bg,
                MAX(al.user_izm) KEEP (DENSE_RANK LAST ORDER BY al.date_izm NULLS LAST) AS edit_username,
                MAX(f.f_name) KEEP (DENSE_RANK LAST ORDER BY al.date_izm NULLS LAST) AS file_name
            FROM attr_latest al
            JOIN dm_dct_data_ds_files f
              ON f.id = al.id
            GROUP BY al.id
        ),
        base_docs AS (
            SELECT
                d.id,
                d.id_afsb AS afsb_id,
                d.newnum AS bank_code,
                d.date_load AS load_date,
                d.date_izm AS edit_date,
                d.user_load AS load_username,
                da.edit_username,
                d.state AS document_status,
                d.show AS is_visible,
                d.exp_enabled AS is_exported,
                CASE WHEN da.bg IS NOT NULL THEN 1 ELSE 0 END AS is_bg_member,
                da.file_name,
                da.document_name,
                da.document_type,
                da.document_number,
                da.document_date,
                da.ko_section,
                da.ko_sections,
                da.bg_section,
                da.bg_sections,
                da.document_note,
                da.pm_address,
                da.bg
            FROM base_tbl d
            JOIN doc_attr da
              ON da.id = d.id
        ),
        struct_paths AS (
            SELECT
                s.id AS token,
                REGEXP_REPLACE(SYS_CONNECT_BY_PATH(s.text, '/'), '^/[^/]+/?', '') AS path_text
            FROM dm_dct_data_mf_struct s
            START WITH s.relate = -1
            CONNECT BY NOCYCLE PRIOR s.id = s.relate
        ),
        section_tokens AS (
            SELECT
                b.id,
                'KO' AS section_kind,
                TO_NUMBER(TRIM(COLUMN_VALUE)) AS token
            FROM base_docs b,
                 XMLTABLE(
                     ('"' || REPLACE(b.ko_section, ',', '","') || '"')
                     COLUMNS COLUMN_VALUE VARCHAR2(4000) PATH 'text()'
                 )
            WHERE b.ko_section IS NOT NULL

            UNION ALL

            SELECT
                b.id,
                'BG' AS section_kind,
                TO_NUMBER(TRIM(COLUMN_VALUE)) AS token
            FROM base_docs b,
                 XMLTABLE(
                     ('"' || REPLACE(b.bg_section, ',', '","') || '"')
                     COLUMNS COLUMN_VALUE VARCHAR2(4000) PATH 'text()'
                 )
            WHERE b.bg_section IS NOT NULL
        ),
        section_agg AS (
            SELECT
                st.id,
                st.section_kind,
                LISTAGG(sp.path_text, ', ') WITHIN GROUP (ORDER BY st.token) AS section_text
            FROM section_tokens st
            JOIN struct_paths sp
              ON sp.token = st.token
            WHERE sp.path_text IS NOT NULL
              AND sp.path_text <> ''
            GROUP BY st.id, st.section_kind
        )
        SELECT
            b.id,
            b.afsb_id,
            b.bank_code,
            b.load_date,
            b.edit_date,
            b.load_username,
            b.edit_username,
            b.document_status,
            b.is_visible,
            b.is_exported,
            b.is_bg_member,
            b.file_name,
            b.document_name,
            b.document_type,
            b.document_number,
            b.document_date,
            b.ko_section,
            b.ko_sections,
            ko.section_text,
            b.bg_section,
            b.bg_sections,
            bg.section_text,
            b.document_note,
            b.pm_address,
            b.bg
        FROM base_docs b
        LEFT JOIN section_agg ko
          ON ko.id = b.id
         AND ko.section_kind = 'KO'
        LEFT JOIN section_agg bg
          ON bg.id = b.id
         AND bg.section_kind = 'BG';

        v_offset := v_offset + p_batch_size;
        COMMIT;
    END LOOP;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END;
/
