package main.validator;

import main.domain.CityRegisterCheckerResponse;
import main.domain.Person;

public interface CityRegisterChecker {
    CityRegisterCheckerResponse checkPerson(Person person);
}