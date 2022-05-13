package com.dariomorgrane.compledocx.service;

import com.dariomorgrane.compledocx.dto.SupplementedDocumentDto;
import com.dariomorgrane.compledocx.exception.*;
import com.dariomorgrane.compledocx.model.document.DocumentInProcessing;
import com.dariomorgrane.compledocx.model.document.SupplementedDocument;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SupplementedDocumentService {

    SupplementedDocumentDto addNewDocument(DocumentInProcessing documentInProcessing) throws EmptyParagraphException, DocxSavingException, DocxLoadingException;

    SupplementedDocument getDocument(Long documentId);

    List<? extends SupplementedDocumentDto> getDocuments(Pageable pageable);

    void deleteDocument(Long documentId);

}
