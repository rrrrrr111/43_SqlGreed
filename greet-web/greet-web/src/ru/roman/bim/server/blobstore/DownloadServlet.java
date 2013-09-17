package ru.roman.bim.server.blobstore;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

/**
 * Created with IntelliJ IDEA.
 * User: churganov
 * Date: 17.09.13
 * Time: 12:37
 * To change this template use File | Settings | File Templates.
 */
public class DownloadServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(DownloadServlet.class.getName());

    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        String blobKeyParam = req.getParameter("blob-key");

        if (blobKeyParam == null) {
            log.warning("blob-key is null, request skipped");
            return;
        }

        log.info("Getting from blobstore blob-key=" + blobKeyParam);
        final BlobKey blobKey = new BlobKey(blobKeyParam);
        blobstoreService.serve(blobKey, res);
    }
}
