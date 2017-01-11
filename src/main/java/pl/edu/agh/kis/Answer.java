package pl.edu.agh.kis;

/**
 * Created by Karl on 07.01.2017.
 */
//TODO
// time
// measurement - server gets normal time, client sends różnica of time
// multithreading in server counts 15 seconds and makes Answer null or sth like that


public class Answer extends AnswerAbstract {
    Answer(Reply reply, int playerID) {
        super(reply, playerID);
    }
}
