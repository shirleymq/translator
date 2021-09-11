package com.files.translator.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TranslatorServiceTest {

    @Autowired
    private TranslatorService translatorService;

    @Test
    public void operations() throws IOException {
        String originalFileName = "src/main/resources/Original.txt";
        String inverseFileName = "src/main/resources/estrofasEnOrdenInverso.txt";
        File inverseFile = new File(inverseFileName);
        String statisticsFileName = "src/main/resources/statistics.txt";
        File statisticFile = new File(statisticsFileName);
        String finalFileName = "src/main/resources/finalOutput.txt";
        File finalFile = new File(finalFileName);

        translatorService.reverseOrderStrophe(originalFileName,inverseFileName);
        assertTrue(inverseFile.exists());

        assertEquals(17,translatorService.numberStrophe(inverseFileName));

        assertEquals("beggin'",translatorService.repeatedWord(inverseFileName));
        translatorService.printRepeatedWord(inverseFileName,statisticsFileName);
        assertTrue(statisticFile.exists());

        translatorService.replaceWord(statisticsFileName, inverseFileName, finalFileName);
        assertTrue(finalFile.exists());

    }
}