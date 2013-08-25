package ru.roman.bim.server.service.dataws.dto.word;

/** @author Roman 24.08.13 20:02 */
public enum SaveStatus {

    CREATED_NEW,
    ALREADY_EXIST_SKIPPED,
    ALREADY_EXIST_MERGED,
    EDITED,
    EDITED_OLD_MERGED_AND_DELETED,

}
