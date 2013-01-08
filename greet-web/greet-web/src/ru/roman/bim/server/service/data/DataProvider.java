package ru.roman.bim.server.service.data;

import ru.roman.bim.server.dao.WordDao;
import ru.roman.bim.server.service.data.dto.BimItemModel;
import ru.roman.bim.server.service.data.dto.GaeGetListRequest;
import ru.roman.bim.server.service.data.dto.GaeGetListResponse;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author Roman 07.01.13 17:22 */
@WebService
public class DataProvider {
    private final Logger log = Logger.getLogger(this.getClass().getName());

    @WebMethod
    public Long save(BimItemModel model){
        try {
            log.log(Level.INFO, "saving item");
            return WordDao.createOrUpdate(model);
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "", e);
            throw e;
        }
    }

    @WebMethod
    public GaeGetListResponse getList(GaeGetListRequest req){
        try {
            log.log(Level.INFO, "getting list");
            return WordDao.getWords(req);
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "", e);
            throw e;
        }
    }

    @WebMethod
    public void renewRating(Long id, Integer rating){
        try {
            log.log(Level.INFO, "renew rating");
            WordDao.renewRating(id, rating);
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "", e);
            throw e;
        }
    }


}
