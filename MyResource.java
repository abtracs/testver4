package jersey2;

import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.io.*;
import java.util.*;
import javax.ws.rs.PathParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.CookieParam;
import java.net.URI;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.PathSegment;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@Path("myresource")
public class MyResource {
    private Logger logger = LoggerFactory.getLogger(MyResource.class);

    @Context private HttpServletRequest request;
    @Context private HttpServletResponse response;
    @Context private UriInfo uriinfo;
    @Context private HttpHeaders hheaders;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CustomObject {
        @JsonProperty
        public String name;

        @JsonProperty
        public List<String> aliases;

        @JsonProperty
        public int age;

        public String toString() {
            return "CustomObject{name='" + this.name + "'}";
        }
        public String safeString() {
            return "nothing here";
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SafeStringObj {
        @JsonProperty
        public String name;

        @JsonProperty
        public List<String> aliases;

        @JsonProperty
        public int age;

        public String toString() {
            return "nothing here";
        }
        public String safeString() {
            return "nothing here";
        }
    }


    @POST
    @Consumes({"application/json"})
    @Produces("application/json")
    @Path("handleCustom")
    public CustomObject handleCustom(CustomObject inobj) {
        logger.debug("bad: " + this.bad1);      // CWEID 117
        logger.debug("bad: {}", this.bad1);      // CWEID 117
        logger.debug("bad: {}", new Object[] { this.bad1});      // CWEID 117

        logger.debug("name: " + inobj.name);    // CWEID 117
        logger.debug("name: {}", inobj.name);    // CWEID 117
        logger.debug("name: {}", new Object[] {inobj.name});    // CWEID 117

        if(inobj.aliases.size() > 0) {
            String alias1 = inobj.aliases.get(0);
            logger.debug("alias: " + alias1);    // CWEID 117
            logger.debug("alias: {}", alias1);    // CWEID 117
            logger.debug("alias: {}", new Object[] {alias1});    // CWEID 117
        }

        logger.debug("tostr: " + inobj);    // CWEID 117
        logger.debug("tostr: {}", inobj);    // CWEID 117
        logger.debug("tostr: {}", new Object[] {inobj});    // CWEID 117

        logger.debug("safeString: " + inobj.safeString());
        logger.debug("safeString: {}", inobj.safeString());
        logger.debug("safeString: {}", new Object[] {inobj.safeString()});

        CustomObject obj = new CustomObject();
        obj.name = "foo";
        obj.aliases = Arrays.asList(new String[] { "baz", "quux" });
        obj.age = 1337;

        logger.debug("good: " + this.good1);
        logger.debug("good: {}", this.good1);
        logger.debug("good: {}", new Object[] { this.good1});

        logger.debug("name: " + obj.name);
        logger.debug("name: {}", obj.name);
        logger.debug("name: {}", new Object[] {obj.name});

        if(obj.aliases.size() > 0) {
            String alias1 = obj.aliases.get(0);
            logger.debug("alias: " + alias1);
            logger.debug("alias: {}", alias1);
            logger.debug("alias: {}", new Object[] {alias1});
        }

        logger.debug("tostr: " + obj);
        logger.debug("tostr: {}", obj);
        logger.debug("tostr: {}", new Object[] {obj});


        return obj;

    }

    @POST
    @Consumes({"application/json"})
    @Produces("application/json")
    @Path("handleSafe")
    public SafeStringObj handleSafe(SafeStringObj inobj) {
        logger.debug("bad: " + this.bad1);      // CWEID 117
        logger.debug("bad: {}", this.bad1);      // CWEID 117
        logger.debug("bad: {}", new Object[] { this.bad1});      // CWEID 117

        logger.debug("name: " + inobj.name);    // CWEID 117
        logger.debug("name: {}", inobj.name);    // CWEID 117
        logger.debug("name: {}", new Object[] {inobj.name});    // CWEID 117

        if(inobj.aliases.size() > 0) {
            String alias1 = inobj.aliases.get(0);
            logger.debug("alias: " + alias1);    // CWEID 117
            logger.debug("alias: {}", alias1);    // CWEID 117
            logger.debug("alias: {}", new Object[] {alias1});    // CWEID 117
        }

        logger.debug("tostr: " + inobj);
        logger.debug("tostr: {}", inobj);
        logger.debug("tostr: {}", new Object[] {inobj});

        logger.debug("safeString: " + inobj.safeString());
        logger.debug("safeString: {}", inobj.safeString());
        logger.debug("safeString: {}", new Object[] {inobj.safeString()});

        SafeStringObj obj = new SafeStringObj();
        obj.name = "foo";
        obj.aliases = Arrays.asList(new String[] { "baz", "quux" });
        obj.age = 1337;

        logger.debug("good: " + this.good1);
        logger.debug("good: {}", this.good1);
        logger.debug("good: {}", new Object[] { this.good1});

        logger.debug("name: " + obj.name);
        logger.debug("name: {}", obj.name);
        logger.debug("name: {}", new Object[] {obj.name});

        if(obj.aliases.size() > 0) {
            String alias1 = obj.aliases.get(0);
            logger.debug("alias: " + alias1);
            logger.debug("alias: {}", alias1);
            logger.debug("alias: {}", new Object[] {alias1});
        }

        logger.debug("tostr: " + obj);
        logger.debug("tostr: {}", obj);
        logger.debug("tostr: {}", new Object[] {obj});


        return obj;

    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        logger.debug("gotit");
        return "Got it!";
    }

    @QueryParam("bad")
    private String bad1;

    private String good1 = "nothing";

    @GET
    @Path("badresp1")
    public Response badresp1() {
        return Response.ok("badresp1: " + bad1, "text/html").build();       // CWEID 80
    }

    @POST
    @Consumes("application/json")
    @Path("badresp2")
    public Response badresp2(CustomObject obj) {
        return Response.ok("badresp2: " + obj.name, "text/html").build();       // CWEID 80
    }

    @POST
    @Consumes("application/json")
    @Path("badresp3")
    public Response badresp3(CustomObject obj) {
        return Response.ok("badresp3: " + obj, "text/html").build();       // CWEID 80
    }

 
}
