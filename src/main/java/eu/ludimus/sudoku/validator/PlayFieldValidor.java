package eu.ludimus.sudoku.validator;

import eu.ludimus.sudoku.PlayField;
import eu.ludimus.sudoku.SudokuSolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class PlayFieldValidor implements Validator {
    @Autowired
    private SudokuSolver sudokuSolver;

    @Override
    public boolean supports(Class<?> aClass) {
        return PlayField.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        final List<List<Integer>> solved = sudokuSolver.solve((PlayField) o);
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
