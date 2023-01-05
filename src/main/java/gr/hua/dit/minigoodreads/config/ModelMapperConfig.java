package gr.hua.dit.minigoodreads.config;

import gr.hua.dit.minigoodreads.dto.books_list.GetBooksListDto;
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
        return modelMapper;
    }
}
