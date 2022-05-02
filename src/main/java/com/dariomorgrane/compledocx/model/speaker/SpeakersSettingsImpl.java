package com.dariomorgrane.compledocx.model.speaker;

public class SpeakersSettingsImpl implements SpeakersSettings {

    private final String interviewerDesignation;

    private final String respondentDesignation;

    private final Speaker firstSpeaker;

    private final Boolean speakersDesignationsAreBold;

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

}
