package com.twb.files.upload.db.message;

import java.time.LocalDate;

public class ResponseFile {
  private String name;
  private String url;
  private String type;
  private long size;

  private String description;
  private String other;
  private LocalDate date;

  public ResponseFile(){}
  public ResponseFile(String name, String url, String type, long size) {
    this.name = name;
    this.url = url;
    this.type = type;
    this.size = size;
  }

  public ResponseFile(String name, String url, String type, long size, String description, LocalDate date, String other) {
    this.name = name;
    this.url = url;
    this.type = type;
    this.size = size;
    this.description = description;
    this.date = date;
    this.other = other;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getOther() {
    return other;
  }

  public void setOther(String other) {
    this.other = other;
  }

  @Override
  public String toString() {
    return "ResponseFile{" +
            "name='" + name + '\'' +
            ", url='" + url + '\'' +
            ", type='" + type + '\'' +
            ", size=" + size +
            ", description='" + description + '\'' +
            ", other='" + other + '\'' +
            ", date=" + date +
            '}';
  }
}
