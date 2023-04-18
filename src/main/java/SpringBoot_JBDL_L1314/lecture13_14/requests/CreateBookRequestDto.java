package SpringBoot_JBDL_L1314.lecture13_14.requests;

import SpringBoot_JBDL_L1314.lecture13_14.models.Author;
import SpringBoot_JBDL_L1314.lecture13_14.models.Book;
import SpringBoot_JBDL_L1314.lecture13_14.models.BookStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;


@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequestDto {

    @NotBlank
    String name;

    @Positive
    int cost;

    @NotBlank
    String isbn;


    @NotBlank
    String authorName;

    @NotBlank
    @Email
    String authorEmail;


    public Book toBook(){
        Author author = Author.builder()
                .name(authorName)
                .email(authorEmail).build();


        return Book.builder()
                .cost(cost)
                .isbn(isbn)
                .name(name.toUpperCase())
                .bookStatus(BookStatus.AVAILABLE)
                .author1(author)
                .build();
    }
}
