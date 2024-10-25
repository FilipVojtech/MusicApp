CREATE TABLE artist (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL
);

CREATE TABLE album (
                       id SERIAL PRIMARY KEY,
                       artist_id INT NOT NULL,
                       title VARCHAR(255) NOT NULL,
                       release_date DATE,
                       FOREIGN KEY (artist_id) REFERENCES artist(id)
);

CREATE TABLE song (
                      id SERIAL PRIMARY KEY,
                      artist_id INT NOT NULL,
                      title VARCHAR(255) NOT NULL,
                      FOREIGN KEY (artist_id) REFERENCES artist(id)
);

CREATE TABLE album_songs (
                             album_id INT NOT NULL,
                             song_id INT NOT NULL,
                             PRIMARY KEY (album_id, song_id),
                             FOREIGN KEY (album_id) REFERENCES album(id),
                             FOREIGN KEY (song_id) REFERENCES song(id)
);

CREATE TABLE "user" (
                        id SERIAL PRIMARY KEY,
                        email VARCHAR(255) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        display_name VARCHAR(255)
);


CREATE TABLE playlist (
                          id SERIAL PRIMARY KEY,
                          owner_id INT NOT NULL,
                          visibility BOOLEAN NOT NULL,
                          FOREIGN KEY (owner_id) REFERENCES "user"(id)
);

CREATE TABLE playlist_songs (
                                song_id INT NOT NULL,
                                playlist_id INT NOT NULL,
                                PRIMARY KEY (song_id, playlist_id),
                                FOREIGN KEY (song_id) REFERENCES song(id),
                                FOREIGN KEY (playlist_id) REFERENCES playlist(id)
);

CREATE TABLE user_likes (
                            user_id INT NOT NULL,
                            song_id INT NOT NULL,
                            PRIMARY KEY (user_id, song_id),
                            FOREIGN KEY (user_id) REFERENCES "user"(id),
                            FOREIGN KEY (song_id) REFERENCES song(id)
);
