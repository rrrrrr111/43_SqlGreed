package ru.roman.bim.server.service.data;

import ru.roman.bim.server.service.data.dto.BimItemModel;
import ru.roman.bim.server.service.data.dto.GaeGetListRequest;
import ru.roman.bim.server.service.data.dto.GaeGetListResponse;
import ru.roman.bim.server.service.data.jaxws.*;

/** @author Roman 07.01.13 23:48 */
public class DataProviderAdapter {

    private DataProvider dataProvider = new DataProvider();

    public SaveResponse save(Save request){
        BimItemModel req = request.getArg0();
        Long res = dataProvider.save(req);
        SaveResponse response = new SaveResponse();
        response.setReturn(res);
        return response;
    }

    public GetListResponse getList(GetList request){
        GaeGetListRequest req = request.getArg0();
        GaeGetListResponse res = dataProvider.getList(req);
        GetListResponse response = new GetListResponse();
        response.setReturn(res);
        return response;
    }

    public RenewRatingResponse renewRating(RenewRating request){
        Long id = request.getArg0();
        Integer rating = request.getArg1();
        dataProvider.renewRating(id, rating);;
        RenewRatingResponse response = new RenewRatingResponse();
        return response;
    }
}
