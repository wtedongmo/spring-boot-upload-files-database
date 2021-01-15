package com.twb.files.upload.db.service;

import java.io.IOException;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twb.files.upload.db.model.FileDB;
import com.twb.files.upload.db.model.ModelDto;
import com.twb.files.upload.db.repository.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

  @Autowired
  private FileDBRepository fileDBRepository;

  public FileDB store(MultipartFile file) throws IOException {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

    return fileDBRepository.save(FileDB);
  }

  public FileDB storeData(MultipartFile file, String model) throws IOException {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    ObjectMapper mapper = new ObjectMapper();
//    model = model.replaceAll("\n", "");
    ModelDto modelDto = mapper.readValue(model, ModelDto.class);
    FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes(), modelDto.getDescription(), modelDto.getDate(), modelDto.getOther());

    return fileDBRepository.save(FileDB);
  }

  public FileDB getFile(String id) {
    return fileDBRepository.findById(id).get();
  }
  
  public Stream<FileDB> getAllFiles() {
    return fileDBRepository.findAll().stream();
  }
}
