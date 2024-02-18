package by.harlap.monitoring.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Utility class for input/output operations for controllers.
 */
public final class IOUtil {

    private static final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    private IOUtil() {
    }

    /**
     * Reads JSON data from the request and maps it to the specified class.
     *
     * @param request      the HTTP servlet request containing the JSON data
     * @param mappingClass the class to which the JSON data should be mapped
     * @param <T>          the type of the mapping class
     * @return an instance of the specified class mapped from the JSON data
     * @throws IOException if an I/O error occurs
     */
    public static <T> T read(HttpServletRequest request, Class<? extends T> mappingClass) throws IOException {
        return objectMapper.readValue(request.getReader(), mappingClass);
    }

    /**
     * Writes data to the HTTP servlet response in JSON format.
     *
     * @param response the HTTP servlet response where the data will be written
     * @param data     the data to be written
     * @param code     the HTTP status code to be set in the response
     * @throws IOException if an I/O error occurs
     */
    public static void write(HttpServletResponse response, Object data, int code) throws IOException {
        final String result = objectMapper.writeValueAsString(data);
        response.setContentType("application/json");
        response.setStatus(code);
        response.getWriter().write(result);
    }
}
