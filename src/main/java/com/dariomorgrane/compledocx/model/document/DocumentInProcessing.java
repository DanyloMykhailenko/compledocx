package com.dariomorgrane.compledocx.model.document;

import com.dariomorgrane.compledocx.exception.DocxLoadingException;
import com.dariomorgrane.compledocx.exception.DocxSavingException;
import com.dariomorgrane.compledocx.exception.EmptyParagraphException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = DocumentInProcessingImpl.class)
public interface DocumentInProcessing {

    String getName();

    byte[] getProcessedContent() throws EmptyParagraphException, DocxSavingException, DocxLoadingException;

}
