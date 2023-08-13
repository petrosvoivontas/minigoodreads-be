package gr.hua.dit.minigoodreads.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.hua.dit.minigoodreads.dto.book_in_list.AddBookInListDto;
import gr.hua.dit.minigoodreads.dto.book_in_list.GetBookInListDto;
import gr.hua.dit.minigoodreads.dto.books_list.GetBooksListDto;
import gr.hua.dit.minigoodreads.dto.event.GetEventDto;
import gr.hua.dit.minigoodreads.dto.reading_progress.GetReadingProgressDto;
import gr.hua.dit.minigoodreads.entity.BookInList;
import gr.hua.dit.minigoodreads.entity.BooksList;
import gr.hua.dit.minigoodreads.entity.Event;
import gr.hua.dit.minigoodreads.entity.ReadingProgress;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Map;

@Configuration
public class ModelMapperConfig {

	private final ObjectMapper objectMapper;

	@Autowired
	public ModelMapperConfig(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

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
				source.bookAuthor(),
				source.pageCount()
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
				source.getPageCount(),
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
			Map<String, Object> params = Collections.emptyMap();
			try {
				TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {
				};
				params = objectMapper.readValue(source.getEventParams(), typeRef);
			} catch (Exception ignored) {
			}
			return new GetEventDto(
				source.getEventName().getEventName(),
				params,
				source.getInsertTs().getTime()
			);
		});

		return modelMapper;
	}
}
