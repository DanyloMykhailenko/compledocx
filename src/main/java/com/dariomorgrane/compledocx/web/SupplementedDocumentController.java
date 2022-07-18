package com.dariomorgrane.compledocx.web;

import com.dariomorgrane.compledocx.dto.SupplementedDocumentDto;
import com.dariomorgrane.compledocx.exception.*;
import com.dariomorgrane.compledocx.model.document.*;
import com.dariomorgrane.compledocx.service.SupplementedDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.DataBinder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

import static org.springframework.http.MediaType.*;

@CrossOrigin
@RestController
@RequestMapping("/supplemented-documents")
@Validated
public class SupplementedDocumentController {

    private final SupplementedDocumentService service;

    @Autowired
    public SupplementedDocumentController(SupplementedDocumentService service) {
        this.service = service;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SupplementedDocumentDto createDocument(
            @Valid @RequestBody DocumentInProcessing documentInProcessing
    ) throws EmptyParagraphException, DocxSavingException, DocxLoadingException {
        return service.addNewDocument(documentInProcessing);
    }

    @GetMapping(path = "/{documentId}", produces = APPLICATION_JSON_VALUE)
    public SupplementedDocument readDocument(@Positive @PathVariable Long documentId) {
        return service.getDocument(documentId);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<? extends SupplementedDocumentDto> readDocuments(Pageable pageable) {
        return service.getDocuments(pageable);
    }

    @DeleteMapping("/{documentId}")
    public void deleteDocument(@Positive @PathVariable Long documentId) {
        service.deleteDocument(documentId);
    }

    @InitBinder
    private void activateDirectFieldAccess(DataBinder dataBinder) {
        dataBinder.initDirectFieldAccess();
    }

}
