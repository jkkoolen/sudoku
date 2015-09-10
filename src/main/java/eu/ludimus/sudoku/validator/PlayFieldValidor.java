package eu.ludimus.sudoku.validator;

import eu.ludimus.sudoku.PlayField;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class PlayFieldValidor implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return PlayField.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PlayField field = (PlayField) o;
        int count = 0;
        for(List<Integer> list : field.getValues()) {
            for(Integer i : list) {
                if(i != 0) {
                    count++;
                }
            }
        }
        if(count < 17) {
            errors.rejectValue("values","values[invalidLength]", "Minimal filled in digits should be 17, otherwise it is impossible to solve the puzzle");
        };
    }
}
