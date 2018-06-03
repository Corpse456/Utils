CREATE TABLE my_games (
	id SERIAL PRIMARY KEY,
	game_name int,
	change_date date,
	folder_size bigint,
	dics int,
	FOREIGN KEY (game_name) REFERENCES games (id),
	FOREIGN KEY (disc) REFERENCES discs (id)
);