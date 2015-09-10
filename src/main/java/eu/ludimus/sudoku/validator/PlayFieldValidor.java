package eu.ludimus.sudoku.validator;

import eu.ludimus.sudoku.PlayField;
import eu.ludimus.sudoku.SudokuSolver;
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
        final List<List<Integer>> solved = new SudokuSolver(field.getValues()).solve();
        for(List<Integer> list : solved) {
            for(Integer i : list) {
                if(i == 0) {
                    errors.rejectValue("values","values[invalidLength]", "This puzzle is unsolvable! Fill in different values");
                    return;
                }
            }
        }
    }
}
