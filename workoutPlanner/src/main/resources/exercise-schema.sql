DROP TABLE `exercise`;

CREATE TABLE exercise (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    day VARCHAR(255),
    name VARCHAR(255),
    time BIGINT NOT NULL,
    reps BIGINT NOT NULL,
    done BOOLEAN
);