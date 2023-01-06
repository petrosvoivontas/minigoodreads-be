package gr.hua.dit.minigoodreads.controller;

import gr.hua.dit.minigoodreads.dto.books_list.CreateBooksListDto;
import gr.hua.dit.minigoodreads.dto.books_list.GetBooksListDto;
import gr.hua.dit.minigoodreads.dto.books_list.RenameBooksListDto;
import gr.hua.dit.minigoodreads.entity.BooksList;
import gr.hua.dit.minigoodreads.service.BooksListService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lists")
public class BooksListController {

    private final BooksListService booksListService;
    private final ModelMapper modelMapper;

    public BooksListController(BooksListService booksListService, ModelMapper modelMapper) {
        this.booksListService = booksListService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    List<GetBooksListDto> getAll() {
        return booksListService.getListsForUser("uid")
            .stream()
            .map(list -> modelMapper.map(list, GetBooksListDto.class))
            .toList();
    }

    @PostMapping(
        consumes = "application/json",
        produces = "application/json"
    )
    ResponseEntity<GetBooksListDto> postBooksList(@Valid @RequestBody CreateBooksListDto booksListDto) {
        BooksList list = new BooksList("uid", booksListDto.getName());
        BooksList finalList = booksListService.saveList(list);
        GetBooksListDto response = modelMapper.map(finalList, GetBooksListDto.class);
        return ResponseEntity.created(URI.create("/lists/" + response.getListId())).body(response);
    }

    @PatchMapping(
        value = "/{id}",
        consumes = "application/json"
    )
    ResponseEntity<Void> renameList(@PathVariable("id") int listId, @Valid @RequestBody RenameBooksListDto renameBooksListDto) {
        boolean changed = booksListService.renameList(listId, "uid", renameBooksListDto.getName());
        if (changed) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteList(@PathVariable("id") int listId) {
        boolean deleted = booksListService.deleteList(listId, "uid");
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }
}
