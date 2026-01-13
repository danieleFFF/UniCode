package it.unical.unicode.dto;

import it.unical.unicode.model.Exercise;
import it.unical.unicode.model.TestCase;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class ExerciseCreationRequest{
    private Exercise exercise;
    private List<TestCase> testCases;
}
