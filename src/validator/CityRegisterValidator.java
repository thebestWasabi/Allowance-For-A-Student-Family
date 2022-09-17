package validator;

import answer.AnswerCityRegister;
import main.StudentOrder;

public class CityRegisterValidator {

    private String hostName;
    private String login;
    private String password;

    public AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
        System.out.printf("City register check is running: %s, %s, %s \n", hostName, login, password);
        AnswerCityRegister cityRegister = new AnswerCityRegister();
        cityRegister.success = false;
        return cityRegister;
    }

    public CityRegisterValidator(String hostName, String login, String password) {
        this.hostName = hostName;
        this.login = login;
        this.password = password;
    }

    public CityRegisterValidator() {

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
