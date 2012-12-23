package ru.roman.bim.service.gae;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.roman.bim.gui.pane.main.MainViewModel;
import ru.roman.bim.service.gae.dto.GaeGetListRequest;
import ru.roman.bim.service.gae.dto.GaeGetListResponse;

/** @author Roman 22.12.12 15:36 */
public class GaeConnectorImpl implements GaeConnector {
    private static final Log log = LogFactory.getLog(GaeConnectorImpl.class);

    @Override
    public void save(MainViewModel model) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public GaeGetListResponse getList(GaeGetListRequest request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void renewRating(Long id, Integer rating) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
