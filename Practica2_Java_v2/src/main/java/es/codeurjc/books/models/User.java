package es.codeurjc.books.models;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.*;

import lombok.Data;
import lombok.ToString;


@Entity
@Table(name = "users")
@Data
@ToString(exclude = "comments")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String nick;

	@Column(nullable = false)
	private String email;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Collection<Comment> comments = Collections.emptyList();

	@Column
	private String password;

}
