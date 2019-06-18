PRAGMA foreign_keys = ON;

CREATE TABLE competition (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    type TEXT NOT NULL,
    teams INTEGER NOT NULL,
    finished INTEGER NOT NULL,
	nota text
);

--CUP
CREATE TABLE cup (
    id INTEGER PRIMARY KEY,
    nRounds INTEGER NOT NULL,

    FOREIGN KEY (id) REFERENCES competition (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE cup_round (
    id INTEGER,
    nRound INTEGER,
    roundName TEXT NOT NULL,

    PRIMARY KEY (id,nRound),
    FOREIGN KEY (id) REFERENCES cup (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE cup_confrontation (
    id INTEGER,
    round INTEGER,
    participant1 TEXT,
    participant2 TEXT,
    winner TEXT NULL,

    PRIMARY KEY(id,round,participant1,participant2),
    FOREIGN KEY (id,round) REFERENCES cup_round (id,nRound) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE cup_participants (
    id INTEGER,
    name TEXT,
    points INTEGER NOT NULL,
    PROGRESS NULL,

    PRIMARY KEY (id,name),
    FOREIGN KEY (id) REFERENCES cup (id) ON UPDATE CASCADE ON DELETE CASCADE
);

--LEAGUE
CREATE TABLE league (
    id INTEGER PRIMARY KEY,
    nDayLeagues INTEGER NOT NULL,
    nReturns INTEGER NOT NULL,

    FOREIGN KEY (id) REFERENCES competition (id) ON UPDATE CASCADE ON DELETE CASCADE
);
                 