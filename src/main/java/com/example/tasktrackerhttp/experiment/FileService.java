package com.example.tasktrackerhttp.experiment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    @Autowired  // лучше не использовать, потому что внедрение происходит после создания объекта, а иногда бывает что необходимо использовать бины в конструкторе
    private InternetService internetService;

    public void saveFile() {
        System.out.println("Я СОХРАНЯЮ ФАЙЛ");
    }
}
