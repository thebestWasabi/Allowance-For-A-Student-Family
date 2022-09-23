package main.validator.register;

import main.register.CityRegisterResponse;
import main.domain.Person;
import main.exception.CityRegisterException;

public interface CityRegisterChecker {
    CityRegisterResponse checkPerson(Person person) throws CityRegisterException;
}