package main.validator.register;

import main.register.CityRegisterResponse;
import main.domain.Person;
import main.exception.CityRegisterException;

public class RealCityRegisterChecker implements CityRegisterChecker {

    public CityRegisterResponse checkPerson(Person person) throws CityRegisterException {
        return null;
    }
}
