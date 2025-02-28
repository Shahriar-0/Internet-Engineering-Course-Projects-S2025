package application.services;

import application.dtos.AddAuthorDto;
import application.dtos.AddBookDto;
import application.dtos.AddUserDto;
import application.repositories.IAuthorRepository;
import domain.entities.Author;
import domain.entities.Book;
import domain.entities.User;
import domain.valueobjects.BookContent;
import java.time.LocalDate;

public class UtilService {

	public static User createUser(AddUserDto dto) {
		return User
			.builder()
			.key(dto.username())
			.address(dto.address())
			.password(dto.password())
			.email(dto.email())
			.role(User.Role.valueOf(dto.role().toUpperCase()))
			.build();
	}

	public static Author createAuthor(AddAuthorDto dto) {
		return Author
			.builder()
			.key(dto.name())
			.penName(dto.penName())
			.nationality(dto.nationality())
			.born(LocalDate.parse(dto.born()))
			.died(dto.died() == null ? null : LocalDate.parse(dto.died()))
			.build();
	}

	public static Book createBook(AddBookDto dto, IAuthorRepository authorRepository) {
		return Book
			.builder()
			.key(dto.title())
			.author(authorRepository.find(dto.author()).getData())
			.publisher(dto.publisher())
			.year(dto.year())
			.price(dto.price())
			.synopsis(dto.synopsis())
			.content(new BookContent(dto.content()))
			.genres(dto.genres())
			.build();
	}
}
