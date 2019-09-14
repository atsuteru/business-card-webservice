package jp.example.jaxrs.controllers.contents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BusinessCardResponse {

	public boolean ok;

	public String error;
	
	public byte[] pdf;
}
