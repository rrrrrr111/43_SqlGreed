package ru.roman.bim.service.translate.rotator;

/** @author Roman 07.09.13 2:28 */
public interface RotationService {



    String getFirstValidTranslation();

    String getNextTranslation();

    boolean wasLastTranslation();


}
