package com.dariomorgrane.compledocx.web;

import com.dariomorgrane.compledocx.model.document.DocumentInProcessingImpl;
import com.dariomorgrane.compledocx.model.document.SupplementedDocument;
import com.dariomorgrane.compledocx.service.SupplementedDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        path = "/supplemented-documents",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class SupplementedDocumentController {

    private final SupplementedDocumentService service;

    @Autowired
    public SupplementedDocumentController(SupplementedDocumentService service) {
        this.service = service;
    }

    //TODO по возвращаемому значению:
    // нам не только нужно исключить байты из сериализации, но и НЕ загружать их из базы
    // и все равно мб возвращать буду эту модель, просто как-то хиберу скажу не сетить байты
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public SupplementedDocument createDocument(
            @RequestBody DocumentInProcessingImpl documentInProcessing
    ) {
        return service.addNewDocument(documentInProcessing);
    }

    @GetMapping("/{documentId}")
    public SupplementedDocument readDocument(@PathVariable Long documentId) {
        return service.getDocument(documentId);
    }

    @GetMapping
    public List<SupplementedDocument> readDocuments() { //TODO add pagination here and exclude bytes
        return service.getDocuments();
    }

    @DeleteMapping("/{documentId}")
    public void deleteDocument(@PathVariable Long documentId) {
        service.deleteDocument(documentId);
    }

}
