package com.dariomorgrane.compledocx.model.speaker;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = SpeakersSettingsImpl.class)
public interface SpeakersSettings {

    String getInterviewerDesignation();

    String getRespondentDesignation();

    Speaker getFirstSpeaker();

    Boolean speakersDesignationsAreBold();

}
