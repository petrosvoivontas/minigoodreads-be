package gr.hua.dit.minigoodreads.dto.book_in_list;

public record GetBookInListDto(
    String bookId,
    String coverImageUrl,
    String bookTitle,
    String bookAuthor,
    long insertTs
) {
}
