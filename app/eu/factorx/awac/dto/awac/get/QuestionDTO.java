package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

public class QuestionDTO<T> extends DTO {
    public T value;
    public ReviewersPart reviewers;

    public QuestionDTO() {
        super();
        reviewers = new ReviewersPart();
    }

    private class ReviewersPart {
        public String dataOwner;
        public String dataValidator;
        public String dataVerifier;
        public String dataLocker;
    }
}



