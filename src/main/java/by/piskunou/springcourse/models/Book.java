package by.piskunou.springcourse.models;

import java.time.LocalDate;
import java.time.Period;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Book")
public class Book {
	@Transient
	private static final int OVERDUE_DAYS_PERIOD = 10;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name")
	@NotBlank
	private String name;
	
	@Column(name = "author")
	@NotBlank
	@Pattern(regexp = "([A-Z]\\w{1,100} ?){2,4}",
			message = "The author should be in follow format 'Firstname Surname'")
	private String author;
	
	@Column(name = "year")
	@Positive(message = "Year must be positive")
	private int year;

	@Column(name = "taken_at")
	private LocalDate takenAt;
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "person_id", referencedColumnName = "id")
	private Person owner;
	
	public Book(String name, String author, int year) {
		this.name = name;
		this.author = author;
		this.year = year;
	}
	
	public boolean isOverdue() {
		Period period = Period.between(takenAt, LocalDate.now());
		return !period.minusDays(OVERDUE_DAYS_PERIOD).isNegative();
	}
}
