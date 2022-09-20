package main.validator;

import main.domain.CityRegisterCheckerResponse;
import main.domain.Person;

public class FakeCityRegisterChecker implements CityRegisterChecker {
    // Временный класс-заглулка для CityRegisterValidator, пока не настрою RealCityRegisterChecker
    public CityRegisterCheckerResponse checkPerson(Person person) {
        return null;
    }
}
