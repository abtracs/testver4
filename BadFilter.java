package jersey2;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.container.ContainerRequestFilter;
import java.nio.charset.StandardCharsets;
import java.net.URI;
import javax.ws.rs.core.Response;
import javax.ws.rs.container.ContainerRequestContext;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.jersey.message.internal.ReaderWriter;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@PreMatching
public class BadFilter implements ContainerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(BadFilter.class);
    public void filter(ContainerRequestContext ctx) throws IOException {
        StringBuilder logMessage = new StringBuilder("Request received: ").append("Method:[").append(ctx.getMethod()).append("]").append(", URL:[").append(ctx.getUriInfo().getRequestUri().toString()).append(", Headers:[").append("]");

		InputStream in = ctx.getEntityStream();
		try {
			if ((in.available() > 0) || (ctx.getLength() > 0)) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				ReaderWriter.writeTo(in, out);
				byte[] requestEntity = out.toByteArray();

				logMessage
					.append(", Body:[")
					.append(new String(requestEntity, StandardCharsets.UTF_8))
					.append("]");

				ctx.setEntityStream(new ByteArrayInputStream(requestEntity));

				logger.info(new String(requestEntity, StandardCharsets.UTF_8));		// CWEID 117
				logger.info(logMessage.toString());	// CWEID 117;
			}
		} finally {
			logger.info(logMessage.toString());       // CWEID 117
		}
    }
}
