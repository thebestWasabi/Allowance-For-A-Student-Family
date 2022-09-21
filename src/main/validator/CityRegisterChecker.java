package main.validator;

import main.domain.CityRegisterCheckerResponse;
import main.domain.Person;
import main.exception.CityRegisterException;

public interface CityRegisterChecker {
    CityRegisterCheckerResponse checkPerson(Person person) throws CityRegisterException;
}