package gr.hua.dit.minigoodreads.controller.reading_progress;

import gr.hua.dit.minigoodreads.controller.BaseController;
import gr.hua.dit.minigoodreads.controller.ResponseWrapper;
import gr.hua.dit.minigoodreads.dto.reading_progress.GetReadingProgressDto;
import gr.hua.dit.minigoodreads.dto.reading_progress.UpdateReadingProgressDto;
import gr.hua.dit.minigoodreads.entity.ReadingProgress;
import gr.hua.dit.minigoodreads.service.Result;
import gr.hua.dit.minigoodreads.service.reading_progress.ReadingProgressService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/progress/{bookId}")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class ReadingProgressController extends BaseController {

	private final ReadingProgressService readingProgressService;
	private final ModelMapper modelMapper;

	public ReadingProgressController(
		ReadingProgressService readingProgressService,
		ModelMapper modelMapper
	) {
		this.readingProgressService = readingProgressService;
		this.modelMapper = modelMapper;
	}

	private ResponseEntity<ResponseWrapper<GetReadingProgressDto>> handleGetProgressSuccess(
		Result.Success<ReadingProgress, ReadingProgressErrors> result
	) {
		GetReadingProgressDto dto = modelMapper.map(
			result.getData(),
			GetReadingProgressDto.class
		);
		return ResponseEntity.ok(new ResponseWrapper<>(dto));
	}

	@GetMapping
	public ResponseEntity<ResponseWrapper<GetReadingProgressDto>> getProgress(
		@PathVariable("bookId") String bookId,
		Principal principal
	) {
		Result<ReadingProgress, ReadingProgressErrors> result = readingProgressService.getReadingProgress(
			bookId,
			principal.getName()
		);
		return switch (result) {
			case Result.Success<ReadingProgress, ReadingProgressErrors> success -> handleGetProgressSuccess(success);
			case Result.Error<ReadingProgress, ReadingProgressErrors> error -> throw handleError(error.getError());
		};
	}

	@PatchMapping
	public ResponseEntity<Void> updateProgress(
		@PathVariable("bookId") String bookId,
		@Valid @RequestBody UpdateReadingProgressDto currentPage,
		Principal principal
	) {
		Result<Void, ReadingProgressErrors> result = readingProgressService.updateReadingProgress(
			bookId,
			currentPage.currentPage(),
			principal.getName()
		);
		return switch (result) {
			case Result.Success<Void, ReadingProgressErrors> ignored -> ResponseEntity.ok().build();
			case Result.Error<Void, ReadingProgressErrors> error -> throw handleError(error.getError());
		};
	}
}
