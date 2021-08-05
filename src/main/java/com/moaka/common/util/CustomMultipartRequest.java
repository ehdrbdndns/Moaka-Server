package com.moaka.common.util;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;
import main.exceptions.BusinessException;
import main.exceptions.enums.BusinessExceptionType;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

public class CustomMultipartRequest extends MultipartRequest {
    public CustomMultipartRequest(HttpServletRequest request, String saveDirectory) throws IOException {
        super(request, saveDirectory);
    }

    public CustomMultipartRequest(HttpServletRequest request, String saveDirectory, int maxPostSize) throws IOException {
        super(request, saveDirectory, maxPostSize);
    }

    public CustomMultipartRequest(HttpServletRequest request, String saveDirectory, String encoding) throws IOException {
        super(request, saveDirectory, encoding);
    }

    public CustomMultipartRequest(HttpServletRequest request, String saveDirectory, int maxPostSize, FileRenamePolicy policy) throws IOException {
        super(request, saveDirectory, maxPostSize, policy);
    }

    public CustomMultipartRequest(HttpServletRequest request, String saveDirectory, int maxPostSize, String encoding) throws IOException {
        super(request, saveDirectory, maxPostSize, encoding);
    }

    public CustomMultipartRequest(HttpServletRequest request, String saveDirectory, int maxPostSize, String encoding, FileRenamePolicy policy) throws IOException {
        super(request, saveDirectory, maxPostSize, encoding, policy);
    }

    public CustomMultipartRequest(ServletRequest request, String saveDirectory) throws IOException {
        super(request, saveDirectory);
    }

    public CustomMultipartRequest(ServletRequest request, String saveDirectory, int maxPostSize) throws IOException {
        super(request, saveDirectory, maxPostSize);
    }

    @Override
    public Enumeration getParameterNames() {
        return super.getParameterNames();
    }

    @Override
    public Enumeration getFileNames() {
        return super.getFileNames();
    }

    @Override
    public String getParameter(String name) {
        try {
            return new String(super.getParameter(name).getBytes("8859_1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new BusinessException(BusinessExceptionType.STRING_CHANGER_EXCEPTION, "Multipart Overriding getParameter()");
        }
    }

    @Override
    public String[] getParameterValues(String name) {
        return super.getParameterValues(name);
    }

    @Override
    public String getFilesystemName(String name) {
        return super.getFilesystemName(name);
    }

    @Override
    public String getOriginalFileName(String name) {
        return super.getOriginalFileName(name);
    }

    @Override
    public String getContentType(String name) {
        return super.getContentType(name);
    }

    @Override
    public File getFile(String name) {
        return super.getFile(name);
    }
}
