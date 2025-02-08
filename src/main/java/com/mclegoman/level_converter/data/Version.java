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
	private String version;
	private String author;
	private Licence licence;
	private String year;
	private String source;
	public Version() {}
	public Version(String id, String name, String version, String author, Licence licence, String year, String source) {
		this.id = id;
		this.name = name;
		this.version = version;
		this.author = author;
		this.licence = licence;
		this.year = year;
		this.source = source;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
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
		return licence;
	}
	public void setLicence(Licence licence) {
		this.licence = licence;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getSource() {
		return source;
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
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String toString() {
			return "Licence{id='" + id + "', url='" + url + "'}";
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
		return "Version{id='" + id + "', name='" + name + "', version='" + version + "', author='" + author + "', licence=" + licence + ", year='" + year + "', source='" + source + "'}";
	}
}
