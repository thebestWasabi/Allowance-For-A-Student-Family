package main.validator;

import main.answer.AnswerCityRegister;
import main.domain.StudentOrder;

public class CityRegisterValidator {

    private String hostName;
    private String login;
    private String password;
    private CityRegisterChecker personChecker;


    public CityRegisterValidator() {
        personChecker = new FakeCityRegisterChecker();
    }


    public AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
        personChecker.checkPerson(studentOrder.getHusband());
        personChecker.checkPerson(studentOrder.getWife());
        personChecker.checkPerson(studentOrder.getChild());

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
