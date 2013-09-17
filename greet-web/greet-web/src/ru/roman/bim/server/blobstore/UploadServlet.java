package ru.roman.bim.server.blobstore;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

/**
 * Created with IntelliJ IDEA.
 * User: churganov
 * Date: 17.09.13
 * Time: 12:38
 * To change this template use File | Settings | File Templates.
 */
public class UploadServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(DownloadServlet.class.getName());

    public static final String BLOBSTORE_UPLOAD_JSP = "/jsp/blobstoreUpload.jsp";
    public static final String BLOB_INFOS_ATTR = "blobInfos";
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        List<BlobInfo> blobInfos = null;
        blobInfos = (List<BlobInfo>) req.getSession().getAttribute(BLOB_INFOS_ATTR);
        if (blobInfos == null) {
            blobInfos = new LinkedList<BlobInfo>();
            req.getSession().setAttribute(BLOB_INFOS_ATTR, blobInfos);
        }
        blobInfos.clear();

        final Map<String, List<BlobInfo>> blobs = blobstoreService.getBlobInfos(req);
        if (blobs.isEmpty()) {
            res.sendRedirect(BLOBSTORE_UPLOAD_JSP);
            return;
        }

        for (Map.Entry<String, List<BlobInfo>> entry : blobs.entrySet()) {
            if (entry.getValue().isEmpty()) {
                res.sendRedirect(BLOBSTORE_UPLOAD_JSP);
                return;
            }
            for (BlobInfo blobInfo : entry.getValue()) {
                log.info("Stored to blobstore : " + blobInfo);
                blobInfos.add(blobInfo);
            }
        }
        res.sendRedirect(BLOBSTORE_UPLOAD_JSP);
        return;
    }
}
