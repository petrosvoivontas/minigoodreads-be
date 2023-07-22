package gr.hua.dit.minigoodreads.config;

import gr.hua.dit.minigoodreads.dto.book_in_list.AddBookInListDto;
import gr.hua.dit.minigoodreads.dto.book_in_list.GetBookInListDto;
import gr.hua.dit.minigoodreads.dto.books_list.GetBooksListDto;
import gr.hua.dit.minigoodreads.dto.event.GetEventDto;
import gr.hua.dit.minigoodreads.dto.reading_progress.GetReadingProgressDto;
import gr.hua.dit.minigoodreads.entity.BookInList;
import gr.hua.dit.minigoodreads.entity.BooksList;
import gr.hua.dit.minigoodreads.entity.Event;
import gr.hua.dit.minigoodreads.entity.ReadingProgress;
import gr.hua.dit.minigoodreads.events.EventConstants;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.typeMap(BooksList.class, GetBooksListDto.class).setProvider(
			request -> {
				BooksList source = (BooksList) request.getSource();
				return new GetBooksListDto(
					source.getResourceId(),
					source.getListId(),
					source.getName()
				);
			});

		modelMapper.typeMap(
			AddBookInListDto.class,
			BookInList.class
		).setProvider(request -> {
			AddBookInListDto source = (AddBookInListDto) request.getSource();
			return new BookInList(
				source.bookId(),
				source.coverImageUrl(),
				source.bookTitle(),
				source.bookAuthor()
			);
		});

		modelMapper.typeMap(
			BookInList.class,
			GetBookInListDto.class
		).setProvider(request -> {
			BookInList source = (BookInList) request.getSource();
			return new GetBookInListDto(
				source.getBookId(),
				source.getCoverImageUrl(),
				source.getBookTitle(),
				source.getBookAuthor(),
				source.getInsertTs().getTime()
			);
		});

		modelMapper.typeMap(
			ReadingProgress.class,
			GetReadingProgressDto.class
		).setProvider(request -> {
			ReadingProgress source = (ReadingProgress) request.getSource();
			return new GetReadingProgressDto(
				source.getCurrentPage(),
				source.getLastUpdated().getTime()
			);
		});

		modelMapper.typeMap(
			Event.class,
			GetEventDto.class
		).setProvider(request -> {
			Event source = (Event) request.getSource();
			HashMap<String, Object> params = new HashMap<>();
			params.put(EventConstants.EVENT_PARAM_LIST_ID, source.getListId());
			params.put(EventConstants.EVENT_PARAM_BOOK_ID, source.getBookId());
			params.put(EventConstants.EVENT_PARAM_IMAGE_URL, source.getImageUrl());
			return new GetEventDto(
				source.getEventName().name(),
				params,
				source.getInsertTs().getTime()
			);
		});

		return modelMapper;
	}
}
