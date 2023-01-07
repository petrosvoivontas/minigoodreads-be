package gr.hua.dit.minigoodreads.config;

import gr.hua.dit.minigoodreads.dto.book_in_list.AddBookInListDto;
import gr.hua.dit.minigoodreads.dto.book_in_list.GetBookInListDto;
import gr.hua.dit.minigoodreads.dto.books_list.GetBooksListDto;
import gr.hua.dit.minigoodreads.entity.BookInList;
import gr.hua.dit.minigoodreads.entity.BooksList;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(BooksList.class, GetBooksListDto.class).addMappings(mapper -> {
            mapper.map(BooksList::getResourceId, GetBooksListDto::setResourceId);
            mapper.map(BooksList::getListId, GetBooksListDto::setListId);
            mapper.map(BooksList::getName, GetBooksListDto::setName);
        });

        modelMapper.typeMap(AddBookInListDto.class, BookInList.class).setProvider(request -> {
            AddBookInListDto source = (AddBookInListDto) request.getSource();
            return new BookInList(
                source.getBookId(),
                source.getCoverImageUrl(),
                source.getBookTitle(),
                source.getBookAuthor()
            );
        });

        modelMapper.typeMap(BookInList.class, GetBookInListDto.class).setProvider(request -> {
            BookInList source = (BookInList) request.getSource();
            return new GetBookInListDto(
                source.getBookId(),
                source.getCoverImageUrl(),
                source.getBookTitle(),
                source.getBookAuthor(),
                source.getInsertTs().getTime()
            );
        });
        return modelMapper;
    }
}
