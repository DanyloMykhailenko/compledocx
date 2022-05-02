package com.dariomorgrane.compledocx.model.speaker;

public enum Speaker {

    INTERVIEWER,
    RESPONDENT;

    public Speaker getAnotherSpeaker() {
        return this == INTERVIEWER ? RESPONDENT : INTERVIEWER;
    }

}
