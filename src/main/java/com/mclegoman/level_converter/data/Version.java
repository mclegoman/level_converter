package com.mclegoman.level_converter.data;

public class Version {
	private final String id;
	private final String name;
	private final String version;
	private final String author;
	private final Licence licence;
	private final String year;
	private final String source;
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
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public String getVersion() {
		return this.version;
	}
	public String getAuthor() {
		return this.author;
	}
	public Licence getLicence() {
		return this.licence;
	}
	public String getYear() {
		return this.year;
	}
	public static class Licence {
		private final String id;
		private final String url;
		public Licence(String id, String url) {
			this.id = id;
			this.url = url;
		}
		public String getId() {
			return this.id;
		}
		public String getUrl() {
			return this.url;
		}
	}
	public String getSource() {
		return this.source;
	}
}