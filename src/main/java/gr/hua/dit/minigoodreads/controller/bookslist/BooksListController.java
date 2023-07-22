package gr.hua.dit.minigoodreads.controller.bookslist;

import gr.hua.dit.minigoodreads.controller.BaseController;
import gr.hua.dit.minigoodreads.controller.ResponseWrapper;
import gr.hua.dit.minigoodreads.dto.books_list.CreateBooksListDto;
import gr.hua.dit.minigoodreads.dto.books_list.GetBooksListDto;
import gr.hua.dit.minigoodreads.dto.books_list.RenameBooksListDto;
import gr.hua.dit.minigoodreads.entity.BooksList;
import gr.hua.dit.minigoodreads.service.BooksListService;
import gr.hua.dit.minigoodreads.service.Result;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lists")
public class BooksListController extends BaseController {

	private final BooksListService booksListService;
	private final ModelMapper modelMapper;

	public BooksListController(BooksListService booksListService, ModelMapper modelMapper) {
		this.booksListService = booksListService;
		this.modelMapper = modelMapper;
	}

	@GetMapping
	ResponseEntity<ResponseWrapper<List<GetBooksListDto>>> getAll(Principal principal) {
		List<GetBooksListDto> lists = booksListService.getListsForUser(principal.getName())
			.getData()
			.stream()
			.map(list -> modelMapper.map(list, GetBooksListDto.class))
			.collect(Collectors.toList());
		return ResponseEntity.ok(new ResponseWrapper<>(lists));
	}

	private ResponseEntity<ResponseWrapper<GetBooksListDto>> handleSaveListSuccess(Result.Success<BooksList, BooksListErrors> result) {
		GetBooksListDto response = modelMapper.map(result.getData(), GetBooksListDto.class);
		return ResponseEntity
			.created(URI.create("/lists/" + response.listId()))
			.body(new ResponseWrapper<>(response));
	}

	@PostMapping(
		consumes = "application/json",
		produces = "application/json"
	)
	ResponseEntity<ResponseWrapper<GetBooksListDto>> postBooksList(
		@Valid @RequestBody CreateBooksListDto booksListDto,
		Principal principal
	) {
		String listName = booksListDto.name().trim();
		BooksList list = new BooksList(principal.getName(), listName);
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
	ResponseEntity<Void> renameList(
		@PathVariable("id") int listId,
		@Valid @RequestBody RenameBooksListDto renameBooksListDto,
		Principal principal
	) {
		Result<Void, BooksListErrors> result = booksListService.renameList(listId, principal.getName(), renameBooksListDto.name());
		return switch (result) {
			case Result.Success<Void, BooksListErrors> ignored -> ResponseEntity.noContent().build();
			case Result.Error<Void, BooksListErrors> error -> throw handleError(error.getError());
		};
	}

	@DeleteMapping("/{id}")
	ResponseEntity<Void> deleteList(
		@PathVariable("id") int listId,
		Principal principal
	) {
		Result<Void, BooksListErrors> result = booksListService.deleteList(listId, principal.getName());
		return switch (result) {
			case Result.Success<Void, BooksListErrors> ignored -> ResponseEntity.ok().build();
			case Result.Error<Void, BooksListErrors> error -> throw handleError(error.getError());
		};
	}
}
