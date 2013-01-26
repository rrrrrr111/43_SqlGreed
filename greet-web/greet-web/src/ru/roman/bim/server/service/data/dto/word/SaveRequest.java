package ru.roman.bim.server.service.data.dto.word;

import ru.roman.bim.server.service.data.dto.AbstractRequest;

/** @author Roman 26.01.13 3:58 */
public class SaveRequest extends AbstractRequest {

    private BimItemModel model;

    public BimItemModel getModel() {
        return model;
    }

    public void setModel(BimItemModel model) {
        this.model = model;
    }
}
