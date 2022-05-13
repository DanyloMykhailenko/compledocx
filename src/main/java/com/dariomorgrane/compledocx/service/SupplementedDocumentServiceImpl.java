package com.dariomorgrane.compledocx.service;

import com.dariomorgrane.compledocx.dto.SupplementedDocumentDto;
import com.dariomorgrane.compledocx.dto.SupplementedDocumentDtoImpl;
import com.dariomorgrane.compledocx.exception.*;
import com.dariomorgrane.compledocx.model.document.*;
import com.dariomorgrane.compledocx.persistence.SupplementedDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplementedDocumentServiceImpl implements SupplementedDocumentService {

    private final SupplementedDocumentRepository repository;

    @Autowired
    public SupplementedDocumentServiceImpl(SupplementedDocumentRepository repository) {
        this.repository = repository;
    }

    @Override
    public SupplementedDocumentDto addNewDocument(DocumentInProcessing documentInProcessing)
            throws EmptyParagraphException, DocxSavingException, DocxLoadingException {
        SupplementedDocument savedDocument = repository.save(
                new SupplementedDocumentImpl(
                        documentInProcessing.getName(),
                        documentInProcessing.getProcessedContent()
                )
        );
        return new SupplementedDocumentDtoImpl(
                savedDocument.getId(),
                savedDocument.getName(),
                savedDocument.getCreateTimestamp()
        );
    }

    @Override
    public SupplementedDocument getDocument(Long documentId) throws DocumentNotFoundException {
        return repository.findById(documentId)
                .orElseThrow(() -> new DocumentNotFoundException(documentId));
    }

    @Override
    public List<? extends SupplementedDocumentDto> getDocuments(Pageable pageable) {
        return repository.findAllBy(pageable).getContent();
    }

    @Override
    public void deleteDocument(Long documentId) {
        repository.deleteById(documentId);
    }

}
