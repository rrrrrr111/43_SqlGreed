package ru.roman.bim.server.service.data;

import ru.roman.bim.server.service.data.dto.word.GetListRequest;
import ru.roman.bim.server.service.data.dto.word.GetListResp;
import ru.roman.bim.server.service.data.dto.word.RenewRatingRequest;
import ru.roman.bim.server.service.data.dto.word.SaveRequest;
import ru.roman.bim.server.service.data.jaxws.*;

/** @author Roman 07.01.13 23:48 */
public class DataProviderAdapter {

    private DataProvider dataProvider = new DataProvider();

    public SaveResponse save(Save request){
        SaveRequest req = request.getArg0();
        Long res = dataProvider.save(req);
        SaveResponse response = new SaveResponse();
        response.setReturn(res);
        return response;
    }

    public ru.roman.bim.server.service.data.jaxws.GetListResponse getList(GetList request){
        GetListRequest req = request.getArg0();
        GetListResp res = dataProvider.getList(req);
        ru.roman.bim.server.service.data.jaxws.GetListResponse response = new ru.roman.bim.server.service.data.jaxws.GetListResponse();
        response.setReturn(res);
        return response;
    }

    public RenewRatingResponse renewRating(RenewRating request){
        RenewRatingRequest req = request.getArg0();
        dataProvider.renewRating(req);
        RenewRatingResponse response = new RenewRatingResponse();
        return response;
    }
}
