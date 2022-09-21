package main.validator;

import main.answer.AnswerCityRegister;
import main.domain.CityRegisterCheckerResponse;
import main.domain.StudentOrder;
import main.exception.CityRegisterException;

public class CityRegisterValidator {

    private String hostName;
    private String login;
    private String password;
    private CityRegisterChecker personChecker;


    public CityRegisterValidator() {
        personChecker = new FakeCityRegisterChecker();
    }


    public AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {

        try {
            CityRegisterCheckerResponse hAnswer = personChecker.checkPerson(studentOrder.getHusband());
            CityRegisterCheckerResponse wAnswer = personChecker.checkPerson(studentOrder.getWife());
            CityRegisterCheckerResponse chAnswer = personChecker.checkPerson(studentOrder.getChild());
        } catch (CityRegisterException ex) {
            ex.printStackTrace(System.out);
        }

        AnswerCityRegister answer = new AnswerCityRegister();
        return answer;
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
