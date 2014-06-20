package uml.examples;

public class CustomExampleTopicValidator extends ExampleTopic{
    public boolean validate(){
        return AGE.value > 0;
    }
}
