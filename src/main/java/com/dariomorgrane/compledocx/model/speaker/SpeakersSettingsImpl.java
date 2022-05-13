package com.dariomorgrane.compledocx.model.speaker;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.StringJoiner;

public class SpeakersSettingsImpl implements SpeakersSettings {

    @NotBlank
    private final String interviewerDesignation;

    @NotBlank
    private final String respondentDesignation;

    @NotNull
    private final Speaker firstSpeaker;

    @NotNull
    private final Boolean speakersDesignationsAreBold;

    @JsonCreator
    public SpeakersSettingsImpl(
            String interviewerDesignation,
            String respondentDesignation,
            Speaker firstSpeaker,
            Boolean speakersDesignationsAreBold
    ) {
        this.interviewerDesignation = interviewerDesignation;
        this.respondentDesignation = respondentDesignation;
        this.firstSpeaker = firstSpeaker;
        this.speakersDesignationsAreBold = speakersDesignationsAreBold;
    }

    @Override
    public String getInterviewerDesignation() {
        return interviewerDesignation;
    }

    @Override
    public String getRespondentDesignation() {
        return respondentDesignation;
    }

    @Override
    public Speaker getFirstSpeaker() {
        return firstSpeaker;
    }

    @Override
    public Boolean speakersDesignationsAreBold() {
        return speakersDesignationsAreBold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpeakersSettingsImpl that = (SpeakersSettingsImpl) o;
        return interviewerDesignation.equals(that.interviewerDesignation)
                && respondentDesignation.equals(that.respondentDesignation)
                && firstSpeaker == that.firstSpeaker
                && speakersDesignationsAreBold.equals(that.speakersDesignationsAreBold);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                interviewerDesignation,
                respondentDesignation,
                firstSpeaker,
                speakersDesignationsAreBold
        );
    }

    @Override
    public String toString() {
        return new StringJoiner(",\n", SpeakersSettingsImpl.class.getSimpleName() + "[\n", "]")
                .add("interviewerDesignation='" + interviewerDesignation + "'")
                .add("respondentDesignation='" + respondentDesignation + "'")
                .add("firstSpeaker=" + firstSpeaker)
                .add("speakersDesignationsAreBold=" + speakersDesignationsAreBold)
                .toString();
    }

}
