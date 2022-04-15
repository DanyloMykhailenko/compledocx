package com.dariomorgrane.compledocx.model;

import com.dariomorgrane.compledocx.exception.*;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class DocumentInProcessingImpl implements DocumentInProcessing {

    private static final String PRESERVE_SPACE_ATTRIBUTE_VALUE = "preserve";

    private final String name;

    private final byte[] originalContent;

    private final SpeakersSettings speakersSettings;

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
    public byte[] getProcessedContent() throws EmptyParagraphException, DocxSavingException, DocxParsingException {
        WordprocessingMLPackage docxInProcessing;
        try {
            docxInProcessing = WordprocessingMLPackage.load(
                    new ByteArrayInputStream(originalContent)
            );
        } catch (Docx4JException e) {
            throw new DocxParsingException(String.format("Can not parse docx %s.", name), e);
        }
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
        throw new EmptyParagraphException("An empty paragraph was found.");
    }

    private byte[] docxToBytes(WordprocessingMLPackage docx) throws DocxSavingException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            docx.save(outputStream);
        } catch (Docx4JException e) {
            throw new DocxSavingException(String.format("Can not save docx %s.", name), e);
        }
        return outputStream.toByteArray();
    }

}
