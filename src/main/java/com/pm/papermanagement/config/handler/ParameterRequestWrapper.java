//package com.pm.papermanagement.config.handler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletRequestWrapper;
//import org.springframework.http.MediaType;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.util.Map;
//
//public class ParameterRequestWrapper extends HttpServletRequestWrapper {
//    Map<String, String> requestBody;
//
//    /**
//     * Constructs a request object wrapping the given request.
//     *
//     * @param request The request to wrap
//     * @throws IllegalArgumentException if the request is null
//     */
//    public ParameterRequestWrapper(HttpServletRequest request) throws IOException {
//        super(request);
//        StringBuilder sb = new StringBuilder();
//        BufferedReader reader = request.getReader();
//        String s;
//        while ((s = reader.readLine()) != null) {
//            sb.append(s);
//        }
//        requestBody = new ObjectMapper().readValue(sb.toString(), Map.class);
//    }
//
//    @Override
//    public String getContentType() {
//        return MediaType.APPLICATION_FORM_URLENCODED_VALUE;
//    }
//
//    @Override
//    public String getParameter(String name) {
//        return requestBody.get(name);
//    }
//}
