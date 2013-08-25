package ru.roman.bim.server.service.dataws.dto.word;

import ru.roman.bim.server.service.dataws.dto.AbstractResponse;

/** @author Roman 26.01.13 3:58 */
public class SaveResp extends AbstractResponse {

    private Long id;
    private SaveStatus status;

    public SaveResp() {
    }

    public SaveResp(Long id, SaveStatus status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SaveStatus getStatus() {
        return status;
    }

    public void setStatus(SaveStatus status) {
        this.status = status;
    }
}
