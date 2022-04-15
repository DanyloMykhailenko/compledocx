package com.dariomorgrane.compledocx.model;

public enum Speaker {

    INTERVIEWER,
    RESPONDENT;

    public Speaker getAnotherSpeaker() {
        return this == INTERVIEWER ? RESPONDENT : INTERVIEWER;
    }

}
