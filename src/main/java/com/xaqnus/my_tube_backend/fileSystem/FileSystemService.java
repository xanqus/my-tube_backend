package com.xaqnus.my_tube_backend.fileSystem;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileSystemService {
    public void createFolder(String root) {
        File fileCheck = new File(root);
        if(!fileCheck.exists()) fileCheck.mkdirs();
    }
}
