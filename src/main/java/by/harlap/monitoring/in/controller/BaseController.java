package by.harlap.monitoring.in.controller;

import by.harlap.monitoring.dto.ErrorResponse;
import by.harlap.monitoring.exception.GenericHttpException;
import by.harlap.monitoring.util.IOUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * BaseController provides common functionality for controller classes.
 */
public abstract class BaseController extends HttpServlet {

    /**
     * Overrides the service method of HttpServlet to handle exceptions uniformly.
     *
     * @param req  HttpServletRequest object representing the request
     * @param resp HttpServletResponse object representing the response
     * @throws ServletException if an error occurs while processing the request
     * @throws IOException      if an I/O error occurs while handling the request
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            super.service(req, resp);
        } catch (GenericHttpException exception) {
            final ErrorResponse responseData = new ErrorResponse(exception.getMessage());
            IOUtil.write(resp, responseData, exception.getCode());
        }
    }
}
