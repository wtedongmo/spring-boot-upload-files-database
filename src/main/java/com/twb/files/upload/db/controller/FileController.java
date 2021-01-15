package com.twb.files.upload.db.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.twb.files.upload.db.message.ResponseMessage;
import com.twb.files.upload.db.model.FileDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.twb.files.upload.db.service.FileStorageService;
import com.twb.files.upload.db.message.ResponseFile;

@Controller
@CrossOrigin("http://localhost:8081")
public class FileController {

  @Autowired
  private FileStorageService storageService;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";
    try {
      storageService.store(file);

      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }

  @PostMapping("/upload/data")
  public ResponseEntity<ResponseFile> uploadFileData(@RequestParam("file") MultipartFile file, @RequestParam("model") String model) {
    String message = "";
    try {
      FileDB dbFile = storageService.storeData(file, model);
      String fileDownloadUri = ServletUriComponentsBuilder
              .fromCurrentContextPath()
              .path("/files/")
              .path(dbFile.getId())
              .toUriString();
      ResponseFile result = new ResponseFile(
              dbFile.getName(),
              fileDownloadUri,
              dbFile.getType(),
              dbFile.getData().length, dbFile.getDescription(), dbFile.getDate(), dbFile.getOther());

//      message = "Uploaded the file and set data successfully: \n" + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(result);
    } catch (Exception e) {
      e.printStackTrace();
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      ResponseFile resp = new ResponseFile();
      resp.setDescription(message);
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(resp);
    }
  }

  @GetMapping("/files")
  public ResponseEntity<List<ResponseFile>> getListFiles() {
    List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
      String fileDownloadUri = ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/files/")
          .path(dbFile.getId())
          .toUriString();

      return new ResponseFile(
          dbFile.getName(),
          fileDownloadUri,
          dbFile.getType(),
          dbFile.getData().length, dbFile.getDescription(), dbFile.getDate(), dbFile.getOther());
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(files);
  }

  @GetMapping("/files/{id}")
  public ResponseEntity<byte[]> getFile(@PathVariable String id) {
    FileDB fileDB = storageService.getFile(id);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
        .body(fileDB.getData());
  }
}
