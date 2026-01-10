package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class ApiLoggingFilter implements Filter {

    private static final Logger logger =
            LogManager.getLogger(ApiLoggingFilter.class);

    @Override
    public Response filter(FilterableRequestSpecification request,
                           FilterableResponseSpecification response,
                           FilterContext context) {

        long startTime = System.currentTimeMillis();

        // -------- REQUEST --------
        logger.info("REQUEST METHOD : {}", request.getMethod());
        logger.info("REQUEST URI    : {}", request.getURI());
        logger.info("REQUEST HEADERS: {}", request.getHeaders());

        try {
            Object body = request.getBody();
            if (body != null) {
                logger.info("REQUEST BODY  : {}", body.toString());
            }
        } catch (Exception e) {
            logger.warn("REQUEST BODY  : <unable to log safely>");
        }

        // Send request
        Response res = context.next(request, response);

        // -------- RESPONSE --------
        logger.info("RESPONSE STATUS: {}", res.getStatusCode());
        logger.info("RESPONSE BODY  : {}", res.asPrettyString());
        logger.info("RESPONSE TIME  : {} ms",
                System.currentTimeMillis() - startTime);

        return res;
    }
}