package main.validator;

import main.domain.Person;
import main.register.AnswerCityRegister;
import main.domain.Child;
import main.register.AnswerCityRegisterItem;
import main.register.CityRegisterResponse;
import main.domain.StudentOrder;
import main.exception.CityRegisterException;
import main.validator.register.CityRegisterChecker;
import main.validator.register.FakeCityRegisterChecker;

import java.util.List;

public class CityRegisterValidator {

    private String hostName;
    private String login;
    private String password;
    private CityRegisterChecker personChecker;

    public CityRegisterValidator() {
        personChecker = new FakeCityRegisterChecker();
    }

    public AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
        AnswerCityRegister answer = new AnswerCityRegister();

        answer.addItem(checkPerson(studentOrder.getHusband()));
        answer.addItem(checkPerson(studentOrder.getWife()));
        for (Child child : studentOrder.getChildren()) {
            answer.addItem(checkPerson(child));
        }

        return answer;
    }

    private AnswerCityRegisterItem checkPerson(Person person) {
        try {
            CityRegisterResponse chAnswer = personChecker.checkPerson(person);
        } catch (CityRegisterException ex) {
            ex.printStackTrace(System.out);
        }
        return null;
    }

    public CityRegisterValidator(String hostName, String login, String password) {
        this.hostName = hostName;
        this.login = login;
        this.password = password;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
