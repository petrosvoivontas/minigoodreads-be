package gr.hua.dit.minigoodreads.controller.bookslist;

import gr.hua.dit.minigoodreads.controller.BaseController;
import gr.hua.dit.minigoodreads.controller.ResponseWrapper;
import gr.hua.dit.minigoodreads.dto.books_list.CreateBooksListDto;
import gr.hua.dit.minigoodreads.dto.books_list.GetBooksListDto;
import gr.hua.dit.minigoodreads.dto.books_list.RenameBooksListDto;
import gr.hua.dit.minigoodreads.entity.BooksList;
import gr.hua.dit.minigoodreads.service.BooksListService;
import gr.hua.dit.minigoodreads.service.Result;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lists")
public class BooksListController extends BaseController {

    private final BooksListService booksListService;
    private final ModelMapper modelMapper;

    public BooksListController(BooksListService booksListService, ModelMapper modelMapper) {
        this.booksListService = booksListService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    ResponseEntity<ResponseWrapper<List<GetBooksListDto>>> getAll() {
        List<GetBooksListDto> lists = booksListService.getListsForUser("uid")
            .getData()
            .stream()
            .map(list -> modelMapper.map(list, GetBooksListDto.class))
            .toList();
        return ResponseEntity.ok(new ResponseWrapper<>(lists));
    }

    private ResponseEntity<ResponseWrapper<GetBooksListDto>> handleSaveListSuccess(Result.Success<BooksList, BooksListErrors> result) {
        GetBooksListDto response = modelMapper.map(result.getData(), GetBooksListDto.class);
        return ResponseEntity
            .created(URI.create("/lists/" + response.getListId()))
            .body(new ResponseWrapper<>(response));
    }

    @PostMapping(
        consumes = "application/json",
        produces = "application/json"
    )
    ResponseEntity<ResponseWrapper<GetBooksListDto>> postBooksList(@Valid @RequestBody CreateBooksListDto booksListDto) {
        String listName = booksListDto.getName().trim();
        BooksList list = new BooksList("uid", listName);
        Result<BooksList, BooksListErrors> result = booksListService.saveList(list);
        return switch (result) {
            case Result.Success<BooksList, BooksListErrors> s -> handleSaveListSuccess(s);
            case Result.Error<BooksList, BooksListErrors> error -> throw handleError(error.getError());
        };
    }

    @PatchMapping(
        value = "/{id}",
        consumes = "application/json"
    )
    ResponseEntity<Void> renameList(@PathVariable("id") int listId, @Valid @RequestBody RenameBooksListDto renameBooksListDto) {
        Result<Void, BooksListErrors> result = booksListService.renameList(listId, "uid", renameBooksListDto.getName());
        return switch (result) {
            case Result.Success<Void, BooksListErrors> ignored -> ResponseEntity.noContent().build();
            case Result.Error<Void, BooksListErrors> error -> throw handleError(error.getError());
        };
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteList(@PathVariable("id") int listId) {
        Result<Void, BooksListErrors> result = booksListService.deleteList(listId, "uid");
        return switch (result) {
            case Result.Success<Void, BooksListErrors> ignored -> ResponseEntity.ok().build();
            case Result.Error<Void, BooksListErrors> error -> throw handleError(error.getError());
        };
    }
}
