package ru.roman.bim.gui.pane.main;

import ru.roman.bim.gui.common.Model;
import ru.roman.bim.service.gae.wsclient.BimItemModel;
import ru.roman.bim.service.gae.wsclient.BimItemType;

import javax.xml.datatype.XMLGregorianCalendar;

/** @author Roman 19.12.12 23:36 */
public class MainViewModel extends BimItemModel implements Model {



    public MainViewModel() {
        super();
    }

    public MainViewModel(Long id, String textFaced, String textShadowed,
                         Long facedLangId, Long shadowedLangId, Long rating,
                         BimItemType type, Long modelNum, Long owner, XMLGregorianCalendar editDate) {
        this.id = id;
        this.textFaced = textFaced;
        this.textShadowed = textShadowed;
        this.facedLangId = facedLangId;
        this.shadowedLangId = shadowedLangId;
        this.rating = rating;
        this.type = type;
        this.modelNum = modelNum;
        this.owner = owner;
        this.editDate = editDate;
    }

    public MainViewModel(BimItemModel model) {
        this(
                model.getId(),
                model.getTextFaced(),
                model.getTextShadowed(),
                model.getFacedLangId(),
                model.getShadowedLangId() ,
                model.getRating(),
                model.getType(),
                model.getModelNum(),
                model.getOwner(),
                model.getEditDate()
        );
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MainViewModel)) return false;
        MainViewModel that = (MainViewModel) o;
        if (id != null) {
            return id.equals(that.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


}
