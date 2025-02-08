/*
    Level Converter
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/level_converter
    Licence: GNU LGPLv3
*/

package com.mclegoman.level_converter.data;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class Version {
	private String id;
	private String name;
	private String description;
	private String version;
	private String author;
	private Licence licence;
	private String year;
	private String source;
	public Version() {}
	public Version(String id, String name, String description, String version, String author, Licence licence, String year, String source) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.version = version;
		this.author = author;
		this.licence = licence;
		this.year = year;
		this.source = source;
	}
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVersion() {
		return this.version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Licence getLicence() {
		return this.licence;
	}
	public void setLicence(Licence licence) {
		this.licence = licence;
	}
	public String getYear() {
		return this.year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getSource() {
		return this.source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public static class Licence {
		private String id;
		private String url;
		public Licence() {}
		public Licence(String id, String url) {
			this.id = id;
			this.url = url;
		}
		public String getId() {
			return this.id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getUrl() {
			return this.url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String toString() {
			return "Licence{id='" + this.id + "', url='" + this.url + "'}";
		}
	}
	public static Version create(String resourceLocation) {
		ObjectMapper objectMapper = new ObjectMapper();
		try (InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(resourceLocation)) {
			if (inputStream == null) throw new IOException("Resource not found: " + resourceLocation);
			return objectMapper.readValue(inputStream, Version.class);
		} catch (Exception error) {
			System.out.println(error.getLocalizedMessage());
			return null;
		}
	}
	public String toString() {
		return "Version{id='" + this.id + "', name='" + this.name + "', description='" + this.description + "', version='" + this.version + "', author='" + this.author + "', licence=" + this.licence + ", year='" + this.year + "', source='" + this.source + "'}";
	}
}
