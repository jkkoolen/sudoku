package eu.ludimus.sudoku;

import eu.ludimus.sudoku.io.ReaderWriter;
import eu.ludimus.sudoku.validator.PlayFieldValidor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Controller
public class SudokuController {
    private PlayFields playFields;
    @Autowired
    private PlayFieldValidor playFieldValidor;
    @Autowired
    private ReaderWriter<PlayFields> readerWriter;
    @Autowired
    private SudokuSolver sudokuSolver;

    @PostConstruct
    public void read() throws IOException {
        playFields = readerWriter.read();
    }

    @RequestMapping(value = "/playfields", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<PlayFields> playfields(Model model) {
        return new ResponseEntity<>(playFields, HttpStatus.OK);
    }

    @RequestMapping(value = "/solve", method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<PlayField> solve(@RequestParam(value = "step", required = false) Boolean step, @RequestBody PlayField playField, Model model) {
        if(step == null) {
            step = false;
        }
        playField.setValues(sudokuSolver.solve(playField, step));
        return new ResponseEntity<>(playField, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<PlayFields> add(@RequestBody PlayField playField, BindingResult result, Model model) throws IOException {
        playFieldValidor.validate(playField.clone(), result);
        if(result.hasErrors()) {
            throw new RestClientException(result.getFieldErrors().get(0).getDefaultMessage());
        }
        playFields.getFields().add(playField);
        readerWriter.write(playFields);
        return playfields(model);
    }

    @RequestMapping("/")
    public String init( Model model) {
        return "index";
    }
}



