package ru.roman.bim.server.service.data.dto.system;

import ru.roman.bim.server.service.data.dto.AbstractResponse;

/** @author Roman 29.01.13 23:04 */
public class SystemTaskResp  extends AbstractResponse {
    private int resultCount;

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public int getResultCount() {
        return resultCount;
    }
}
