package com.dariomorgrane.compledocx.model.document;

import com.dariomorgrane.compledocx.exception.*;
import com.dariomorgrane.compledocx.model.speaker.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;

public class DocumentInProcessingImpl implements DocumentInProcessing {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentInProcessingImpl.class);

    private static final String PRESERVE_SPACE_ATTRIBUTE_VALUE = "preserve";

    @NotBlank
    private final String name;

    @NotEmpty
    private final byte[] originalContent;

    @NotNull
    @Valid
    private final SpeakersSettings speakersSettings;

    @JsonCreator
    public DocumentInProcessingImpl(
            String name,
            byte[] originalContent,
            SpeakersSettings speakersSettings
    ) {
        this.name = name;
        this.originalContent = originalContent;
        this.speakersSettings = speakersSettings;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public byte[] getProcessedContent() throws EmptyParagraphException, DocxSavingException, DocxLoadingException {
        WordprocessingMLPackage docxInProcessing = bytesToDocx(originalContent);
        Speaker currentSpeaker = speakersSettings.getFirstSpeaker();
        for (Object contentElement : docxInProcessing.getMainDocumentPart().getContent()) {
            if (!(contentElement instanceof P currentParagraph)) {
                continue;
            }
            currentParagraph.getContent().add(
                    0, generateSpeakerDesignationRun(currentSpeaker, currentParagraph)
            );
            currentSpeaker = currentSpeaker.getAnotherSpeaker();
        }
        return docxToBytes(docxInProcessing);
    }

    private WordprocessingMLPackage bytesToDocx(byte[] bytes) throws DocxLoadingException {
        WordprocessingMLPackage docx;
        try {
            docx = WordprocessingMLPackage.load(
                    new ByteArrayInputStream(bytes)
            );
        } catch (Docx4JException e) {
            DocxLoadingException exception = new DocxLoadingException(name, e);
            LOGGER.error(exception.getMessage(), exception);
            throw exception;
        }
        return docx;
    }

    private R generateSpeakerDesignationRun(Speaker currentSpeaker, P paragraph) throws EmptyParagraphException {
        R speakerDesignationRun = new R();
        speakerDesignationRun.getContent().add(generateSpeakerDesignationText(currentSpeaker));
        speakerDesignationRun.setRPr(generateSpeakerDesignationRunProperties(paragraph));
        return speakerDesignationRun;
    }

    private Text generateSpeakerDesignationText(Speaker speaker) {
        Text speakerDesignationText = new Text();
        speakerDesignationText.setValue(
                speaker == Speaker.INTERVIEWER
                        ? speakersSettings.getInterviewerDesignation()
                        : speakersSettings.getRespondentDesignation()
        );
        speakerDesignationText.setSpace(PRESERVE_SPACE_ATTRIBUTE_VALUE);
        return speakerDesignationText;
    }

    private RPr generateSpeakerDesignationRunProperties(P paragraph) throws EmptyParagraphException {
        for (Object paragraphElement : paragraph.getContent()) {
            if (paragraphElement instanceof R run) {
                RPr resultRunProperties = XmlUtils.deepCopy(run.getRPr());
                BooleanDefaultTrue isBold = new BooleanDefaultTrue();
                isBold.setVal(speakersSettings.speakersDesignationsAreBold());
                resultRunProperties.setB(isBold);
                return resultRunProperties;
            }
        }
        EmptyParagraphException exception = new EmptyParagraphException("An empty paragraph was found.");
        LOGGER.error(exception.getMessage(), exception);
        throw exception;
    }

    private byte[] docxToBytes(WordprocessingMLPackage docx) throws DocxSavingException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            docx.save(outputStream);
        } catch (Docx4JException e) {
            DocxSavingException exception = new DocxSavingException(name, e);
            LOGGER.error(exception.getMessage(), exception);
            throw exception;
        }
        return outputStream.toByteArray();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentInProcessingImpl that = (DocumentInProcessingImpl) o;
        return name.equals(that.name)
                && Arrays.equals(originalContent, that.originalContent)
                && speakersSettings.equals(that.speakersSettings);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, speakersSettings);
        result = 31 * result + Arrays.hashCode(originalContent);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(",\n", DocumentInProcessingImpl.class.getSimpleName() + "[\n", "]")
                .add("name='" + name + "'")
                .add("originalContent=" + Arrays.toString(originalContent))
                .add("speakersSettings=" + speakersSettings)
                .toString();
    }

}
