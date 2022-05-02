package com.dariomorgrane.compledocx.service;

import com.dariomorgrane.compledocx.model.document.DocumentInProcessing;
import com.dariomorgrane.compledocx.model.document.SupplementedDocument;

import java.util.List;

public interface SupplementedDocumentService {

    SupplementedDocument addNewDocument(DocumentInProcessing documentInProcessing);

    SupplementedDocument getDocument(Long documentId);

    List<SupplementedDocument> getDocuments();

    void deleteDocument(Long documentId);

}
