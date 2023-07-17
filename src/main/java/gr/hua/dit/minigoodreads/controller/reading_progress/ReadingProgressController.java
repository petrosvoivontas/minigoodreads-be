package gr.hua.dit.minigoodreads.controller.reading_progress;

import gr.hua.dit.minigoodreads.controller.BaseController;
import gr.hua.dit.minigoodreads.controller.ResponseWrapper;
import gr.hua.dit.minigoodreads.dto.reading_progress.GetReadingProgressDto;
import gr.hua.dit.minigoodreads.dto.reading_progress.UpdateReadingProgressDto;
import gr.hua.dit.minigoodreads.entity.ReadingProgress;
import gr.hua.dit.minigoodreads.service.Result;
import gr.hua.dit.minigoodreads.service.reading_progress.ReadingProgressService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/progress/{bookId}")
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
        @PathVariable("bookId") String bookId
    ) {
        Result<ReadingProgress, ReadingProgressErrors> result = readingProgressService.getReadingProgress(
            bookId,
            "uid"
        );
        return switch (result) {
            case Result.Success<ReadingProgress, ReadingProgressErrors> success ->
                handleGetProgressSuccess(success);
            case Result.Error<ReadingProgress, ReadingProgressErrors> error ->
                throw handleError(error.getError());
        };
    }

    @PatchMapping
    public ResponseEntity<Void> updateProgress(
        @PathVariable("bookId") String bookId,
        @Valid @RequestBody UpdateReadingProgressDto currentPage
    ) {
        Result<Void, ReadingProgressErrors> result = readingProgressService.updateReadingProgress(
            bookId,
            currentPage.currentPage(),
            "uid"
        );
        return switch (result) {
            case Result.Success<Void, ReadingProgressErrors> ignored ->
                ResponseEntity.ok().build();
            case Result.Error<Void, ReadingProgressErrors> error ->
                throw handleError(error.getError());
        };
    }
}
