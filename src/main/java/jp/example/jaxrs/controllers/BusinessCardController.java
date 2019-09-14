package jp.example.jaxrs.controllers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import jp.example.businesscard.BusinessCardGenerator;
import jp.example.jaxrs.controllers.contents.BusinessCardRequest;
import jp.example.jaxrs.controllers.contents.BusinessCardResponse;
import jp.example.jaxrs.helpers.UriInfoHelper;

@Path("businesscard")
public class BusinessCardController {
	@GET
	@Path("/generate/as/pdf")
	@Produces("application/pdf")
	public InputStream generatePdf(@QueryParam("template") String templateName, @Context UriInfo uriInfo) {
		var parameters = new HashMap<String, String>();
		for (Map.Entry<String, List<String>> entry : uriInfo.getQueryParameters().entrySet()) {
			parameters.put(entry.getKey(), entry.getValue().get(0));
		}
		var request = new BusinessCardRequest();
		request.templateName = templateName;
		request.parameters = parameters;
		var response = generate(request, uriInfo);
		return new ByteArrayInputStream(response.pdf);
	}

	@GET
	@Path("/generate")
	@Produces(MediaType.APPLICATION_JSON)
	public BusinessCardResponse generate(@QueryParam("template") String templateName, @Context UriInfo uriInfo) {
		var parameters = new HashMap<String, String>();
		for (Map.Entry<String, List<String>> entry : uriInfo.getQueryParameters().entrySet()) {
			parameters.put(entry.getKey(), entry.getValue().get(0));
		}
		var request = new BusinessCardRequest();
		request.templateName = templateName;
		request.parameters = parameters;
		return generate(request, uriInfo);
	}

	@POST
	@Path("/generate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BusinessCardResponse generate(BusinessCardRequest request, @Context UriInfo uriInfo) {
		try {
			byte[] pdfData = BusinessCardGenerator.Generate(
					request.templateName, 
					request.parameters, 
					UriInfoHelper.getServletUrl(uriInfo));
			
			return new BusinessCardResponse() {{
				ok = true;
				pdf = pdfData;
			}};
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return new BusinessCardResponse() {{
				ok = false;
				error = e.getMessage();
			}};
		}
	}
}
