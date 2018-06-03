CREATE TABLE games (
	id serial PRIMARY KEY,
	title varchar(150) NOT NULL,
	genre varchar(150),
	rel_date date,
	rate_im numeric(3,1),
	rate_users numeric(3,1),
	link text NOT NULL
);