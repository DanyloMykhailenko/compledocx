package com.dariomorgrane.compledocx.model.document;

import com.dariomorgrane.compledocx.exception.*;

public interface DocumentInProcessing {

    String getName();

    byte[] getProcessedContent() throws EmptyParagraphException, DocxSavingException, DocxParsingException;

}
