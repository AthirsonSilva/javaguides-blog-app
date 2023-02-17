package com.blog.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
	name = "category",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = "name")
	}
)
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	@Column(name = "description", nullable = false)
	private String description;
	@OneToMany(
		mappedBy = "category",
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	private List<Post> posts;
}
