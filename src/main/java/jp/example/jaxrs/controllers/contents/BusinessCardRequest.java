package jp.example.jaxrs.controllers.contents;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BusinessCardRequest {

	@JsonProperty("template_name")
	public String templateName;
	
	public Map<String, String> parameters;
}
