package gr.hua.dit.minigoodreads.controller.book_in_list;

import gr.hua.dit.minigoodreads.controller.BaseController;
import gr.hua.dit.minigoodreads.controller.ResponseWrapper;
import gr.hua.dit.minigoodreads.dto.book_in_list.AddBookInListDto;
import gr.hua.dit.minigoodreads.dto.book_in_list.GetBookInListDto;
import gr.hua.dit.minigoodreads.entity.BookInList;
import gr.hua.dit.minigoodreads.service.BookInListService;
import gr.hua.dit.minigoodreads.service.Result;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lists/{listId}/books")
public class BookInListController extends BaseController {

    private final BookInListService service;
    private final ModelMapper modelMapper;

    public BookInListController(BookInListService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    ResponseEntity<ResponseWrapper<Void>> addBookToList(@PathVariable("listId") int listId, @Valid @RequestBody AddBookInListDto requestBody) {
        requestBody.setListId(listId);
        Result<BookInList, BookInListErrors> result = service.addBookToList("uid", requestBody);
        return switch (result) {
            case Result.Success<BookInList, BookInListErrors> ignored -> ResponseEntity.created(URI.create("")).build();
            case Result.Error<BookInList, BookInListErrors> error -> throw handleError(error.getError());
        };
    }

    @GetMapping
    ResponseEntity<ResponseWrapper<Set<GetBookInListDto>>> getBooksInList(@PathVariable("listId") int listId) {
        Result<Set<BookInList>, BookInListErrors> result = service.getBooksInList("uid", listId);
        return switch (result) {
            case Result.Success<Set<BookInList>, BookInListErrors> s -> handleGetBooksInListSuccess(s);
            case Result.Error<Set<BookInList>, BookInListErrors> error -> throw handleError(error.getError());
        };
    }

    private ResponseEntity<ResponseWrapper<Set<GetBookInListDto>>> handleGetBooksInListSuccess(Result.Success<Set<BookInList>, BookInListErrors> s) {
        Set<BookInList> data = s.getData();
        if (data == null) {
            return ResponseEntity.ok(new ResponseWrapper<>(new HashSet<>()));
        }
        Set<GetBookInListDto> response = data.stream()
            .map(b -> modelMapper.map(b, GetBookInListDto.class))
            .collect(Collectors.toSet());
        return ResponseEntity.ok(new ResponseWrapper<>(response));
    }
}
