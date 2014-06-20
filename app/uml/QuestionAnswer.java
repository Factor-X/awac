package uml;

public abstract class QuestionAnswer {
    public Integer id;
    public Question question;
    public User owner;
    public User validator;
    public User locker;
    public VerificationStatus verificationStatus;
}

