package jp.example.jaxrs.helpers;

import jakarta.ws.rs.core.UriInfo;

public class UriInfoHelper {
	public static String getServletUrl(UriInfo uriInfo) {
		return String.format("%s://%s", uriInfo.getBaseUri().getScheme(), uriInfo.getBaseUri().getAuthority());
	}
}
