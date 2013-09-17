<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>blobstore upload page</title>
</head>
<body>
    <br/><br/><br/>
    <form name="files" action="<%= blobstoreService.createUploadUrl("/blobstore/upload") %>" method="post" enctype="multipart/form-data">
        <input type="file" name="uploadFile1"><br/>
        <input type="file" name="uploadFile2"><br/>
        <input type="file" name="uploadFile3"><br/>
        <input type="file" name="uploadFile4"><br/>
        <input type="submit" value="Submit">
    </form>

    <br/><br/><br/>
    <c:if test="${not empty blobInfos}">
      <text>Files uploaded :</text><br/>
      <c:forEach var="blobInfo" begin="0" items="${blobInfos}">
        File&nbsp;:&nbsp;<a href="/blobstore/download?blob-key=${blobInfo.blobKey.keyString}">${blobInfo.filename}</a><br/>
        Blobstore ID&nbsp;:&nbsp;<text>${blobInfo.blobKey.keyString}</text><br/>
        Content Type&nbsp;:&nbsp;<text>${blobInfo.contentType}</text><br/>
        Creation Date&nbsp;:&nbsp;<text>${blobInfo.creation}</text><br/>
        MD5 Hash&nbsp;:&nbsp;<text>${blobInfo.md5Hash}</text><br/>
        Size&nbsp;:&nbsp;<text>${blobInfo.size}</text><br/>

        <br/>
      </c:forEach>
    </c:if>

</body>
</html>


<%
    session.removeAttribute("blobInfos");
%>