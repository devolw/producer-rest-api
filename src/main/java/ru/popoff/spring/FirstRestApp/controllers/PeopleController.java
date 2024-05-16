package ru.popoff.spring.FirstRestApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.popoff.spring.FirstRestApp.dto.PersonDTO;
import ru.popoff.spring.FirstRestApp.models.Person;
import ru.popoff.spring.FirstRestApp.services.PeopleService;
import ru.popoff.spring.FirstRestApp.util.PersonErrorResponse;
import ru.popoff.spring.FirstRestApp.util.PersonNotCreatedException;
import ru.popoff.spring.FirstRestApp.util.PersonNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
public class PeopleController {

    // Добавлено внедрение `ModelMapper`
    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @Autowired
    public PeopleController(PeopleService peopleService,
                            ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    // Метод изменен. Теперь получаем людей из `PersonDTO`, а не `Person`
    @GetMapping()
    public List<PersonDTO> getPeople() {
        return peopleService.findAll().stream().map(this::convertToPersonDTO).collect(Collectors.toList()); // Jackson конвертирует эти объекты в JSON
    }

    // Метод изменен. Теперь получаем человека из `PersonDTO`, а не `Person`
    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable("id") int id) {
        return convertToPersonDTO(peopleService.findOne(id)); // Jackson конвертирует эти объекты в JSON
    }

    /*
         /////////////////////////////////////////////////////////////////
         /// Метод изменен. Теперь получаем данные с `PersonDTO`       ///
         /// При сохранение в БД вызываем `convertToPerson()`          ///
         /// В `convertToPerson()` конвертируем `PersonDTO` в `Person` ///
         /////////////////////////////////////////////////////////////////
         Обрабатывает POST-запрос (создание нового пользователя)
     */
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {

        // Если есть ошибки валидации записываем и выбрасываем
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }

            throw new PersonNotCreatedException(errorMsg.toString());
        }

        // Конвертируем `personDTO` в `Person`, сохраняем в БД
        peopleService.save(convertToPerson(personDTO));

        // Отправляем HTTP ответ с пустым телом и со статусом 200
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // Метод для конвертации `personDTO` в `Person`
    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    // Метод для конвертации `person` в `PersonDTO`
    private PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

    // Метод обрабатывает исключение PersonNotCreatedException
    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        // В HTTP ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400 статус
    }

    // Метод обрабатывает исключение PersonNotFoundException
    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                "Person with this id wasn't found!",
                System.currentTimeMillis()
        );

        // В HTTP ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // NOT_FOUND - 404 статус
    }
}