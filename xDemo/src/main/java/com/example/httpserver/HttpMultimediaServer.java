package com.example.httpserver;

import android.os.Environment;
import android.util.Log;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.request.Method;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.protocols.http.response.Status;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ww on 2017/8/3.
 */

public class HttpMultimediaServer extends NanoHTTPD {
    public static final String tag = HttpMultimediaServer.class.getSimpleName();

    public static final String FOLDER_NAME = "Deep";
    public static final int PORT = 8888;

    public HttpMultimediaServer() {
        super(PORT);
    }

    @Override
    protected Response serve(IHTTPSession session) {
        Method method = session.getMethod();
        Map<String, String> files = new HashMap<>();
        if (Method.GET.equals(method)) {
            return createResponse(Status.OK, MIME_HTML, makeResponseHtml());
        }
        if (Method.POST.equals(method) || Method.PUT.equals(method)) {
            try {
                session.parseBody(files);
            } catch (IOException ioe) {
                return Response.newFixedLengthResponse("Internal Error IO Exception: " + ioe.getMessage());
            } catch (ResponseException re) {
                return Response.newFixedLengthResponse(re.getStatus(), MIME_PLAINTEXT, re.getMessage());
            }
        }
        String curDir = Environment.getExternalStorageDirectory().getPath();
        if (files.size() > 0) {
            String filename = null, tmpFilePath;
            File src, dst;
            for (Map.Entry entry : files.entrySet()) {
                try {
                    filename = URLDecoder.decode(entry.getKey().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (filename != null && filename.startsWith("C:\\fakepath\\")) {
                    filename = filename.substring(12);
                }
                tmpFilePath = entry.getValue().toString();
                File baseFolder = new File(curDir, FOLDER_NAME);
                if (!baseFolder.exists()) {
                    baseFolder.mkdirs();
                }
                dst = new File(baseFolder, filename);
                if (dst.exists()) {
                    deleteFile(dst);
                }
                src = new File(tmpFilePath);
                if (!copyFile(src, dst)) {
                    return getResponse("Internal Error: Uploading failed");
                }
            }
            return getResponse("Success");
        }

        return getResponse("Error 404: File not found");
    }

    private boolean deleteFile(File target) {
        if (target.isDirectory()) {
            for (File child : target.listFiles()) {
                if (!deleteFile(child)) {
                    return false;
                }
            }
        }
        return target.delete();
    }

    private String responseHtml;

    private String makeResponseHtml() {
        if (responseHtml == null) {
            String style = "<style>" + "html {background-color:#eeeeee;} "
                    + "body { background-color:#FFFFFF; "
                    + "font-family:Tahoma,Arial,Helvetica,sans-serif; "
                    + "font-size:18x; " + "border:3px " + "groove #006600; "
                    + "padding:15px; } " + "</style>";
            String script = "<script language='javascript'>"
                    + "function handleFile(){"
                    + "var file = document.getElementById(\"file\");"
                    + "var filename = document.getElementById(\"fileName\");"
                    + "file.name = encodeURI(file.value);"
                    + "filename.value = file.name"
                    + "}"
                    + "</script>";
            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            sb.append("<head>");
            sb.append("<title>Files from device</title>");
            sb.append(style);
            // form upload
            sb.append(script);
            sb.append("</head>");
            sb.append("<body alink=\"blue\" vlink=\"blue\">");
            sb.append("<h3>File Upload:</h3>");
            sb.append("选择上传文件: <br/>");
            sb.append("<form action=\"\" method=\"post\"  enctype=\"multipart/form-data\">");
            sb.append("<input type=\"text\" disabled=\"disable\" value=\"文件名\" id=\"fileName\">");
            sb.append("<input type=\"file\" name=\"uploadfile\" size=\"50\" id=\"file\" onchange=\"handleFile()\"/>");
            sb.append("<input type=\"submit\" value=\"上传文件\" />");
            sb.append("</form>");
            sb.append("</body>");
            sb.append("</html>");
            responseHtml = sb.toString();
        }
        return responseHtml;
    }

    private boolean copyFile(File source, File target) {
        if (source.isDirectory()) {
            if (!target.exists()) {
                if (!target.mkdir()) {
                    return false;
                }
            }
            String[] children = source.list();
            for (int i = 0; i < source.listFiles().length; i++) {
                if (!copyFile(new File(source, children[i]), new File(target, children[i]))) {
                    return false;
                }
            }
        } else {
            try {
                InputStream in = new FileInputStream(source);
                OutputStream out = new FileOutputStream(target);

                byte[] buf = new byte[65536];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            } catch (IOException ioe) {
                return false;
            }
        }
        return true;
    }

    private Response getResponse(String message) {
        return createResponse(Status.OK, MIME_PLAINTEXT, message);
    }

    // Announce that the file server accepts partial content requests
    private Response createResponse(Status status, String mimeType, String message) {
        Response res = Response.newFixedLengthResponse(status, mimeType, message);
        res.addHeader("Accept-Ranges", "bytes");
        return res;
    }
}
