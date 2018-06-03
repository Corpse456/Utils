CREATE TABLE discs (
	id SERIAL PRIMARY KEY,
	disc_name varchar (60) NOT NULL UNIQUE,
	total_space bigint NOT NULL,
	free_space bigint NOT NULL
);